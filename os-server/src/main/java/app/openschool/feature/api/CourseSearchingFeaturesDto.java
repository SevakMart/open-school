package app.openschool.feature.api;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

public class CourseSearchingFeaturesDto {

  @Schema(description = "Parent categories and relevant subcategories")
  private final Map<String, Map<Long, String>> parentAndSubcategories;

  @Schema(description = "Languages")
  private final Map<Integer, String> allLanguages;

  @Schema(description = "Difficulty levels")
  private final Map<Integer, String> allDifficultyLevels;

  public CourseSearchingFeaturesDto(
      Map<String, Map<Long, String>> parentAndSubcategories,
      Map<Integer, String> allLanguages,
      Map<Integer, String> allDifficultyLevels) {
    this.parentAndSubcategories = parentAndSubcategories;
    this.allLanguages = allLanguages;
    this.allDifficultyLevels = allDifficultyLevels;
  }

  public Map<String, Map<Long, String>> getParentAndSubcategories() {
    return parentAndSubcategories;
  }

  public Map<Integer, String> getAllLanguages() {
    return allLanguages;
  }

  public Map<Integer, String> getAllDifficultyLevels() {
    return allDifficultyLevels;
  }
}
