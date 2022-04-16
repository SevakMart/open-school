package app.openschool.user.api.mapper;

import app.openschool.user.User;
import app.openschool.user.api.dto.UserLoginDto;

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
