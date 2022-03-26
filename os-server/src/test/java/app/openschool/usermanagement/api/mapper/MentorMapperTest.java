package app.openschool.usermanagement.api.mapper;

import app.openschool.usermanagement.api.UserGenerator;
import app.openschool.usermanagement.api.dto.MentorDto;
import app.openschool.usermanagement.api.mapper.MentorMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class MentorMapperTest {
    @Test
    public void toMentorDtoTest() {
        MentorDto expected = MentorMapper.toMentorDto(UserGenerator.generateUser());
        assertThat(expected).hasOnlyFields("name", "surname", "professionName", "companyName", "courseCount",
                "userImgPath", "emailPath", "linkedinPath");
    }
}
