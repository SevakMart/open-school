package app.openschool.course.module.api.dto;

import app.openschool.course.module.item.api.dto.CreateModuleItemRequest;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class CreateModuleRequest {

  @NotBlank(message = "{argument.required}")
  @Length(max = 45, message = "{length.max.string}")
  private String title;

  @NotBlank(message = "{argument.required}")
  @Length(max = 1000, message = "{length.max.text}")
  private String description;

  @NotEmpty(message = "{argument.required}")
  Set<CreateModuleItemRequest> createModuleItemRequests;

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
