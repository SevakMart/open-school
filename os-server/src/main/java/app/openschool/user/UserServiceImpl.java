package app.openschool.user;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import app.openschool.category.api.dto.PreferredCategoryDto;
import app.openschool.category.api.exception.CategoryNotFoundException;
import app.openschool.category.api.mapper.CategoryMapper;
import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.dto.UserCourseDto;
import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.course.api.mapper.UserCourseMapper;
import app.openschool.user.api.dto.MentorDto;
import app.openschool.user.api.exception.UserNotFoundException;
import app.openschool.user.api.mapper.MentorMapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;
  private final CourseRepository courseRepository;
  private final EnrolledCourseRepository enrolledCourseRepository;

  public UserServiceImpl(
      UserRepository userRepository,
      CategoryRepository categoryRepository,
      CourseRepository courseRepository,
      EnrolledCourseRepository enrolledCourseRepository) {
    this.userRepository = userRepository;
    this.categoryRepository = categoryRepository;
    this.courseRepository = courseRepository;
    this.enrolledCourseRepository = enrolledCourseRepository;
  }

  @Override
  public Page<MentorDto> findAllMentors(Pageable pageable) {
    return MentorMapper.toMentorDtoPage(userRepository.findAllMentors(pageable));
  }

  @Override
  public List<CourseDto> getSuggestedCourses(Long userId) {
    if (userRepository.getById(userId).getCategories().isEmpty()) {
      return CourseMapper.toCourseDtoList(courseRepository.getRandomSuggestedCourses(4));
    }
    List<Course> suggestedCourses = courseRepository.getSuggestedCourses(userId);
    int sizeOfSuggestedCourses = suggestedCourses.size();
    if (sizeOfSuggestedCourses >= 4) {
      return CourseMapper.toCourseDtoList(suggestedCourses);
    }
    List<Course> courseList = new ArrayList<>();
    int sizeOfRandomSuggestedCourses = 4 - sizeOfSuggestedCourses;
    List<Long> existingCoursesIds =
        suggestedCourses.stream().map(Course::getId).collect(Collectors.toList());
    List<Course> randomSuggestedCourses =
        courseRepository.getRandomSuggestedCoursesIgnoredExistingCourses(
            sizeOfRandomSuggestedCourses, existingCoursesIds);
    courseList.addAll(suggestedCourses);
    courseList.addAll(randomSuggestedCourses);
    return CourseMapper.toCourseDtoList(courseList);
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
  public List<UserCourseDto> findUserEnrolledCourses(Long userId, Long courseStatusId) {
    if (courseStatusId == null) {
      return UserCourseMapper.toUserCourseDtoList(
          enrolledCourseRepository.findAllUserEnrolledCourses(userId));
    }
    return UserCourseMapper.toUserCourseDtoList(
        enrolledCourseRepository.findUserEnrolledCoursesByStatus(userId, courseStatusId));
  }

  @Override
  @Transactional
  public Optional<CourseDto> enrollCourse(String username, long courseId) {
    Optional<User> optionalUser = userRepository.findByEmail(username);
    Optional<Course> optionalCourse = courseRepository.findById(courseId);
    if (optionalUser.isPresent() && optionalCourse.isPresent()) {
      User user = optionalUser.get();
      Course course = optionalCourse.get();
      user.getEnrolledCourses().add(CourseMapper.toEnrolledCourse(course, user));
      userRepository.save(user);
      return Optional.of(CourseMapper.toCourseDto(course));
    }
    return Optional.empty();
  }
}
