package app.openschool.category.api.dto;

import java.util.Set;

public class SavePreferredCategoriesResponseDto {

  private Long userId;
  private Set<PreferredCategoryDto> categories;

  public SavePreferredCategoriesResponseDto(Long userId, Set<PreferredCategoryDto> categories) {
    this.userId = userId;
    this.categories = categories;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Set<PreferredCategoryDto> getCategories() {
    return categories;
  }

  public void setCategories(Set<PreferredCategoryDto> categories) {
    this.categories = categories;
  }
}
