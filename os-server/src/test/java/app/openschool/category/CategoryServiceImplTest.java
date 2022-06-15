package app.openschool.category;

import app.openschool.category.api.CategoryGenerator;
import app.openschool.category.api.dto.PreferredCategoryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

  @Mock private CategoryRepository categoryRepository;

  private CategoryService categoryService;

  @BeforeEach
  void setUp() {
    categoryService = new CategoryServiceImpl(categoryRepository);
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
    Assertions.assertEquals(3, categoryService.findAllCategories(pageable).getTotalPages());
    Assertions.assertEquals(5, categoryService.findAllCategories(pageable).getTotalElements());
    Mockito.verify(categoryRepository, Mockito.times(2)).findAllCategories(pageable);
  }

  @ParameterizedTest
  @NullAndEmptySource
  void findCategoriesByTitle_titleIsNotProvided_returnsAllParents(String title) {
    // given
    List<Category> parentCategories = getParentCategories();
    when(categoryRepository.findByParentCategoryIsNull()).thenReturn(parentCategories);

    // when
    Map<String, List<PreferredCategoryDto>> categoriesByTitle =
        categoryService.findCategoriesByTitle(title);

    // then
    assertEquals(categoriesByTitle.size(), parentCategories.size());
    verify(categoryRepository, times(1)).findByParentCategoryIsNull();
    verifyNoMoreInteractions(categoryRepository);
  }

  @Test
  void findCategoriesByTitle_titleIsProvided_returnsFilteredByTitle() {
    // given
    String categoryTitleJS = "JS";
    List<Category> allJSCategories = getAllJSCategories();
    when(categoryRepository.findByTitleIgnoreCaseStartingWith(categoryTitleJS))
        .thenReturn(allJSCategories);

    // when
    Map<String, List<PreferredCategoryDto>> categoriesByTitle =
        categoryService.findCategoriesByTitle(categoryTitleJS);

    // then
    assertEquals(
        categoriesByTitle.get(categoryTitleJS).size(),
        allJSCategories.stream().filter(category -> !category.isParent()).count());
    verify(categoryRepository, times(0)).findByParentCategoryIsNull();
    verify(categoryRepository, times(1)).findByTitleIgnoreCaseStartingWith(categoryTitleJS);
  }

  private List<Category> getAllJSCategories() {
    List<Category> allJSCategories = new ArrayList<>(getJsSubCategories());
    allJSCategories.add(getJSCategory());
    return allJSCategories;
  }

  private List<Category> getParentCategories() {
    List<Category> categories = new ArrayList<>();
    Category java = new Category("Java", null);
    java.setId(1L);
    Set<Category> subcategories1 = getJavaSubCategories();
    java.setSubCategories(subcategories1);

    Category js = getJSCategory();

    categories.add(java);
    categories.add(js);

    return categories;
  }

  private Category getJSCategory() {
    Category js = new Category("JS", null);
    js.setId(2L);
    js.setSubCategories(getJsSubCategories());
    return js;
  }

  private Category getJSCategoryWithoutSubs() {
    Category js = new Category("JS", null);
    js.setId(2L);
    return js;
  }

  private Set<Category> getJsSubCategories() {
    Set<Category> jsSubCategories = new HashSet<>();
    Category angularJS = new Category("Angular-JS", 2L);
    jsSubCategories.add(angularJS);
    angularJS.setParentCategory(getJSCategoryWithoutSubs());
    Category reactJS = new Category("React-JS", 2L);
    reactJS.setParentCategory(getJSCategoryWithoutSubs());
    jsSubCategories.add(reactJS);
    return jsSubCategories;
  }

  private Set<Category> getJavaSubCategories() {
    Set<Category> subcategories1 = new HashSet<>();
    subcategories1.add(new Category("Collections", 1L));
    subcategories1.add(new Category("Generics", 1L));
    return subcategories1;
  }
}
