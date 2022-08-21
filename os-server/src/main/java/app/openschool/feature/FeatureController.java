package app.openschool.feature;

import app.openschool.common.response.ResponseMessage;
import app.openschool.feature.api.CourseSearchingFeaturesDto;
import app.openschool.feature.api.CreateAwsTemplateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/features")
public class FeatureController {

  private final FeatureService featureService;

  public FeatureController(FeatureService featureService) {
    this.featureService = featureService;
  }

  @ApiResponse(
      responseCode = "200",
      description = "Returns languages, difficulty levels, categories and relevant subcategories")
  @GetMapping("/courses/searched")
  @Operation(
      summary = "get course features for searching",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<CourseSearchingFeaturesDto> getCourseSearchingFeatures() {
    return ResponseEntity.ok(this.featureService.getCourseSearchingFeatures());
  }

  @Operation(
      summary = "create aws email template",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Template was created",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "400",
            description = "Template name, subject or html body not provided",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only user with admin role has access to this method",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/ses/templates")
  public ResponseEntity<HttpStatus> createAwsEmailTemplate(
      @Valid @RequestBody CreateAwsTemplateRequest request) {
    featureService.createTemplate(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "create template", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "Template was deleted",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "400",
            description =
                "Template name not supplied or template was not found with provided template name",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Only user with admin role has access to this method",
            content = @Content(schema = @Schema()))
      })
  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/ses/templates/{templateName}")
  public ResponseEntity<HttpStatus> deleteAwsEmailTemplate(
      @Valid @NotBlank(message = "{template.name.blank}") @PathVariable String templateName) {
    featureService.deleteTemplate(templateName);
    return ResponseEntity.noContent().build();
  }
}
