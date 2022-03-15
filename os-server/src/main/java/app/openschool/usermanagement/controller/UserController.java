package app.openschool.usermanagement.controller;


import app.openschool.usermanagement.api.dto.MentorDto;
import app.openschool.usermanagement.service.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/mentors")
    public ResponseEntity<List<MentorDto>> findAllMentors() {
        List<MentorDto> allMentors = this.userService.findAllMentors();
        if (allMentors == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allMentors);
    }

}
