package app.openschool.usermanagement.api.mapper;

import app.openschool.usermanagement.api.dto.MentorDto;
import app.openschool.usermanagement.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class MentorMapper {

    public static List<MentorDto> toMentorDtoList(List<UserEntity> userEntityList) {
        List<MentorDto> mentorDtoList = new ArrayList<>();
        for (UserEntity userEntity : userEntityList) {
            mentorDtoList.add(toMentorDto(userEntity));
        }
        return mentorDtoList;
    }

    public static MentorDto toMentorDto(UserEntity userEntity) {
        MentorDto mentorDto = new MentorDto();
        mentorDto.setName(userEntity.getName());
        mentorDto.setSurname(userEntity.getSurname());
        mentorDto.setProfessionName(userEntity.getProfessionName());
        mentorDto.setCompanyName(userEntity.getCompany().getCompanyName());
        mentorDto.setCourseCount(userEntity.getCourseCount());
        mentorDto.setUserImgPath(userEntity.getUserImgPath());
        mentorDto.setEmailPath(userEntity.getEmailPath());
        mentorDto.setUserImgPath(userEntity.getEmailPath());
        mentorDto.setLinkedinPath(userEntity.getLinkedinPath());
        return mentorDto;
    }

}
