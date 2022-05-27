package app.openschool.course.api.dto;

import java.util.Map;

public class CourseSearchingFeaturesDto {

  private Map<String, Map<Long, String>> parentAndRelevantChildCategories;

  private Map<Integer, String> allLanguages;

  private Map<Integer, String> allDifficultyLevels;

  public CourseSearchingFeaturesDto() {}

  public CourseSearchingFeaturesDto(
      Map<String, Map<Long, String>> parentAndRelevantChildCategories,
      Map<Integer, String> allLanguages,
      Map<Integer, String> allDifficultyLevels) {
    this.parentAndRelevantChildCategories = parentAndRelevantChildCategories;
    this.allLanguages = allLanguages;
    this.allDifficultyLevels = allDifficultyLevels;
  }

  public Map<String, Map<Long, String>> getParentAndRelevantChildCategories() {
    return parentAndRelevantChildCategories;
  }

  public Map<Integer, String> getAllLanguages() {
    return allLanguages;
  }

  public Map<Integer, String> getAllDifficultyLevels() {
    return allDifficultyLevels;
  }
}
