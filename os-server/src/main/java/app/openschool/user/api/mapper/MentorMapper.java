package app.openschool.user.api.mapper;

import app.openschool.user.User;
import app.openschool.user.api.dto.MentorDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class MentorMapper {

  public static Page<MentorDto> toMentorDtoPage(Page<User> userPage) {
    List<User> userList = userPage.toList();
    List<MentorDto> mentorDtoList = new ArrayList<>();
    for (User user : userList) {
      mentorDtoList.add(toMentorDto(user));
    }
    return new PageImpl<>(mentorDtoList, userPage.getPageable(), userPage.getTotalElements());
  }

  public static MentorDto toMentorDto(User user) {
    return new MentorDto(
        user.getId(),
        user.getName(),
        user.getSurname(),
        user.getProfessionName(),
        user.getCompany().getCompanyName(),
        user.getCourseCount(),
        user.getUserImgPath(),
        user.getEmailPath(),
        user.getLinkedinPath());
  }
}
