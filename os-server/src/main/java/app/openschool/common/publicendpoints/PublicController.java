package app.openschool.common.publicendpoints;

import app.openschool.category.CategoryService;
import app.openschool.category.api.dto.CategoryDto;
import app.openschool.user.UserService;
import app.openschool.user.api.dto.MentorDto;
import app.openschool.user.api.mapper.MentorMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {

  private final UserService userService;
  private final CategoryService categoryService;

  public PublicController(UserService userService, CategoryService categoryService) {
    this.userService = userService;
    this.categoryService = categoryService;
  }

  @GetMapping("/users/mentors")
  @Operation(summary = "find all mentors")
  public ResponseEntity<Page<MentorDto>> findAllMentors(Pageable pageable) {
    return ResponseEntity.ok(MentorMapper.toMentorDtoPage(userService.findAllMentors(pageable)));
  }

  @GetMapping("/categories")
  @Operation(summary = "find all categories")
  public ResponseEntity<Page<CategoryDto>> findAllCategories(Pageable pageable) {
    return ResponseEntity.ok(this.categoryService.findAllCategories(pageable));
  }
}
