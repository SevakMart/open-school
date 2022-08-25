package app.openschool.course.module.api;

import app.openschool.course.module.item.api.CreateModuleItemRequest;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class CreateModuleRequest {

  @NotBlank(message = "")
  private String title;

  @NotBlank(message = "")
  private String description;

  @NotEmpty Set<CreateModuleItemRequest> createModuleItemRequests;

  public CreateModuleRequest() {}

  public CreateModuleRequest(
      String title, String description, Set<CreateModuleItemRequest> createModuleItemRequests) {
    this.title = title;
    this.description = description;
    this.createModuleItemRequests = createModuleItemRequests;
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

  public Set<CreateModuleItemRequest> getCreateModuleItemRequests() {
    return createModuleItemRequests;
  }

  public void setCreateModuleItemRequests(Set<CreateModuleItemRequest> createModuleItemRequests) {
    this.createModuleItemRequests = createModuleItemRequests;
  }
}
