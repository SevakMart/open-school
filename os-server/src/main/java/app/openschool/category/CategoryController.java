package app.openschool.category;

import static org.springframework.http.HttpStatus.CREATED;

import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.dto.CreateCategoryRequest;
import app.openschool.category.api.dto.ModifyCategoryDataRequest;
import app.openschool.category.api.dto.ModifyCategoryImageRequest;
import app.openschool.category.api.dto.ParentAndSubCategoriesDto;
import app.openschool.category.api.dto.PreferredCategoryDto;
import app.openschool.category.api.mapper.CategoryMapper;
import app.openschool.common.response.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
  @GetMapping("/subcategories")
  public ResponseEntity<Map<String, List<PreferredCategoryDto>>> findCategoriesByTitle(
      @Parameter(description = "Category title") @RequestParam(required = false) String title) {
    return ResponseEntity.ok(categoryService.findCategoriesByTitle(title));
  }

  @Operation(
      summary = "find all categories and relevant subcategories",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponse(
      responseCode = "200",
      description = "Returns all parent categories and relevant subcategories")
  @GetMapping
  public ResponseEntity<ParentAndSubCategoriesDto> findAll() {
    return ResponseEntity.ok(categoryService.findAll());
  }

  @Operation(
      summary = "find category or subcategory",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Returns parent category or subcategory"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid category id supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @GetMapping("/{id}")
  public ResponseEntity<CategoryDto> findById(
      @Parameter(description = "Id of category which will be returned") @PathVariable(value = "id")
          Long categoryId) {
    return ResponseEntity.ok(CategoryMapper.toCategoryDto(categoryService.findById(categoryId)));
  }

  @Operation(
      summary = "add categories or subcategories",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Creates new category and returns that"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid or existing category title supplied or image not provided",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only user with admin role has access to this method",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<CategoryDto> add(
      @Parameter(
              description =
                  "Request object for creating new category, which includes title that must be unique, "
                      + "id of parent category, which is necessary to pass only when"
                      + " will be created a subcategory and image of category")
          @Valid
          @ModelAttribute
          CreateCategoryRequest createCategoryRequest) {
    return ResponseEntity.status(CREATED)
        .body(CategoryMapper.toCategoryDto(categoryService.add(createCategoryRequest)));
  }

  @Operation(
      summary = "modify data of categories or subcategories",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description =
                "Modifies provided field or fields of category and returns updated category"),
        @ApiResponse(
            responseCode = "400",
            description =
                "Invalid category id, invalid new parent category id or"
                    + " invalid or existing category title supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only user with admin role has access to this method",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAuthority('ADMIN')")
  @PatchMapping(value = "/{id}")
  public ResponseEntity<CategoryDto> updateData(
      @Parameter(description = "Id of category which fields will be modified")
          @PathVariable(value = "id")
          Long categoryId,
      @Parameter(
              description =
                  "Request object for modifying category, which includes new title that must be unique and "
                      + "id of new parent category. Both parameters are not required")
          @Valid
          @RequestBody
          ModifyCategoryDataRequest request) {
    return ResponseEntity.ok()
        .body(CategoryMapper.toCategoryDto(categoryService.updateData(categoryId, request)));
  }

  @Operation(
      summary = "change image of categories or subcategories",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Modifies image of category and returns updated category"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid category id supplied or new image not provided",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only user with admin role has access to this method",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAuthority('ADMIN')")
  @PatchMapping(
      value = "/{id}/image",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<CategoryDto> updateImage(
      @Parameter(description = "Id of category which image will be modified")
          @PathVariable(value = "id")
          Long categoryId,
      @Parameter(
              description =
                  "Request object for modifying image of category, which includes new image.")
          @Valid
          @ModelAttribute
          ModifyCategoryImageRequest request) {
    return ResponseEntity.ok()
        .body(CategoryMapper.toCategoryDto(categoryService.updateImage(categoryId, request)));
  }

  @Operation(
      summary = "delete categories or subcategories",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "Category was deleted",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "400",
            description =
                "Invalid category id supplied, or provided id of parent category. "
                    + "Deleting parent categories not allowed",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only user with admin role has access to this method",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> delete(
      @Parameter(description = "Id of category which will be deleted") @PathVariable(value = "id")
          Long categoryId,
      Locale locale) {
    categoryService.delete(categoryId, locale);
    return ResponseEntity.noContent().build();
  }
}
