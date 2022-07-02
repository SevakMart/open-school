package app.openschool.user.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class MentorDto {

  @Schema(description = "Mentor Id", example = "1")
  private Long id;

  @Schema(description = "Mentor name", example = "John")
  private String name;

  @Schema(description = "Mentor surname", example = "Smith")
  private String surname;

  @Schema(description = "Profession of mentor", example = "Java Developer")
  private String professionName;

  @Schema(description = "Name of the company where the mentor works", example = "Web Development")
  private String companyName;

  @Schema(description = "Count of mentor courses", example = "5")
  private Integer courseCount;

  @Schema(description = "Path of user's image", example = "S3kds78")
  private String userImgPath;

  @Schema(description = "Path of user's email", example = "S3kds79")
  private String emailPath;

  @Schema(description = "Path of user's linkedin", example = "S3kds80")
  private String linkedinPath;

  public MentorDto() {}

  public MentorDto(
      Long id,
      String name,
      String surname,
      String professionName,
      String companyName,
      Integer courseCount,
      String userImgPath,
      String emailPath,
      String linkedinPath) {
    this.name = name;
    this.surname = surname;
    this.professionName = professionName;
    this.companyName = companyName;
    this.courseCount = courseCount;
    this.userImgPath = userImgPath;
    this.emailPath = emailPath;
    this.linkedinPath = linkedinPath;
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public String getProfessionName() {
    return professionName;
  }

  public String getCompanyName() {
    return companyName;
  }

  public Integer getCourseCount() {
    return courseCount;
  }

  public String getUserImgPath() {
    return userImgPath;
  }

  public String getEmailPath() {
    return emailPath;
  }

  public String getLinkedinPath() {
    return linkedinPath;
  }
}
