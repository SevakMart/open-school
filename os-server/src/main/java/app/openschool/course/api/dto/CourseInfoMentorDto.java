package app.openschool.course.api.dto;

public class CourseInfoMentorDto {

  private String name;
  private String surname;
  private String userImgPath;
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
