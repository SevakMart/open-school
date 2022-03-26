package app.openschool.coursemanagement.api.mapper;

import app.openschool.coursemanagement.api.CategoryGenerator;
import app.openschool.coursemanagement.api.dto.CategoryDto;
import app.openschool.coursemanagement.api.mapper.CategoryMapper;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CategoryMapperTest {

    @Test
    public void toCategoryDtoTest() {
        CategoryDto expected = CategoryMapper.toCategoryDto(CategoryGenerator.generateCategory());
        assertThat(expected).hasOnlyFields("title", "logoPath", "subCategoryCount");
    }

}
