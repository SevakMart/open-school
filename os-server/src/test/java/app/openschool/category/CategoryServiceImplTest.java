package app.openschool.category;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import app.openschool.category.api.CategoryGenerator;
import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.dto.ParentAndSubCategoriesDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

  @Mock private CategoryRepository categoryRepository;

  private CategoryService categoryService;

  private MessageSource messageSource;

  @BeforeEach
  void setUp() {
    categoryService = new CategoryServiceImpl(categoryRepository, messageSource);
  }

  @Test
  void findAllCategories() {
    List<Category> categoryList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      categoryList.add(CategoryGenerator.generateCategory());
    }
    Pageable pageable = PageRequest.of(0, 2);
    Page<Category> categoryPage = new PageImpl<>(categoryList, pageable, 5);
    when(categoryRepository.findByParentCategoryIsNull(pageable)).thenReturn(categoryPage);
    Assertions.assertEquals(3, categoryService.findAllParentCategories(pageable).getTotalPages());
    Assertions.assertEquals(
        5, categoryService.findAllParentCategories(pageable).getTotalElements());
    Mockito.verify(categoryRepository, Mockito.times(2)).findByParentCategoryIsNull(pageable);
  }

  //  @Test
  //  @Transactional
  //  void mapAllCategoriesToSubcategories() {
  //    List<Category> categories = new ArrayList<>();
  //    Category parentCategory1 = new Category("Java", null);
  //    parentCategory1.setId(1L);
  //    Category parentCategory2 = new Category("JS", null);
  //    parentCategory2.setId(2L);
  //    categories.add(parentCategory1);
  //    categories.add(parentCategory2);
  //    List<Category> subcategories1 = new ArrayList<>();
  //    subcategories1.add(new Category("Collections", parentCategory1));
  //    subcategories1.add(new Category("Generics", parentCategory1));
  //    List<Category> subcategories2 = new ArrayList<>();
  //    subcategories2.add(new Category("Angular-JS", parentCategory2));
  //    subcategories2.add(new Category("React-JS", parentCategory2));
  //
  //    List<Category> categoriesSearchedByJs = new ArrayList<>();
  //    List<Category> categoriesSearchedByAng = new ArrayList<>();
  //    categoriesSearchedByJs.add(parentCategory2);
  //    categoriesSearchedByAng.add(new Category("Angular-JS", parentCategory2));
  //
  //    given(categoryRepository.findCategoriesByParentCategoryId(null)).willReturn(categories);
  //    given(categoryRepository.findByTitleIgnoreCaseStartingWith("Js"))
  //        .willReturn(categoriesSearchedByJs);
  //    given(categoryRepository.findByTitleIgnoreCaseStartingWith("ang"))
  //        .willReturn(categoriesSearchedByAng);
  //    given(categoryRepository.getById(2L)).willReturn(parentCategory2);
  //    given(categoryRepository.findCategoriesByParentCategoryId(1L)).willReturn(subcategories1);
  //    given(categoryRepository.findCategoriesByParentCategoryId(2L)).willReturn(subcategories2);
  //
  //    Map<String, List<PreferredCategoryDto>> categoryMap1 =
  //        categoryService.findCategoriesByTitle(" ");
  //
  //    PreferredCategoryDto categoryDto1 = new PreferredCategoryDto(null, "Collections");
  //    PreferredCategoryDto categoryDto2 = new PreferredCategoryDto(null, "React-JS");
  //
  //    assertThat(categoryMap1.get("Java").get(0).getTitle()).isEqualTo(categoryDto1.getTitle());
  //    assertThat(categoryMap1.get("JS").get(1).getTitle()).isEqualTo(categoryDto2.getTitle());
  //
  //    Map<String, List<PreferredCategoryDto>> categoryMap2 =
  //        categoryService.findCategoriesByTitle("Js");
  //
  //    assertThat(categoryMap2.get("JS").get(1).getTitle()).isEqualTo(categoryDto2.getTitle());
  //    assertEquals(2, categoryMap2.get("JS").size());
  //
  //    Map<String, List<PreferredCategoryDto>> categoryMap3 =
  //        categoryService.findCategoriesByTitle("ang");
  //
  //    System.out.println(categoryMap3);
  //    assertEquals(1, categoryMap3.size());
  //    assertEquals(1, categoryMap3.get("JS").size());
  //    assertThat(categoryMap3.get("JS").get(0).getTitle()).isEqualTo("Angular-JS");
  //  }

  @Test
  public void findById_withExistingCategoryId_returnsCategory() {

    Category expectingCategory = new Category("Software engineering", null);
    given(categoryRepository.findById(anyLong())).willReturn(Optional.of(expectingCategory));

    Category actualCategory = categoryService.findById(1L);

    assertEquals(expectingCategory.getId(), actualCategory.getId());
    assertEquals(expectingCategory.getTitle(), actualCategory.getTitle());
    verify(categoryRepository, times(1)).findById(1L);
    verifyNoMoreInteractions(categoryRepository);
  }

  @Test
  public void findById_withNonExistingCategoryId_throwsIllegalArgumentException() {

    given(categoryRepository.findById(anyLong())).willReturn(Optional.empty());

    assertThatThrownBy(() -> categoryService.findById(1L))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void findAll_withNotEmptyMap_returnsDtoContainingParentAndSubCategoriesMap() {
    Category parentCategoryJava = new Category(1L, "Java", null);
    Category parentCategoryJs = new Category(2L, "JS", null);
    Category subCategoryJava1 = new Category(3L, "Thread", parentCategoryJava);
    Category subCategoryJava2 = new Category(4L, "Collections", parentCategoryJava);
    Category subCategoryJava3 = new Category(5L, "Collections List", parentCategoryJava);
    Category subCategoryJS1 = new Category(6L, "React", parentCategoryJs);
    Category subCategoryJS2 = new Category(7L, "Collections", parentCategoryJs);
    CategoryDto categoryDtoJava = new CategoryDto(1L, "Java", null);
    CategoryDto categoryDtoJS = new CategoryDto(2L, "JS", null);
    given(categoryRepository.findByParentCategoryIsNotNull())
        .willReturn(
            List.of(
                subCategoryJava1,
                subCategoryJava2,
                subCategoryJava3,
                subCategoryJS1,
                subCategoryJS2));

    ParentAndSubCategoriesDto actualDto = categoryService.findAll();

    assertTrue(actualDto.getParentAndSubCategoriesMap().containsKey(categoryDtoJava));
    assertTrue(actualDto.getParentAndSubCategoriesMap().containsKey(categoryDtoJS));
    assertEquals(actualDto.getParentAndSubCategoriesMap().size(), 2);
    assertEquals(actualDto.getParentAndSubCategoriesMap().get(categoryDtoJava).size(), 3);
    assertEquals(actualDto.getParentAndSubCategoriesMap().get(categoryDtoJS).size(), 2);
    verify(categoryRepository, times(1)).findByParentCategoryIsNotNull();
    verifyNoMoreInteractions(categoryRepository);
  }
}
