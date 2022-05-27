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

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getUserImgPath() {
    return userImgPath;
  }

  public void setUserImgPath(String userImgPath) {
    this.userImgPath = userImgPath;
  }

  public String getLinkedinPath() {
    return linkedinPath;
  }

  public void setLinkedinPath(String linkedinPath) {
    this.linkedinPath = linkedinPath;
  }
}
