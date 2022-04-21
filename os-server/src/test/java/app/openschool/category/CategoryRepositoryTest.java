package app.openschool.category;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.util.AssertionErrors.assertNull;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class CategoryRepositoryTest {

  @Autowired CategoryRepository categoryRepository;

  @Test
  @Transactional
  void findCategoriesByParentCategoryId() {
    Category category = new Category("JS", null);
    Category savedCategory = categoryRepository.save(category);
    categoryRepository.save(new Category("JS-React", 1L));

    List<Category> subcategories = categoryRepository.findCategoriesByParentCategoryId(1L);

    assertThat(savedCategory.getId()).isEqualTo(subcategories.get(0).getParentCategoryId());
  }

  @Test
  @Transactional
  void findByTitleStartingWith() {
    Category category1 = new Category("JAVA", null);
    Category category2 = new Category("JAVA-JDK", null);
    Category category3 = new Category("JAVA-JRE", null);
    Category category4 = new Category("JS-ANGULAR", null);
    Category category5 = new Category("JS-REACT", null);
    categoryRepository.saveAll(List.of(category1, category2, category3, category4, category5));

    List<Category> categoriesStartingJa =
        categoryRepository.findByTitleIgnoreCaseStartingWith("JA");
    List<Category> categoriesStartingJs =
        categoryRepository.findByTitleIgnoreCaseStartingWith("Js");
    assertEquals(3, categoriesStartingJa.size());
    assertEquals(2, categoriesStartingJs.size());
  }

  @Test
  @Transactional
  void findCategoryById() {
    Category category = new Category("JS", null);
    categoryRepository.save(category);
    Category fetchedCategory = categoryRepository.findCategoryById(1L);

    assertThat(category.getId()).isEqualTo(fetchedCategory.getId());
  }

  @Test
  void findAllCategoriesCheckIsParentCategory() {
    List<Category> categoryList =
        categoryRepository.findAllCategories(PageRequest.of(0, 10)).toList();
    for (Category category : categoryList) {
      assertNull(category.getTitle() + "isn't parent category", category.getParentCategoryId());
    }
  }
}
