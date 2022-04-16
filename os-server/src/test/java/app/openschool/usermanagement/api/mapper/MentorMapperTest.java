package app.openschool.usermanagement.api.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import app.openschool.user.api.dto.MentorDto;
import app.openschool.user.api.mapper.MentorMapper;
import app.openschool.usermanagement.api.UserGenerator;
import org.junit.jupiter.api.Test;

public class MentorMapperTest {
  @Test
  public void toMentorDtoTest() {
    MentorDto expected = MentorMapper.toMentorDto(UserGenerator.generateUser());
    assertThat(expected)
        .hasOnlyFields(
            "name",
            "surname",
            "professionName",
            "companyName",
            "courseCount",
            "userImgPath",
            "emailPath",
            "linkedinPath");
  }
}
