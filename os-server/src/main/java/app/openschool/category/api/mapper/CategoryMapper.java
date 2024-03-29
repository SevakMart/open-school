package app.openschool.category.api.mapper;

import app.openschool.category.Category;
import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.dto.PreferredCategoryDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    return new CategoryDto(category.getId(), category.getTitle(), category.getLogoPath());
  }

  public static PreferredCategoryDto toPreferredCategoryDto(Category category) {
    return new PreferredCategoryDto(category.getId(), category.getTitle());
  }

  public static List<PreferredCategoryDto> toPreferredCategoryDtoList(Set<Category> categories) {
    List<PreferredCategoryDto> preferredCategoryDtoList = new ArrayList<>();
    for (Category category : categories) {
      preferredCategoryDtoList.add(toPreferredCategoryDto(category));
    }
    return preferredCategoryDtoList;
  }
}
