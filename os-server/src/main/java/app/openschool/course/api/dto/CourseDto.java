package app.openschool.course.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

public class CourseDto {

  @Schema(description = "Course Id", example = "1")
  private Long id;

  @Schema(description = "Course title", example = "Java")
  private String title;

  @Schema(description = "Course rating", example = "7.7")
  private Double rating;

  @Schema(description = "Course difficulty level", example = "Intermediate")
  private String difficulty;

  @Schema(description = "Tags-keywords regarding course", example = "[Programming, Engineering]")
  private Set<String> keywords;

  public CourseDto() {}

  public CourseDto(Long id, String title, Double rating, String difficulty, Set<String> keywords) {
    this.id = id;
    this.title = title;
    this.rating = rating;
    this.difficulty = difficulty;
    this.keywords = keywords;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Double getRating() {
    return rating;
  }

  public void setRating(Double rating) {
    this.rating = rating;
  }

  public String getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(String difficulty) {
    this.difficulty = difficulty;
  }

  public Set<String> getKeywords() {
    return keywords;
  }

  public void setKeywords(Set<String> keywords) {
    this.keywords = keywords;
  }
}
