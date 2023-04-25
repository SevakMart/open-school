package app.openschool.course.api.dto.basedto;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public abstract class CourseRequestDto {

  @NotBlank(message = "{argument.required}")
  @Length(max = 45, message = "{length.max.string}")
  private String title;

  @NotBlank(message = "{argument.required}")
  @Length(max = 1000, message = "{length.max.text}")
  private String description;

  @NotBlank(message = "{argument.required}")
  @Length(max = 1000, message = "{length.max.text}")
  private String goal;

  @NotNull(message = "{argument.required}")
  private Long categoryId;

  @NotNull(message = "{argument.required}")
  private Integer difficultyId;

  @NotNull(message = "{argument.required}")
  private Integer languageId;

  @NotEmpty(message = "{argument.required}")
  private Set<Long> keywordIds;

  protected CourseRequestDto() {}

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getGoal() {
    return goal;
  }

  public void setGoal(String goal) {
    this.goal = goal;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public Integer getDifficultyId() {
    return difficultyId;
  }

  public void setDifficultyId(Integer difficultyId) {
    this.difficultyId = difficultyId;
  }

  public Integer getLanguageId() {
    return languageId;
  }

  public void setLanguageId(Integer languageId) {
    this.languageId = languageId;
  }

  public Set<Long> getKeywordIds() {
    return keywordIds;
  }

  public void setKeywordIds(Set<Long> keywordIds) {
    this.keywordIds = keywordIds;
  }
}
