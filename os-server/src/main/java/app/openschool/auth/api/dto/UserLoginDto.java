package app.openschool.auth.api.dto;

import app.openschool.user.company.Company;
import io.swagger.v3.oas.annotations.media.Schema;

public class UserLoginDto {
  @Schema(description = "User Id", example = "1")
  private Long id;

  @Schema(description = "User name", example = "John")
  private String name;

  @Schema(description = "Mentor surname", example = "Smith")
  private String surname;

  @Schema(description = "Profession of user", example = "Java Developer")
  private String professionName;

  @Schema(description = "Count of mentor courses", example = "5")
  private Integer courseCount;

  @Schema(description = "Path of user's image", example = "S3kds78")
  private String userImgPath;

  @Schema(description = "User Role", example = "Mentor")
  private String roleType;

  @Schema(description = "Name of the company where the user works", example = "Web Development")
  private Company company;

  public UserLoginDto(
      Long id,
      String name,
      String surname,
      String professionName,
      Integer courseCount,
      String userImgPath,
      String roleType,
      Company company) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.professionName = professionName;
    this.courseCount = courseCount;
    this.userImgPath = userImgPath;
    this.roleType = roleType;
    this.company = company;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getProfessionName() {
    return professionName;
  }

  public void setProfessionName(String professionName) {
    this.professionName = professionName;
  }

  public Integer getCourseCount() {
    return courseCount;
  }

  public void setCourseCount(Integer courseCount) {
    this.courseCount = courseCount;
  }

  public String getUserImgPath() {
    return userImgPath;
  }

  public void setUserImgPath(String userImgPath) {
    this.userImgPath = userImgPath;
  }

  public String getRoleType() {
    return roleType;
  }

  public void setRoleType(String roleType) {
    this.roleType = roleType;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }
}
