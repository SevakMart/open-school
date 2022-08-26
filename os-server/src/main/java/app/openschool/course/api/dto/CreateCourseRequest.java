package app.openschool.course.api.dto;

import app.openschool.course.module.api.CreateModuleRequest;
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
  @Length(max = 1000, message = "{length.max.string}")
  private String description;

  @NotBlank(message = "{argument.required}")
  @Length(max = 1000, message = "{length.max.string}")
  private String goal;

  @NotNull(message = "{argument.required}")
  private Long category_id;

  @NotNull(message = "{argument.required}")
  private Integer difficulty_id;

  @NotNull(message = "{argument.required}")
  private Integer language_id;

  @NotNull(message = "{argument.required}")
  private Long mentor_id;

  @NotEmpty(message = "{argument.required}")
  private Set<Long> keyword_ids;

  @NotEmpty(message = "{argument.required}")
  Set<CreateModuleRequest> createModuleRequests;

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
    this.category_id = category_id;
    this.difficulty_id = difficulty_id;
    this.language_id = language_id;
    this.mentor_id = mentor_id;
    this.keyword_ids = keyword_ids;
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

  public Long getCategory_id() {
    return category_id;
  }

  public void setCategory_id(Long category_id) {
    this.category_id = category_id;
  }

  public Integer getDifficulty_id() {
    return difficulty_id;
  }

  public void setDifficulty_id(Integer difficulty_id) {
    this.difficulty_id = difficulty_id;
  }

  public Integer getLanguage_id() {
    return language_id;
  }

  public void setLanguage_id(Integer language_id) {
    this.language_id = language_id;
  }

  public Long getMentor_id() {
    return mentor_id;
  }

  public void setMentor_id(Long mentor_id) {
    this.mentor_id = mentor_id;
  }

  public Set<Long> getKeyword_ids() {
    return keyword_ids;
  }

  public void setKeyword_ids(Set<Long> keyword_ids) {
    this.keyword_ids = keyword_ids;
  }

  public Set<CreateModuleRequest> getCreateModuleRequests() {
    return createModuleRequests;
  }

  public void setCreateModuleRequests(Set<CreateModuleRequest> createModuleRequests) {
    this.createModuleRequests = createModuleRequests;
  }
}
