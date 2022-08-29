package app.openschool.course.api.dto;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class UpdateCourseRequest {

  @NotBlank(message = "Argument is required")
  @Length(max = 45, message = "{length.max.string}")
  private String title;

  @NotBlank(message = "{argument.required}")
  @Length(max = 1000, message = "{length.max.string}")
  private String description;

  @NotBlank(message = "{argument.required}")
  @Length(max = 1000, message = "{length.max.string}")
  private String goal;

  @NotNull(message = "{argument.required}")
  private Long category_id;

  @NotNull(message = "{argument.required}")
  private Integer difficulty;

  @NotNull(message = "{argument.required}")
  private Integer language_id;

  @NotEmpty(message = "{argument.required}")
  private Set<Long> keywordIds;

  public UpdateCourseRequest() {}

  public UpdateCourseRequest(
      String title,
      String description,
      String goal,
      Long category_id,
      Integer difficulty,
      Integer language_id,
      Set<Long> keywordIds) {
    this.title = title;
    this.description = description;
    this.goal = goal;
    this.category_id = category_id;
    this.difficulty = difficulty;
    this.language_id = language_id;
    this.keywordIds = keywordIds;
  }

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

  public Long getCategory_id() {
    return category_id;
  }

  public void setCategory_id(Long category_id) {
    this.category_id = category_id;
  }

  public Integer getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Integer difficulty) {
    this.difficulty = difficulty;
  }

  public Integer getLanguage_id() {
    return language_id;
  }

  public void setLanguage_id(Integer language_id) {
    this.language_id = language_id;
  }

  public Set<Long> getKeywordIds() {
    return keywordIds;
  }

  public void setKeywordIds(Set<Long> keywordIds) {
    this.keywordIds = keywordIds;
  }
}
