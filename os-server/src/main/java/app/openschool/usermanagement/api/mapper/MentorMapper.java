package app.openschool.usermanagement.api.mapper;

import app.openschool.usermanagement.api.dto.MentorDto;
import app.openschool.usermanagement.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;


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
        return new MentorDto(user.getName(), user.getSurname(), user.getProfessionName(),
                user.getCompany().getCompanyName(),user.getCourseCount(), user.getUserImgPath(),
                user.getEmailPath(), user.getLinkedinPath());
    }
}
