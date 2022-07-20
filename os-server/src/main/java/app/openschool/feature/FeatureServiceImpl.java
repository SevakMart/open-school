package app.openschool.feature;

import static java.util.stream.Collectors.groupingBy;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.difficulty.DifficultyRepository;
import app.openschool.course.language.Language;
import app.openschool.course.language.LanguageRepository;
import app.openschool.feature.api.CourseSearchingFeaturesDto;
import java.util.Map;
import java.util.stream.Collectors;
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

    Map<Integer, String> allLanguagesMap =
        languageRepository.findAll().stream()
            .collect(Collectors.toMap(Language::getId, Language::getTitle));

    Map<Integer, String> allDifficultyLevelsMap =
        difficultyRepository.findAll().stream()
            .collect(Collectors.toMap(Difficulty::getId, Difficulty::getTitle));

    Map<String, Map<Long, String>> parentAndSubCategoriesMap =
        categoryRepository.findByParentCategoryIsNotNull().stream()
            .collect(
                groupingBy(
                    category -> category.getParentCategory().getTitle(),
                    Collectors.toMap(Category::getId, Category::getTitle)));

    return new CourseSearchingFeaturesDto(
        parentAndSubCategoriesMap, allLanguagesMap, allDifficultyLevelsMap);
  }
}
