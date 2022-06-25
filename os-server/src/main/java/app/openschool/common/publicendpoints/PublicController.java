package app.openschool.common.publicendpoints;

import app.openschool.category.CategoryService;
import app.openschool.category.api.dto.CategoryDto;
import app.openschool.user.UserService;
import app.openschool.user.api.dto.MentorDto;
import app.openschool.user.api.mapper.MentorMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

  @Operation(summary = "find all mentors")
  @ApiResponse(
      responseCode = "200",
      description =
          "Will return paginated list of mentors or empty list if no mentor have been found")
  @GetMapping("/users/mentors")
  public ResponseEntity<Page<MentorDto>> findAllMentors(
      @Parameter(
              description =
                  "Includes parameters page, size, and sort which is not required. "
                      + "Page results page you want to retrieve (0..N). Size is count of records per page(1..N). "
                      + "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. "
                      + "Multiple sort criteria are supported.")
          Pageable pageable) {
    return ResponseEntity.ok(
        MentorMapper.toMentorDtoPage(this.userService.findAllMentors(pageable)));
  }

  @Operation(summary = "find all categories")
  @ApiResponse(
      responseCode = "200",
      description =
          "Will return paginated list of parent categories or empty list if no category have been found")
  @GetMapping("/categories")
  public ResponseEntity<Page<CategoryDto>> findAllCategories(
      @Parameter(
              description =
                  "Includes parameters page, size, and sort which is not required. "
                      + "Page results page you want to retrieve (0..N). Size is count of records per page(1..N). "
                      + "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. "
                      + "Multiple sort criteria are supported.")
          Pageable pageable) {
    return ResponseEntity.ok(this.categoryService.findAllCategories(pageable));
  }
}
