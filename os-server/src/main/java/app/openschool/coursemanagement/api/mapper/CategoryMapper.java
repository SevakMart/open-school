package app.openschool.coursemanagement.api.mapper;

import app.openschool.coursemanagement.api.dto.CategoryDto;
import app.openschool.coursemanagement.api.dto.PreferredCategoryDto;
import app.openschool.coursemanagement.entity.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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

  public static PreferredCategoryDto toPreferredCategoryDto(Category category) {
    return new PreferredCategoryDto(category.getId(), category.getTitle());
  }

  public static Set<Category> categoryIdSetToCategorySet(Set<Long> categoryIdSet) {
    return categoryIdSet.stream().map(Category::new).collect(Collectors.toSet());
  }
}
