package app.openschool.user.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;

public class UserDto {

  @Schema(description = "User Id", example = "1")
  private Long id;

  @Schema(description = "User name", example = "Emilia")
  private String name;

  @Schema(description = "User surname", example = "Watson")
  private String surname;

  @Schema(description = "User email", example = "emilia@asd11")
  private String email;

  @Schema(description = "Profession of User", example = "Java developer")
  private String professionName;

  @Schema(description = "Name of the company where the mentor works", example = "Web Development")
  private String companyName;

  public UserDto() {}

  public UserDto(
      Long id,
      String name,
      String surname,
      String email,
      String professionName,
      String companyName) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.professionName = professionName;
    this.companyName = companyName;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getProfessionName() {
    return professionName;
  }

  public void setProfessionName(String professionName) {
    this.professionName = professionName;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDto userDto = (UserDto) o;
    return Objects.equals(id, userDto.id)
        && Objects.equals(name, userDto.name)
        && Objects.equals(surname, userDto.surname)
        && Objects.equals(email, userDto.email)
        && Objects.equals(professionName, userDto.professionName)
        && Objects.equals(companyName, userDto.companyName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, surname, email, professionName, companyName);
  }
}
