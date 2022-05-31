package app.openschool.course;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.dto.CourseSearchingFeaturesDto;
import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.difficulty.DifficultyRepository;
import app.openschool.course.language.Language;
import app.openschool.course.language.LanguageRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepository;
  private final CategoryRepository categoryRepository;
  private final LanguageRepository languageRepository;
  private final DifficultyRepository difficultyRepository;

  public CourseServiceImpl(
      CourseRepository courseRepository,
      CategoryRepository categoryRepository,
      LanguageRepository languageRepository,
      DifficultyRepository difficultyRepository) {
    this.courseRepository = courseRepository;
    this.categoryRepository = categoryRepository;
    this.languageRepository = languageRepository;
    this.difficultyRepository = difficultyRepository;
  }

  @Override
  public CourseSearchingFeaturesDto getCourseSearchingFeatures() {
    List<Language> allLanguages = languageRepository.findAll();
    Map<Integer, String> allLanguagesMap = new HashMap<>();
    for (Language language : allLanguages) {
      allLanguagesMap.put(language.getId(), language.getTitle());
    }
    List<Difficulty> allDifficultyLevels = difficultyRepository.findAll();
    Map<Integer, String> allDifficultyLevelsMap = new HashMap<>();
    for (Difficulty difficultyLevel : allDifficultyLevels) {
      allDifficultyLevelsMap.put(difficultyLevel.getId(), difficultyLevel.getTitle());
    }
    return new CourseSearchingFeaturesDto(
        getParentAndRelevantChildCategories(), allLanguagesMap, allDifficultyLevelsMap);
  }

  @Override
  public Page<CourseDto> searchCourses(
      Pageable pageable,
      String courseTitle,
      List<Long> subCategoryIds,
      List<Long> languageIds,
      List<Long> difficultyIds) {
    Page<Course> courses =
        courseRepository.searchCourses(
            pageable, courseTitle, subCategoryIds, languageIds, difficultyIds);
    return CourseMapper.toCourseDtoPage(courses);
  }

  @Override
  public Optional<Course> findById(Long courseId) {
    return courseRepository.findById(courseId);
  }

  private Map<String, Map<Long, String>> getParentAndRelevantChildCategories() {
    List<Category> parentCategories = categoryRepository.findAllParentCategories();
    List<Long> parenCategoryIds =
        parentCategories.stream().map(Category::getId).collect(Collectors.toList());
    Map<String, Map<Long, String>> parentAndRelevantChildCategoriesMap = new HashMap<>();
    for (Long parenCategoryId : parenCategoryIds) {
      String parentCategoryName =
          parentCategories.stream()
              .filter(category -> category.getId().equals(parenCategoryId))
              .findFirst()
              .get()
              .getTitle();
      List<Category> childCategories =
          categoryRepository.findCategoriesByParentCategoryId(parenCategoryId);
      Map<Long, String> childCategoriesMap = new HashMap<>();
      for (Category childCategory : childCategories) {
        childCategoriesMap.put(childCategory.getId(), childCategory.getTitle());
      }
      parentAndRelevantChildCategoriesMap.put(parentCategoryName, childCategoriesMap);
    }
    return parentAndRelevantChildCategoriesMap;
  }
}
