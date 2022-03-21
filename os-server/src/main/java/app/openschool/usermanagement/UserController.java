package app.openschool.usermanagement;

import static org.springframework.http.HttpStatus.CREATED;

import app.openschool.usermanagement.api.dto.MentorDto;
import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.api.dto.UserRegistrationHttpResponse;
import app.openschool.usermanagement.entities.User;
import java.util.Locale;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

  public static final String SUCCESSFULLY_REGISTERED = " you've successfully registered";
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<UserRegistrationHttpResponse> register(
      @Valid @RequestBody UserRegistrationDto userDto) {
    User user = userService.register(userDto);
    String message = user.getName() + SUCCESSFULLY_REGISTERED;
    UserRegistrationHttpResponse httpResponse =
        new UserRegistrationHttpResponse(message.toUpperCase(Locale.ROOT));

    return new ResponseEntity<>(httpResponse, CREATED);
  }

  @GetMapping("/mentors")
  public ResponseEntity<Page<MentorDto>> findAllMentors(Pageable pageable) {
    return ResponseEntity.ok(this.userService.findAllMentors(pageable));
  }

}
