package app.openschool.auth.api.mapper;

import app.openschool.auth.api.dto.UserLoginDto;
import app.openschool.user.User;

public class UserLoginMapper {
  public static UserLoginDto toUserLoginDto(User user) {
    return new UserLoginDto(
        user.getId(),
        user.getName(),
        user.getSurname(),
        user.getProfessionName(),
        user.getCourseCount(),
        user.getUserImgPath(),
        user.getRole().getType(),
        user.getCompany());
  }
}
