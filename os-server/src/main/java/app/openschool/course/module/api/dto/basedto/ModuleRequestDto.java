package app.openschool.course.module.api.dto.basedto;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public abstract class ModuleRequestDto {

  @NotBlank(message = "{argument.required}")
  @Length(max = 45, message = "{length.max.string}")
  private String title;

  @NotBlank(message = "{argument.required}")
  @Length(max = 1000, message = "{length.max.text}")
  private String description;

  protected ModuleRequestDto() {}

  protected ModuleRequestDto(String title, String description) {
    this.title = title;
    this.description = description;
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
}
