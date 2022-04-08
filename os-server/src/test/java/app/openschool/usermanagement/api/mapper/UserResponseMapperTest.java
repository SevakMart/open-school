package app.openschool.usermanagement.api.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import app.openschool.usermanagement.api.dto.UserResponse;
import app.openschool.usermanagement.entity.Role;
import app.openschool.usermanagement.entity.User;
import org.junit.jupiter.api.Test;

class UserResponseMapperTest {

  @Test
  void toUserResponse() {

    String name = "userName";
    String surname = "userSurname";
    String professionName = "userProfessionName";
    int courseCount = 123;
    String userImgPath = "userImgPath";
    String roleType = "student";

    User user = new User();
    user.setName(name);
    user.setSurname(surname);
    user.setProfessionName(professionName);
    user.setCourseCount(courseCount);
    user.setUserImgPath(userImgPath);
    user.setRole(new Role(1, roleType));

    UserResponse userResponse = new UserResponse();

    userResponse.setName(user.getName());
    userResponse.setSurname(user.getSurname());
    userResponse.setProfessionName(user.getProfessionName());
    userResponse.setCourseCount(user.getCourseCount());
    userResponse.setUserImgPath(user.getUserImgPath());
    userResponse.setRoleType(user.getRole().getType());
    userResponse.setCompany(user.getCompany());


    assertEquals(userResponse, UserResponseMapper.toUserResponse(user));
  }
}