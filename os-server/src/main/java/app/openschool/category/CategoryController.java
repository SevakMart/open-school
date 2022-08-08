package app.openschool.category;

import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.dto.CreateCategoryRequest;
import app.openschool.category.api.dto.ModifyCategoryRequest;
import app.openschool.category.api.dto.ParentAndSubCategoriesDto;
import app.openschool.category.api.dto.PreferredCategoryDto;
import app.openschool.category.api.mapper.CategoryMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @Operation(
      summary = "find all parent categories",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponse(
      responseCode = "200",
      description =
          "Will return paginated list of parent categories or "
              + "empty list if no category has been found")
  @GetMapping("/parentCategories")
  public ResponseEntity<Page<CategoryDto>> findAllParentCategories(
      @Parameter(
              description =
                  "Includes parameters page, size, and sort which is not required. "
                      + "Page results page you want to retrieve (0..N). "
                      + "Size is count of records per page(1..N). "
                      + "Sorting criteria in the format: property(,asc|desc). "
                      + "Default sort order is ascending. "
                      + "Multiple sort criteria are supported.")
          Pageable pageable) {
    return ResponseEntity.ok(this.categoryService.findAllParentCategories(pageable));
  }

  @Operation(
      summary = "find all categories by title mapped by subcategories",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponse(
      responseCode = "200",
      description = "Returns parent categories and relevant subcategories")
  @GetMapping("/searched")
  public ResponseEntity<Map<String, List<PreferredCategoryDto>>> findCategoriesByTitle(
      @Parameter(description = "Category title") @RequestParam(required = false) String title) {
    return ResponseEntity.ok(categoryService.findCategoriesByTitle(title));
  }

  @Operation(summary = "find all categories", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponse(
      responseCode = "200",
      description = "Returns all parent categories and relevant subcategories")
  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping
  public ResponseEntity<ParentAndSubCategoriesDto> findAll() {
    return ResponseEntity.ok(categoryService.findAll());
  }

  @Operation(summary = "find category", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponse(responseCode = "200", description = "Returns parent category or subcategory by id")
  @GetMapping("/{id}")
  public ResponseEntity<CategoryDto> findById(@PathVariable(value = "id") Long categoryId) {
    return ResponseEntity.ok(CategoryMapper.toCategoryDto(categoryService.findById(categoryId)));
  }

  @Operation(
      summary = "add categories or subcategories",
      security = @SecurityRequirement(name = "bearerAuth"))
  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  @SuppressWarnings("rawtypes")
  public ResponseEntity add(
      @Valid @RequestBody CreateCategoryRequest request, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest()
          .body(
              bindingResult.getAllErrors().stream()
                  .map(DefaultMessageSourceResolvable::getDefaultMessage));
    }
    return ResponseEntity.ok(CategoryMapper.toCategoryDto(categoryService.add(request)));
  }

  @Operation(
      summary = "modify categories or subcategories",
      security = @SecurityRequirement(name = "bearerAuth"))
  @PreAuthorize("hasAuthority('ADMIN')")
  @PatchMapping("/{id}")
  @SuppressWarnings("rawtypes")
  public ResponseEntity modify(
      @PathVariable(value = "id") Long categoryId,
      @Valid @RequestBody ModifyCategoryRequest request,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest()
          .body(
              bindingResult.getAllErrors().stream()
                  .map(DefaultMessageSourceResolvable::getDefaultMessage));
    }
    return ResponseEntity.ok(
        CategoryMapper.toCategoryDto(categoryService.modify(categoryId, request)));
  }

  @Operation(
      summary = "delete categories or subcategories",
      security = @SecurityRequirement(name = "bearerAuth"))
  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> delete(
      @PathVariable(value = "id") Long categoryId, Locale locale) {
    categoryService.delete(categoryId, locale);
    return ResponseEntity.ok().build();
  }
}
