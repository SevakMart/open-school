package app.openschool.usermanagement.api.mapper;

import app.openschool.coursemanagement.api.mapper.CategoryMapper;
import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.entity.Role;
import app.openschool.usermanagement.entity.User;
import app.openschool.usermanagement.entities.Role;
import app.openschool.usermanagement.entities.User;
import java.util.Set;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserRegistrationMapper {

  private static final String ROLE_USER = "STUDENT";

  public static User userRegistrationDtoToUser(
      UserRegistrationDto userDto, BCryptPasswordEncoder passwordEncoder) {
    User user =
        new User(
            userDto.getFirstName(),
            userDto.getEmail(),
            passwordEncoder.encode(userDto.getPassword()),
            new Role(1, ROLE_USER));

    Set<Long> categoryIdSet = userDto.getCategoryIdSet();
    if (categoryIdSet != null) {
      user.setCategories(CategoryMapper.categoryIdSetToCategorySet(categoryIdSet));
    }

    return user;
  }
}
