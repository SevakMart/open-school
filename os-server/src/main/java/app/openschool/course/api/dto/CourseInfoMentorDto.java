package app.openschool.course.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class CourseInfoMentorDto {

  @Schema(description = "Mentor name", example = "John")
  private String name;

  @Schema(description = "Mentor surname", example = "Smith")
  private String surname;

  @Schema(description = "Path oh the mentor's image", example = "S3lc5")
  private String userImgPath;

  @Schema(description = "Path oh the mentor's linkedin", example = "S3l55")
  private String linkedinPath;

  public CourseInfoMentorDto(String name, String surname, String userImgPath, String linkedinPath) {
    this.name = name;
    this.surname = surname;
    this.userImgPath = userImgPath;
    this.linkedinPath = linkedinPath;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public String getUserImgPath() {
    return userImgPath;
  }

  public String getLinkedinPath() {
    return linkedinPath;
  }
}
