package app.openschool.user;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import app.openschool.category.api.dto.PreferredCategoryDto;
import app.openschool.category.api.exception.CategoryNotFoundException;
import app.openschool.category.api.mapper.CategoryMapper;
import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.user.api.exception.UserNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;
  private final CourseRepository courseRepository;

  public UserServiceImpl(
      UserRepository userRepository,
      CategoryRepository categoryRepository,
      CourseRepository courseRepository) {
    this.userRepository = userRepository;
    this.categoryRepository = categoryRepository;
    this.courseRepository = courseRepository;
  }

  @Override
  public Page<User> findAllMentors(Pageable pageable) {
    return userRepository.findAllMentors(pageable);
  }

  @Override
  public List<Course> getSuggestedCourses(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    if (user.getCategories().isEmpty()) {
      return courseRepository.getRandomSuggestedCourses(4);
    }
    List<Course> suggestedCourses = courseRepository.getSuggestedCourses(userId);
    int sizeOfSuggestedCourses = suggestedCourses.size();
    if (sizeOfSuggestedCourses >= 4) {
      return suggestedCourses;
    }
    List<Course> courseList = new ArrayList<>();
    int sizeOfRandomSuggestedCourses = 4 - sizeOfSuggestedCourses;
    List<Long> existingCoursesIds =
        suggestedCourses.stream().map(Course::getId).collect(Collectors.toList());
    List<Course> randomSuggestedCourses =
        courseRepository.getRandomSuggestedCoursesIgnoredExistingCourses(
            existingCoursesIds, sizeOfRandomSuggestedCourses);
    courseList.addAll(suggestedCourses);
    courseList.addAll(randomSuggestedCourses);
    return courseList;
  }

  @Override
  @Transactional
  public Set<PreferredCategoryDto> savePreferredCategories(Long userId, Set<Long> categoryIds) {

    Optional<User> user = userRepository.findUserById(userId);
    Set<PreferredCategoryDto> userCategories = new HashSet<>();

    user.orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));

    Set<Category> categories = CategoryMapper.categoryIdSetToCategorySet(categoryIds);

    categories.forEach(
        (category -> {
          Category fetchedCategory = categoryRepository.findCategoryById(category.getId());
          if (fetchedCategory == null) {
            throw new CategoryNotFoundException(String.valueOf(category.getId()));
          } else {
            userCategories.add(CategoryMapper.toPreferredCategoryDto(fetchedCategory));
          }
        }));

    user.get().setCategories(categories);
    return userCategories;
  }

  @Override
  public List<EnrolledCourse> findEnrolledCourses(Long userId, Long courseStatusId) {
    User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    Set<EnrolledCourse> allEnrolledCourses = user.getEnrolledCourses();
    if (courseStatusId == null) {
      return new ArrayList<>(allEnrolledCourses);
    } else {
      return allEnrolledCourses.stream()
          .filter(enrolledCourse -> enrolledCourse.getCourseStatus().getId().equals(courseStatusId))
          .collect(Collectors.toList());
    }
  }

  @Override
  public Page<Course> findMentorCourses(Long mentorId, Pageable page) {
    userRepository.findById(mentorId).orElseThrow(IllegalArgumentException::new);
    return courseRepository.findCoursesByMentorId(mentorId, page);
  }

  @Override
  public Page<User> findMentorsByName(String name, Pageable pageable) {
    return userRepository.findMentorsByName(name, pageable);
  }

  @Override
  @Transactional
  public User saveMentor(Long userId, Long mentorId, String username) {
    User user = userRepository.findUserByEmail(username);
    if (user.getId().equals(userId)) {
      return userRepository
          .findUserById(mentorId)
          .filter(mentor -> mentor.getRole().getType().equals("MENTOR"))
          .map(
              mentor -> {
                user.getMentors().add(mentor);
                return userRepository.save(user);
              })
          .orElse(user);

    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public Page<User> findSavedMentors(Long userId, String username, Pageable pageable) {
    User user = userRepository.findUserByEmail(username);
    if (user.getId().equals(userId)) {
      List<User> mentors = new ArrayList<>(user.getMentors());
      return new PageImpl<>(mentors, pageable, mentors.size());
    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  @Transactional
  public Page<User> findSavedMentorsByName(
      Long userId, String username, String name, Pageable pageable) {

    User user = userRepository.findUserByEmail(username);
    if (user.getId().equals(userId)) {
      return userRepository.findSavedMentorsByName(userId, name, pageable);
    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public Page<Course> findSavedCourses(Long userId, Pageable pageable) {
    userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    return courseRepository.findSavedCourses(userId, pageable);
  }

  @Override
  public Course saveCourse(Long userId, Long courseId) {
    User user = userRepository.findUserById(userId).orElseThrow(IllegalArgumentException::new);
    Course course = courseRepository.findById(courseId).orElseThrow(IllegalArgumentException::new);
    user.getSavedCourses().add(course);
    userRepository.save(user);
    return course;
  }

  @Override
  public Course deleteCourse(Long userId, Long courseId) {
    User user = userRepository.findUserById(userId).orElseThrow(IllegalArgumentException::new);
    Course course = courseRepository.findById(courseId).orElseThrow(IllegalArgumentException::new);
    Set<Course> savedCourses = user.getSavedCourses();
    if (savedCourses.contains(course)) {
      savedCourses.remove(course);
      userRepository.save(user);
    } else {
      throw new IllegalArgumentException();
    }
    return course;
  }

  @Override
  @Transactional
  public Optional<Course> enrollCourse(String username, long courseId) {
    Optional<User> optionalUser = userRepository.findByEmail(username);
    Optional<Course> optionalCourse = courseRepository.findById(courseId);
    if (optionalUser.isPresent() && optionalCourse.isPresent()) {
      User user = optionalUser.get();
      Course course = optionalCourse.get();
      user.getEnrolledCourses().add(CourseMapper.toEnrolledCourse(course, user));
      userRepository.save(user);
      return Optional.of(course);
    }
    return Optional.empty();
  }
}
