package app.openschool.category;

import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.dto.PreferredCategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  @Operation(summary = "find all categories", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Page<CategoryDto>> getAllCategories(Pageable pageable) {
    return ResponseEntity.ok(this.categoryService.findAllCategories(pageable));
  }

  @GetMapping("/public")
  @Operation(summary = "find all categories")
  public ResponseEntity<Page<CategoryDto>> findAllCategories(Pageable pageable) {
    return ResponseEntity.ok(this.categoryService.findAllCategories(pageable));
  }

  @GetMapping("/subcategories")
  @Operation(
      summary = "find all categories by title mapped by subcategories",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Map<String, List<PreferredCategoryDto>>> findCategoriesByTitle(
      String title) {
    return ResponseEntity.ok(categoryService.findCategoriesByTitle(title));
  }
}
