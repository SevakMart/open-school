package app.openschool.feature;

import app.openschool.feature.api.CourseSearchingFeaturesDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
      description =
          "Returns languages, difficulty levels, categories and relevant subcategories")
  @GetMapping("/courses/searched")
  @Operation(
      summary = "get course features for searching",
      security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<CourseSearchingFeaturesDto> getCourseSearchingFeatures() {
    return ResponseEntity.ok(this.featureService.getCourseSearchingFeatures());
  }
}
