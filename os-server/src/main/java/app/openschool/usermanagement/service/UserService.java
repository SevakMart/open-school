package app.openschool.usermanagement.service;

import app.openschool.usermanagement.api.dto.MentorDto;
import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  User register(UserRegistrationDto userDto);

  User findUserByEmail(String email);

  Page<MentorDto> findAllMentors(Pageable pageable);
}
