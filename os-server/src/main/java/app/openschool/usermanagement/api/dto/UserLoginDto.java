package app.openschool.usermanagement.api.dto;

import app.openschool.usermanagement.entity.Company;

public class UserLoginDto {

  private String name;
  private String surname;
  private String professionName;
  private Integer courseCount;
  private String userImgPath;
  private String roleType;
  private Company company;

  public UserLoginDto(
      String name,
      String surname,
      String professionName,
      Integer courseCount,
      String userImgPath,
      String roleType,
      Company company) {
    this.name = name;
    this.surname = surname;
    this.professionName = professionName;
    this.courseCount = courseCount;
    this.userImgPath = userImgPath;
    this.roleType = roleType;
    this.company = company;
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
