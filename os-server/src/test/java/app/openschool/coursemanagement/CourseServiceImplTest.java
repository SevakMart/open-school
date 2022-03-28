package app.openschool.coursemanagement;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import app.openschool.coursemanagement.api.dto.CategoryDtoForRegistration;
import app.openschool.coursemanagement.entities.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

  @Mock CategoryRepository categoryRepository;
  private CourseService courseService;

  @BeforeEach
  void setUp() {
    courseService = new CourseServiceImpl(categoryRepository);
  }

  @Test
  void mapAllCategoriesToSubcategories() {
    List<Category> categories = new ArrayList<>();
    Category parentCategory1 = new Category("Java", null);
    parentCategory1.setId(1);
    Category parentCategory2 = new Category("JS", null);
    parentCategory2.setId(2);
    categories.add(parentCategory1);
    categories.add(parentCategory2);
    List<Category> subcategories1 = new ArrayList<>();
    subcategories1.add(new Category("Collections", 1));
    subcategories1.add(new Category("Generics", 1));
    List<Category> subcategories2 = new ArrayList<>();
    subcategories2.add(new Category("Angular-JS", 2));
    subcategories2.add(new Category("React-JS", 2));

    List<Category> categoriesSearchedByJs = new ArrayList<>();
    List<Category> categoriesSearchedByAng = new ArrayList<>();
    categoriesSearchedByJs.add(parentCategory2);
    categoriesSearchedByAng.add(new Category("Angular-JS", 2));

    given(categoryRepository.findCategoriesByParentCategoryId(null)).willReturn(categories);
    given(categoryRepository.findByTitleIgnoreCaseStartingWith("Js"))
        .willReturn(categoriesSearchedByJs);
    given(categoryRepository.findByTitleIgnoreCaseStartingWith("ang"))
        .willReturn(categoriesSearchedByAng);
    given(categoryRepository.findCategoryById(2)).willReturn(parentCategory2);
    given(categoryRepository.findCategoriesByParentCategoryId(1)).willReturn(subcategories1);
    given(categoryRepository.findCategoriesByParentCategoryId(2)).willReturn(subcategories2);

    Map<String, List<CategoryDtoForRegistration>> categoryMap1 =
        courseService.findCategoriesByTitle(" ");

    CategoryDtoForRegistration categoryDto1 = new CategoryDtoForRegistration(null, "Collections");
    CategoryDtoForRegistration categoryDto2 = new CategoryDtoForRegistration(null, "React-JS");

    assertThat(categoryMap1.get("Java").get(0).getTitle()).isEqualTo(categoryDto1.getTitle());
    assertThat(categoryMap1.get("JS").get(1).getTitle()).isEqualTo(categoryDto2.getTitle());

    Map<String, List<CategoryDtoForRegistration>> categoryMap2 =
        courseService.findCategoriesByTitle("Js");

    assertThat(categoryMap2.get("JS").get(1).getTitle()).isEqualTo(categoryDto2.getTitle());
    assertEquals(2, categoryMap2.get("JS").size());

    Map<String, List<CategoryDtoForRegistration>> categoryMap3 =
        courseService.findCategoriesByTitle("ang");

    System.out.println(categoryMap3);
    assertEquals(1, categoryMap3.size());
    assertEquals(1, categoryMap3.get("JS").size());
    assertThat(categoryMap3.get("JS").get(0).getTitle()).isEqualTo("Angular-JS");
  }
}
