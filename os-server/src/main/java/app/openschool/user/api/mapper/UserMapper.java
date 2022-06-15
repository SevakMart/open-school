package app.openschool.user.api.mapper;

import app.openschool.user.User;
import app.openschool.user.api.dto.UserWithSavedMentorsDto;
import java.util.stream.Collectors;

public class UserMapper {

  public static UserWithSavedMentorsDto userToUserWithSavedMentorsDto(User user) {
    return new UserWithSavedMentorsDto(
        user.getId(),
        user.getEmail(),
        user.getMentors().stream().map(User::getName).collect(Collectors.toSet()));
  }
}
