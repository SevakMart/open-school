package app.openschool.usermanagement.service;

import app.openschool.usermanagement.api.dto.MentorDto;
import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.api.exceptions.EmailAlreadyExistException;
import app.openschool.usermanagement.api.mapper.MentorMapper;
import app.openschool.usermanagement.api.mapper.UserRegistrationMapper;
import app.openschool.usermanagement.entity.User;
import app.openschool.usermanagement.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private static final String EMAIL_ALREADY_EXISTS = "User with this email already exists";
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User register(UserRegistrationDto userDto) {

    if (emailAlreadyExist(userDto.getEmail())) {
      throw new EmailAlreadyExistException(EMAIL_ALREADY_EXISTS);
    }

    User user = UserRegistrationMapper.userRegistrationDtoToUser(userDto, passwordEncoder);
    return userRepository.save(user);
  }

  @Override
  public User findUserByEmail(String email) {
    return userRepository.findUserByEmail(email);
  }

  private boolean emailAlreadyExist(String email) {
    return findUserByEmail(email) != null;
  }

  @Override
  public Page<MentorDto> findAllMentors(Pageable pageable) {
    return MentorMapper.toMentorDtoPage(userRepository.findAllMentors(pageable));
  }
}
