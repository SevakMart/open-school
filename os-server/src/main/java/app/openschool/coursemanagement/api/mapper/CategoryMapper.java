package app.openschool.coursemanagement.api.mapper;

import app.openschool.coursemanagement.api.dto.CategoryDto;
import app.openschool.coursemanagement.entity.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static List<CategoryDto> toCategoryDtoList(List<CategoryEntity> categoryEntityList) {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntityList) {
            categoryDtoList.add(toCategoryDto(categoryEntity));
        }
        return categoryDtoList;
    }

    public static CategoryDto toCategoryDto(CategoryEntity categoryEntity) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setTitle(categoryEntity.getTitle());
        categoryDto.setLogoPath(categoryEntity.getLogoPath());
        return categoryDto;
    }

}
