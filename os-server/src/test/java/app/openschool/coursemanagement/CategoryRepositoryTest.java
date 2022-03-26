package app.openschool.coursemanagement;

import app.openschool.coursemanagement.entities.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertNull;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void findAllCategoriesCheckIsParentCategory() {
        List<Category> categoryList = categoryRepository.findAllCategories(PageRequest.of(0, 10)).toList();
        for (Category category : categoryList) {
            assertNull(category.getTitle() + "isn't parent category", category.getParentCategoryId());
        }
    }

}
