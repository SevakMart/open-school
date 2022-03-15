package app.openschool.usermanagement.service;

import app.openschool.usermanagement.api.dto.MentorDto;

import java.util.List;

public interface UserService {

    List<MentorDto> findAllMentors();

}
