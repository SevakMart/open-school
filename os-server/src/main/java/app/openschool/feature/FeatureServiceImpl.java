package app.openschool.feature;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.difficulty.DifficultyRepository;
import app.openschool.course.language.Language;
import app.openschool.course.language.LanguageRepository;
import app.openschool.feature.api.CourseSearchingFeaturesDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class FeatureServiceImpl implements FeatureService {

  private final CategoryRepository categoryRepository;
  private final LanguageRepository languageRepository;
  private final DifficultyRepository difficultyRepository;

  public FeatureServiceImpl(
      CategoryRepository categoryRepository,
      LanguageRepository languageRepository,
      DifficultyRepository difficultyRepository) {
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
        getParentAndSubCategories(), allLanguagesMap, allDifficultyLevelsMap);
  }

  private Map<String, Map<Long, String>> getParentAndSubCategories() {
    Map<String, Map<Long, String>> parentAndSubCategoriesMap = new HashMap<>();
    List<Category> parentCategories = categoryRepository.findByParentCategoryIsNull();
    for (Category parentCategory : parentCategories) {
      Map<Long, String> subCategoriesMap = new HashMap<>();
      Set<Category> subCategorySet = parentCategory.getSubCategories();
      for (Category subCategory : subCategorySet) {
        subCategoriesMap.put(subCategory.getId(), subCategory.getTitle());
      }
      parentAndSubCategoriesMap.put(parentCategory.getTitle(), subCategoriesMap);
    }
    return parentAndSubCategoriesMap;
  }
}
