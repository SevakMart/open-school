package app.openschool.coursemanagement.api.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.openschool.coursemanagement.api.CategoryGenerator;
import app.openschool.coursemanagement.api.dto.CategoryDto;
import app.openschool.coursemanagement.api.dto.PreferredCategoryDto;
import app.openschool.coursemanagement.entity.Category;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class CategoryMapperTest {

  @Test
  public void toCategoryDtoTest() {
    CategoryDto expected = CategoryMapper.toCategoryDto(CategoryGenerator.generateCategory());
    assertThat(expected).hasOnlyFields("title", "logoPath", "subCategoryCount");
  }

  @Test
  public void toPreferredCategoryDto() {
    PreferredCategoryDto expected =
        CategoryMapper.toPreferredCategoryDto(CategoryGenerator.generateCategory());
    assertThat(expected).hasOnlyFields("id", "title");
  }

  @Test
  public void categoryIdSetToCategorySet() {
    Set<Long> categoryIdSet = new HashSet<>();
    categoryIdSet.add(1L);
    categoryIdSet.add(2L);

    Set<Category> categories = CategoryMapper.categoryIdSetToCategorySet(categoryIdSet);
    assertEquals(2, categories.size());
  }
}
