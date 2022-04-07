package app.openschool.coursemanagement.service;

import app.openschool.coursemanagement.api.dto.CategoryDto;
import app.openschool.coursemanagement.api.dto.CategoryDtoForRegistration;
import app.openschool.coursemanagement.api.dto.CourseDto;
import app.openschool.coursemanagement.api.dto.CourseOfUserDto;
import app.openschool.coursemanagement.api.mapper.CategoryMapper;
import app.openschool.coursemanagement.api.mapper.CourseMapper;
import app.openschool.coursemanagement.api.mapper.CourseOfUserMapper;
import app.openschool.coursemanagement.entity.Category;
import app.openschool.coursemanagement.entity.Course;
import app.openschool.coursemanagement.repository.CategoryRepository;
import app.openschool.coursemanagement.repository.CourseRepository;
import app.openschool.usermanagement.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  @Transactional
  public Map<String, List<CategoryDtoForRegistration>> findCategoriesByTitle(String title) {

    Map<String, List<CategoryDtoForRegistration>> mappedCategories;

    if (StringUtils.isBlank(title)) {
      return mapCategoriesToSubcategories(
          categoryRepository.findCategoriesByParentCategoryId(null));
    }

    List<Category> parentCategories =
        getCategories(title, category -> category.getParentCategoryId() == null);

    List<Category> subcategories =
        getCategories(title, category -> category.getParentCategoryId() != null);

    if (parentCategories.size() > 0) {
      mappedCategories = mapCategoriesToSubcategories(parentCategories);
    } else if (subcategories.size() > 0) {
      mappedCategories = mapSubcategoriesToCategories(subcategories);
    } else {
      mappedCategories = new HashMap<>();
    }

    return mappedCategories;
  }

  private List<Category> getCategories(String title, Predicate<Category> predicate) {
    return categoryRepository.findByTitleIgnoreCaseStartingWith(title).stream()
        .filter(predicate)
        .collect(Collectors.toList());
  }

  private Map<String, List<CategoryDtoForRegistration>> mapCategoriesToSubcategories(
      List<Category> categories) {
    return categories.stream()
        .collect(
            Collectors.toMap(
                Category::getTitle,
                (category) ->
                    categoryRepository.findCategoriesByParentCategoryId(category.getId()).stream()
                        .map((CategoryMapper::toCategoryDtoForRegistration))
                        .collect(Collectors.toList())));
  }

  private Map<String, List<CategoryDtoForRegistration>> mapSubcategoriesToCategories(
      List<Category> categories) {

    Map<String, List<CategoryDtoForRegistration>> categoryMap = new HashMap<>();

    categories.forEach(
        (category -> {
          String parentCategoryTitle =
              categoryRepository.findCategoryById(category.getParentCategoryId()).getTitle();

          if (categoryMap.containsKey(parentCategoryTitle)) {
            categoryMap
                .get(parentCategoryTitle)
                .add(CategoryMapper.toCategoryDtoForRegistration(category));
          } else {
            List<CategoryDtoForRegistration> subcategories = new ArrayList<>();
            subcategories.add(CategoryMapper.toCategoryDtoForRegistration(category));
            categoryMap.put(parentCategoryTitle, subcategories);
          }
        }));

    return categoryMap;
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
  public List<CourseOfUserDto> coursesOfUser(Long userId, Long courseStatusId) {
    if (courseStatusId == null) {
      return CourseOfUserMapper.toCourseOfUserDtoList(
          courseRepository.findAllCoursesOfUser(userId));
    }
    return CourseOfUserMapper.toCourseOfUserDtoList(
        courseRepository.findAllCoursesOfUserByStatus(userId, courseStatusId));
  }
}
