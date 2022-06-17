package app.openschool.user.api;

import static org.assertj.core.api.Assertions.assertThat;

import app.openschool.user.api.dto.UserWithSavedMentorsDto;
import app.openschool.user.api.mapper.UserMapper;
import org.junit.jupiter.api.Test;

class UserMapperTest {

  @Test
  void userToUserWithSavedMentorsDto() {
    UserWithSavedMentorsDto expected =
        UserMapper.userToUserWithSavedMentorsDto(UserGenerator.generateUserWithSavedMentors());
    assertThat(expected).hasOnlyFields("userId", "email", "mentors");
  }
}
