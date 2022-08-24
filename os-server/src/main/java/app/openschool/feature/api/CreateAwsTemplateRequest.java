package app.openschool.feature.api;

import javax.validation.constraints.NotBlank;

public class CreateAwsTemplateRequest {

  @NotBlank(message = "{template.name.blank}")
  private String templateName;

  @NotBlank(message = "{template.subject.blank}")
  private String subjectPart;

  @NotBlank(message = "template.body.blank")
  private String htmlPart;

  public CreateAwsTemplateRequest() {}

  public CreateAwsTemplateRequest(String templateName, String subjectPart, String htmlPart) {
    this.templateName = templateName;
    this.subjectPart = subjectPart;
    this.htmlPart = htmlPart;
  }

  public String getTemplateName() {
    return templateName;
  }

  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  public String getSubjectPart() {
    return subjectPart;
  }

  public void setSubjectPart(String subjectPart) {
    this.subjectPart = subjectPart;
  }

  public String getHtmlPart() {
    return htmlPart;
  }

  public void setHtmlPart(String htmlPart) {
    this.htmlPart = htmlPart;
  }
}
