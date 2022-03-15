package app.openschool.usermanagement.service;

import app.openschool.usermanagement.api.dto.MentorDto;
import app.openschool.usermanagement.api.mapper.MentorMapper;
import app.openschool.usermanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<MentorDto> findAllMentors() {
        return MentorMapper.toMentorDtoList(userRepository.findAllMentors());
    }

}
