package app.openschool.feature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.difficulty.DifficultyRepository;
import app.openschool.course.language.Language;
import app.openschool.course.language.LanguageRepository;
import app.openschool.feature.api.CourseSearchingFeaturesDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FeatureServiceImplTest {

  @Mock private CategoryRepository categoryRepository;
  @Mock private LanguageRepository languageRepository;
  @Mock private DifficultyRepository difficultyRepository;

  private FeatureService featureService;

  @BeforeEach
  void setUp() {
    featureService =
        new FeatureServiceImpl(categoryRepository, languageRepository, difficultyRepository, applicationEventPublisher);
  }

  @Test
  public void getCourseSearchingFeatures() {
    Difficulty difficulty1 = new Difficulty();
    difficulty1.setId(1);
    difficulty1.setTitle("Basic");
    Difficulty difficulty2 = new Difficulty();
    difficulty2.setId(2);
    difficulty2.setTitle("Advanced");
    List<Difficulty> allDifficultyLevels = List.of(difficulty1, difficulty2);
    when(difficultyRepository.findAll()).thenReturn(allDifficultyLevels);
    Language language1 = new Language();
    language1.setId(1);
    language1.setTitle("English");
    Language language2 = new Language();
    language2.setId(2);
    language2.setTitle("Armenian");
    List<Language> allLanguages = List.of(language1, language2);
    when(languageRepository.findAll()).thenReturn(allLanguages);
    Category category1 = new Category("JAVA", null);
    category1.setId(1L);
    Category category2 = new Category("JS", null);
    category2.setId(2L);
    Category category3 = new Category("Groovy", category1);
    category3.setId(3L);
    category3.setParentCategory(category1);
    Category category4 = new Category("React", category2);
    category4.setId(4L);
    category4.setParentCategory(category2);
    List<Category> subCategories = List.of(category3, category4);
    when(categoryRepository.findByParentCategoryIsNotNull()).thenReturn(subCategories);
    CourseSearchingFeaturesDto courseSearchingFeaturesDto =
        featureService.getCourseSearchingFeatures();
    assertEquals(2, courseSearchingFeaturesDto.getAllDifficultyLevels().size());
    assertEquals(2, courseSearchingFeaturesDto.getAllLanguages().size());
    assertEquals(2, courseSearchingFeaturesDto.getParentAndSubcategories().size());
  }
}
