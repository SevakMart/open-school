package app.openschool.user;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.api.exception.EnrolledCourseExistsException;
import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.course.module.EnrolledModule;
import app.openschool.course.module.EnrolledModuleRepository;
import app.openschool.course.module.item.EnrolledModuleItem;
import app.openschool.course.module.item.EnrolledModuleItemRepository;
import app.openschool.course.module.item.status.ModuleItemStatusRepository;
import app.openschool.course.module.status.ModuleStatusRepository;
import app.openschool.course.status.CourseStatusRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
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
  private final EnrolledCourseRepository enrolledCourseRepository;
  private final EnrolledModuleRepository enrolledModuleRepository;
  private final EnrolledModuleItemRepository enrolledModuleItemRepository;
  private final CourseStatusRepository courseStatusRepository;
  private final ModuleStatusRepository moduleStatusRepository;
  private final ModuleItemStatusRepository moduleItemStatusRepository;
  private static final Pattern CLEAR_PATTERN = Pattern.compile("\\s+");

  public UserServiceImpl(
      UserRepository userRepository,
      CategoryRepository categoryRepository,
      CourseRepository courseRepository,
      EnrolledCourseRepository enrolledCourseRepository,
      EnrolledModuleRepository enrolledModuleRepository,
      EnrolledModuleItemRepository enrolledModuleItemRepository,
      CourseStatusRepository courseStatusRepository,
      ModuleStatusRepository moduleStatusRepository,
      ModuleItemStatusRepository moduleItemStatusRepository) {
    this.userRepository = userRepository;
    this.categoryRepository = categoryRepository;
    this.courseRepository = courseRepository;
    this.enrolledCourseRepository = enrolledCourseRepository;
    this.enrolledModuleRepository = enrolledModuleRepository;
    this.enrolledModuleItemRepository = enrolledModuleItemRepository;
    this.courseStatusRepository = courseStatusRepository;
    this.moduleStatusRepository = moduleStatusRepository;
    this.moduleItemStatusRepository = moduleItemStatusRepository;
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
  public Set<Category> savePreferredCategories(Long userId, Set<Long> categoryIds) {
    User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    Set<Category> userCategories = new HashSet<>();
    categoryIds.forEach(
        (categoryId -> {
          Category fetchedCategory =
              categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
          userCategories.add(fetchedCategory);
        }));
    user.setCategories(userCategories);
    userRepository.save(user);
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
    String normalizeName = Objects.nonNull(name) ? removeRedundantSpaces(name) : null;

    return userRepository
        .findMentorsByName(normalizeName, pageable)
        .orElseThrow(IllegalArgumentException::new);
  }

  @Override
  @Transactional
  public User saveMentor(User user, Long mentorId) {
    User mentor = userRepository.findById(mentorId).orElseThrow(IllegalArgumentException::new);
    if (mentor.getRole().getType().equals("MENTOR")) {
      user.getMentors().add(mentor);
      return userRepository.save(user);
    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public Page<User> findSavedMentors(User user, Pageable pageable) {
    List<User> mentors = new ArrayList<>(user.getMentors());
    return new PageImpl<>(mentors, pageable, mentors.size());
  }

  @Override
  @Transactional
  public Page<User> findSavedMentorsByName(Long userId, String name, Pageable pageable) {
    return userRepository.findSavedMentorsByName(userId, name, pageable);
  }

  @Override
  public void deleteMentor(User user, Long mentorId) {
    userRepository
        .findById(mentorId)
        .map(
            mentor -> {
              user.getMentors().remove(mentor);
              return userRepository.save(user);
            });
  }

  @Override
  public Page<Course> findSavedCourses(
      Long userId,
      String courseTitle,
      List<Long> subCategoryIds,
      List<Long> languageIds,
      List<Long> difficultyIds,
      Pageable pageable) {
    userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    return courseRepository.findSavedCourses(
        userId, courseTitle, subCategoryIds, languageIds, difficultyIds, pageable);
  }

  @Override
  public Course saveCourse(Long userId, Long courseId) {
    User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    Course course = courseRepository.findById(courseId).orElseThrow(IllegalArgumentException::new);
    user.getSavedCourses().add(course);
    userRepository.save(user);
    return course;
  }

  @Override
  public Course deleteCourse(Long userId, Long courseId) {
    User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
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
  public Course enrollCourse(User user, long courseId) {
    Course course = courseRepository.findById(courseId).orElseThrow(IllegalArgumentException::new);
    Set<EnrolledCourse> enrolledCourses = user.getEnrolledCourses();
    boolean enrolledCourseExists =
        enrolledCourses.stream()
            .map(enrolledCourse -> enrolledCourse.getCourse().getId())
            .anyMatch(fetchedCourseId -> fetchedCourseId == courseId);
    if (enrolledCourseExists) {
      throw new EnrolledCourseExistsException();
    }
    enrolledCourses.add(CourseMapper.toEnrolledCourse(course, user));
    userRepository.save(user);
    return course;
  }

  @Override
  public EnrolledCourse findEnrolledCourseById(Long enrolledCourseId) {
    return enrolledCourseRepository
        .findById(enrolledCourseId)
        .orElseThrow(IllegalArgumentException::new);
  }

  @Override
  public void completeEnrolledModuleItem(Long enrolledModuleItemId) {
    EnrolledModuleItem enrolledModuleItem =
        enrolledModuleItemRepository
            .findById(enrolledModuleItemId)
            .orElseThrow(IllegalArgumentException::new);
    enrolledModuleItem.setModuleItemStatus(moduleItemStatusRepository.getById(2L));
    enrolledModuleItemRepository.save(enrolledModuleItem);
    EnrolledModule enrolledModule = enrolledModuleItem.getEnrolledModule();
    if (allModuleItemsInModuleAreCompleted(enrolledModule)) {
      completeEnrolledModule(enrolledModule);
    }
  }

  private void completeEnrolledModule(EnrolledModule enrolledModule) {
    enrolledModule.setModuleStatus(moduleStatusRepository.getById(2L));
    enrolledModuleRepository.save(enrolledModule);
    EnrolledCourse enrolledCourse = enrolledModule.getEnrolledCourse();
    if (allModulesInCourseAreCompleted(enrolledCourse)) {
      completeEnrolledCourse(enrolledCourse);
    }
  }

  private void completeEnrolledCourse(EnrolledCourse enrolledCourse) {
    enrolledCourse.setCourseStatus(courseStatusRepository.getById(2L));
    enrolledCourseRepository.save(enrolledCourse);
  }

  private boolean allModuleItemsInModuleAreCompleted(EnrolledModule enrolledModule) {
    return enrolledModule.getEnrolledModuleItems().stream()
        .noneMatch(enrolledModuleItem -> enrolledModuleItem.getModuleItemStatus().isInProgress());
  }

  private boolean allModulesInCourseAreCompleted(EnrolledCourse enrolledCourse) {
    return enrolledCourse.getEnrolledModules().stream()
        .noneMatch(enrolledModule -> enrolledModule.getModuleStatus().isInProgress());
  }

  private String removeRedundantSpaces(String name) {
    return CLEAR_PATTERN.matcher(name).replaceAll(" ").trim();
  }
}
