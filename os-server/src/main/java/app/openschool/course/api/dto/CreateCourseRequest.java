package app.openschool.course.api.dto;

import app.openschool.course.module.api.dto.CreateModuleRequest;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class CreateCourseRequest {

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

  @NotNull(message = "{argument.required}")
  private Long mentorId;

  @NotEmpty(message = "{argument.required}")
  private Set<Long> keywordIds;

  @NotEmpty(message = "{argument.required}")
  private Set<CreateModuleRequest> createModuleRequests;

  public CreateCourseRequest() {}

  public CreateCourseRequest(
      String title,
      String description,
      String goal,
      Long category_id,
      Integer difficulty_id,
      Integer language_id,
      Long mentor_id,
      Set<Long> keyword_ids,
      Set<CreateModuleRequest> createModuleRequests) {
    this.title = title;
    this.description = description;
    this.goal = goal;
    this.categoryId = category_id;
    this.difficultyId = difficulty_id;
    this.languageId = language_id;
    this.mentorId = mentor_id;
    this.keywordIds = keyword_ids;
    this.createModuleRequests = createModuleRequests;
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

  public Long getMentorId() {
    return mentorId;
  }

  public void setMentorId(Long mentorId) {
    this.mentorId = mentorId;
  }

  public Set<Long> getKeywordIds() {
    return keywordIds;
  }

  public void setKeywordIds(Set<Long> keywordIds) {
    this.keywordIds = keywordIds;
  }

  public Set<CreateModuleRequest> getCreateModuleRequests() {
    return createModuleRequests;
  }

  public void setCreateModuleRequests(Set<CreateModuleRequest> createModuleRequests) {
    this.createModuleRequests = createModuleRequests;
  }
}
