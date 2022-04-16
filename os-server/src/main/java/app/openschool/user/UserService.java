package app.openschool.user;

import app.openschool.user.api.dto.MentorDto;
import app.openschool.user.api.dto.UserLoginDto;
import app.openschool.user.api.dto.UserRegistrationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  User register(UserRegistrationDto userDto);

  User findUserByEmail(String email);

  Page<MentorDto> findAllMentors(Pageable pageable);

  UserLoginDto login(String userEmail);
}
