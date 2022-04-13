package app.openschool.usermanagement.api.mapper;

import app.openschool.usermanagement.api.dto.UserLoginDto;
import app.openschool.usermanagement.entity.User;

public class UserLoginMapper {
  public static UserLoginDto toUserLoginDto(User user) {
    return new UserLoginDto(
        user.getName(),
        user.getSurname(),
        user.getProfessionName(),
        user.getCourseCount(),
        user.getUserImgPath(),
        user.getRole().getType(),
        user.getCompany());
  }
}
