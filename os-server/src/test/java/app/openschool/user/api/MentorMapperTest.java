package app.openschool.user.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import app.openschool.user.api.dto.MentorDto;
import app.openschool.user.api.mapper.MentorMapper;
import org.junit.jupiter.api.Test;

public class MentorMapperTest {
  @Test
  public void toMentorDtoTest() {
    MentorDto expected = MentorMapper.toMentorDto(UserGenerator.generateUser());
    assertThat(expected)
        .hasOnlyFields(
            "id",
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
