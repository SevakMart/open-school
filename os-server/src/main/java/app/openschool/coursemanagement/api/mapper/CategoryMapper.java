package app.openschool.coursemanagement.api.mapper;

import app.openschool.coursemanagement.api.dto.CategoryDto;
import app.openschool.coursemanagement.entity.Category;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class CategoryMapper {

  public static Page<CategoryDto> toCategoryDtoPage(Page<Category> categoryPage) {
    List<Category> categoryList = categoryPage.toList();
    List<CategoryDto> categoryDtoList = new ArrayList<>();
    for (Category category : categoryList) {
      categoryDtoList.add(toCategoryDto(category));
    }
    return new PageImpl<>(
        categoryDtoList, categoryPage.getPageable(), categoryPage.getTotalElements());
  }

  public static CategoryDto toCategoryDto(Category category) {
    return new CategoryDto(
        category.getTitle(), category.getLogoPath(), category.getSubCategoryCount());
  }
}
