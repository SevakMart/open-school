package app.openschool.usermanagement.api.dto;

public class MentorDto {

  private String name;

  private String surname;

  private String professionName;

  private String companyName;

  private Integer courseCount;

  private String userImgPath;

  private String emailPath;

  private String linkedinPath;

  public MentorDto() {}

  public MentorDto(
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
