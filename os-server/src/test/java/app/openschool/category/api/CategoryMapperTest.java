package app.openschool.category.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.dto.PreferredCategoryDto;
import app.openschool.category.api.mapper.CategoryMapper;
import org.junit.jupiter.api.Test;

public class CategoryMapperTest {

  @Test
  public void toCategoryDtoTest() {
    CategoryDto expected = CategoryMapper.toCategoryDto(CategoryGenerator.generateCategory());
    assertThat(expected).hasOnlyFields("id", "title", "logoPath");
  }
}
