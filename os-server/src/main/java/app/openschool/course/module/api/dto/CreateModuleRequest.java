package app.openschool.course.module.api.dto;

import app.openschool.course.module.api.dto.basedto.ModuleRequestDto;
import app.openschool.course.module.item.api.dto.CreateModuleItemRequest;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateModuleRequest extends ModuleRequestDto {

  @NotNull(message = "{argument.required}")
  private Long courseId;
  @NotEmpty(message = "{argument.required}")
  private Set<CreateModuleItemRequest> createModuleItemRequests;

  public CreateModuleRequest() {}

  public CreateModuleRequest(
      String title, String description, Set<CreateModuleItemRequest> createModuleItemRequests) {
    super(title, description);
    this.createModuleItemRequests = createModuleItemRequests;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public Set<CreateModuleItemRequest> getCreateModuleItemRequests() {
    return createModuleItemRequests;
  }

  public void setCreateModuleItemRequests(Set<CreateModuleItemRequest> createModuleItemRequests) {
    this.createModuleItemRequests = createModuleItemRequests;
  }
}
