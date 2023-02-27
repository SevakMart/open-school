package app.openschool.course.api.dto;

import app.openschool.course.api.dto.basedto.CourseRequestDto;
import app.openschool.course.module.api.dto.CreateModuleRequest;
import java.util.Set;
import javax.validation.constraints.NotEmpty;

public class CreateCourseRequest extends CourseRequestDto {

  @NotEmpty(message = "{argument.required}")
  private Set<CreateModuleRequest> createModuleRequests;

  public CreateCourseRequest() {}

  public CreateCourseRequest(
      String title,
      String description,
      String goal,
      Long categoryId,
      Integer difficultyId,
      Integer languageId,
      Set<Long> keywordIds,
      Set<CreateModuleRequest> createModuleRequests) {
    super(title, description, goal, categoryId, difficultyId, languageId, keywordIds);
    this.createModuleRequests = createModuleRequests;
  }

  public Set<CreateModuleRequest> getCreateModuleRequests() {
    return createModuleRequests;
  }

  public void setCreateModuleRequests(Set<CreateModuleRequest> createModuleRequests) {
    this.createModuleRequests = createModuleRequests;
  }
}
