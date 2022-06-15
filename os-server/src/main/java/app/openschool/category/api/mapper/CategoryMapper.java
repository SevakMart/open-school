package app.openschool.category.api.mapper;

import app.openschool.category.Category;
import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.dto.PreferredCategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CategoryMapper {

  private CategoryMapper() {
    throw new IllegalStateException("Can not instantiate utility class");
  }

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

  public static List<PreferredCategoryDto> toPreferredCategoryDtos(
      Collection<Category> categories) {
    return categories.stream()
        .map(CategoryMapper::toPreferredCategoryDto)
        .collect(Collectors.toList());
  }

  public static Set<Category> categoryIdSetToCategorySet(Set<Long> categoryIdSet) {
    return categoryIdSet.stream().map(Category::new).collect(Collectors.toSet());
  }
}
