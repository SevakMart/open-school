package app.openschool.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import app.openschool.category.CategoryRepository;
import app.openschool.course.difficulty.DifficultyRepository;
import app.openschool.course.keyword.KeywordRepository;
import app.openschool.course.language.LanguageRepository;
import app.openschool.course.module.item.type.ModuleItemTypeRepository;
import app.openschool.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

  @Mock private CourseRepository courseRepository;
  @Mock private CategoryRepository categoryRepository;
  @Mock private DifficultyRepository difficultyRepository;
  @Mock private LanguageRepository languageRepository;
  @Mock private KeywordRepository keywordRepository;
  @Mock private UserRepository userRepository;
  @Mock private ModuleItemTypeRepository moduleItemTypeRepository;

  private CourseServiceImpl courseService;

  @BeforeEach
  void setUp() {
    courseService =
        new CourseServiceImpl(
            courseRepository,
            categoryRepository,
            difficultyRepository,
            languageRepository,
            keywordRepository,
            userRepository,
            moduleItemTypeRepository);
  }

  @Test
  public void findCourseByNonexistentId() {
    long wrongId = 999L;
    when(courseRepository.findById(wrongId)).thenReturn(Optional.empty());
    assertEquals(Optional.empty(), courseService.findCourseById(wrongId));
  }
}
