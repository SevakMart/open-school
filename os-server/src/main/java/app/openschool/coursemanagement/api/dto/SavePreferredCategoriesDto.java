package app.openschool.coursemanagement.api.dto;

import java.util.Set;

public class SavePreferredCategoriesDto {

  private Long userId;
  private Set<Long> categoriesIdSet;

  public SavePreferredCategoriesDto(Long userId, Set<Long> categoriesIdSet) {
    this.userId = userId;
    this.categoriesIdSet = categoriesIdSet;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Set<Long> getCategoriesIdSet() {
    return categoriesIdSet;
  }

  public void setCategoriesIdSet(Set<Long> categoriesIdSet) {
    this.categoriesIdSet = categoriesIdSet;
  }
}
