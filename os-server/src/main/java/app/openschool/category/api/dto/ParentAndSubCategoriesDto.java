package app.openschool.category.api.dto;

import java.util.List;
import java.util.Map;

public class ParentAndSubCategoriesDto {

  private final Map<CategoryDto, List<CategoryDto>> parentAndSubCategoriesMap;

  public ParentAndSubCategoriesDto(Map<CategoryDto, List<CategoryDto>> parentAndSubCategoriesMap) {
    this.parentAndSubCategoriesMap = parentAndSubCategoriesMap;
  }

  public Map<CategoryDto, List<CategoryDto>> getParentAndSubCategoriesMap() {
    return parentAndSubCategoriesMap;
  }
}
