package app.openschool.course.api.dto;

import app.openschool.course.api.dto.basedto.CourseRequestDto;
import app.openschool.course.module.api.dto.CreateModuleRequest;
import java.util.Set;
import javax.validation.constraints.NotEmpty;

public class CreateCourseRequest extends CourseRequestDto {

  @NotEmpty(message = "{argument.required}")
  private Set<CreateModuleRequest> createModuleRequests;

  public CreateCourseRequest() {}

  public Set<CreateModuleRequest> getCreateModuleRequests() {
    return createModuleRequests;
  }

  public void setCreateModuleRequests(Set<CreateModuleRequest> createModuleRequests) {
    this.createModuleRequests = createModuleRequests;
  }
}
