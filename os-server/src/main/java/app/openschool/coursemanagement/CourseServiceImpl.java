package app.openschool.coursemanagement;

import app.openschool.coursemanagement.api.dto.CategoryDto;
import app.openschool.coursemanagement.api.dto.CourseDto;
import app.openschool.coursemanagement.api.mapper.CategoryMapper;
import app.openschool.coursemanagement.api.mapper.CourseMapper;
import app.openschool.coursemanagement.entities.Course;
import app.openschool.usermanagement.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

  private final CategoryRepository categoryRepository;
  private final CourseRepository courseRepository;
  private final UserRepository userRepository;

  public CourseServiceImpl(
      CategoryRepository categoryRepository,
      CourseRepository courseRepository,
      UserRepository userRepository) {
    this.categoryRepository = categoryRepository;
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
  }

  @Override
  public Page<CategoryDto> findAllCategories(Pageable pageable) {
    return CategoryMapper.toCategoryDtoPage(categoryRepository.findAllCategories(pageable));
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
}
