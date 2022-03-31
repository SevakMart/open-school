package app.openschool.coursemanagement;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import app.openschool.coursemanagement.api.CategoryGenerator;
import app.openschool.coursemanagement.api.dto.CategoryDtoForRegistration;
import app.openschool.coursemanagement.entities.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

  @Mock CategoryRepository categoryRepository;

  private CourseService courseService;

  @BeforeEach
  void setUp() {
    courseService = new CourseServiceImpl(categoryRepository);
  }

  @Test
  @Transactional
  void mapAllCategoriesToSubcategories() {
    List<Category> categories = new ArrayList<>();
    Category parentCategory1 = new Category("Java", null);
    parentCategory1.setId(1L);
    Category parentCategory2 = new Category("JS", null);
    parentCategory2.setId(2L);
    categories.add(parentCategory1);
    categories.add(parentCategory2);
    List<Category> subcategories1 = new ArrayList<>();
    subcategories1.add(new Category("Collections", 1L));
    subcategories1.add(new Category("Generics", 1L));
    List<Category> subcategories2 = new ArrayList<>();
    subcategories2.add(new Category("Angular-JS", 2L));
    subcategories2.add(new Category("React-JS", 2L));

    List<Category> categoriesSearchedByJs = new ArrayList<>();
    List<Category> categoriesSearchedByAng = new ArrayList<>();
    categoriesSearchedByJs.add(parentCategory2);
    categoriesSearchedByAng.add(new Category("Angular-JS", 2L));

    given(categoryRepository.findCategoriesByParentCategoryId(null)).willReturn(categories);
    given(categoryRepository.findByTitleIgnoreCaseStartingWith("Js"))
        .willReturn(categoriesSearchedByJs);
    given(categoryRepository.findByTitleIgnoreCaseStartingWith("ang"))
        .willReturn(categoriesSearchedByAng);
    given(categoryRepository.findCategoryById(2L)).willReturn(parentCategory2);
    given(categoryRepository.findCategoriesByParentCategoryId(1L)).willReturn(subcategories1);
    given(categoryRepository.findCategoriesByParentCategoryId(2L)).willReturn(subcategories2);

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

  @Test
  void findAllCategories() {
    List<Category> categoryList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      categoryList.add(CategoryGenerator.generateCategory());
    }
    Pageable pageable = PageRequest.of(0, 2);
    Page<Category> categoryPage = new PageImpl<>(categoryList, pageable, 5);
    when(categoryRepository.findAllCategories(pageable)).thenReturn(categoryPage);
    Assertions.assertEquals(3, courseService.findAllCategories(pageable).getTotalPages());
    Assertions.assertEquals(5, courseService.findAllCategories(pageable).getTotalElements());
    Mockito.verify(categoryRepository, Mockito.times(2)).findAllCategories(pageable);
  }
}
