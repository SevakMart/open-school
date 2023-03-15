package app.openschool.user.api.mapper;

import app.openschool.user.User;
import app.openschool.user.api.dto.UserDto;
import app.openschool.user.api.dto.UserWithSavedMentorsDto;
import java.util.stream.Collectors;

public class UserMapper {

  private UserMapper() {}

  public static UserWithSavedMentorsDto userToUserWithSavedMentorsDto(User user) {
    return new UserWithSavedMentorsDto(
        user.getId(),
        user.getEmail(),
        user.getMentors().stream().map(User::getName).collect(Collectors.toSet()));
  }

  public static UserDto toUserDto(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setName(user.getName());
    userDto.setSurname(user.getSurname());
    userDto.setEmail(user.getEmail());
    userDto.setProfessionName(user.getProfessionName());
    if (user.getCompany() != null) {
      userDto.setCompanyName(user.getCompany().getCompanyName());
    }
    return userDto;
  }
}
