package app.openschool.usermanagement.api.mapper;

import app.openschool.usermanagement.api.dto.UserResponse;
import app.openschool.usermanagement.entities.User;

public class UserResponseMapper {

  public static UserResponse toUserResponse(User user) {
    return new UserResponse(user.getName(), user.getSurname(), user.getProfessionName(),
        user.getCourseCount(), user.getUserImgPath(), user.getRole().getType(), user.getCompany());
  }
}
