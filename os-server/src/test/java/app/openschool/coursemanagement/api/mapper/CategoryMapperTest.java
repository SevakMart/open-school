package app.openschool.coursemanagement.api.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import app.openschool.coursemanagement.api.CategoryGenerator;
import app.openschool.coursemanagement.api.dto.CategoryDto;
import org.junit.jupiter.api.Test;

public class CategoryMapperTest {

  @Test
  public void toCategoryDtoTest() {
    CategoryDto expected = CategoryMapper.toCategoryDto(CategoryGenerator.generateCategory());
    assertThat(expected).hasOnlyFields("title", "logoPath", "subCategoryCount");
  }
}
