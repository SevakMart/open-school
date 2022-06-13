package app.openschool.course.api.dto;

import java.util.Set;

public class CourseDto {

  private Long id;

  private String title;

  private Double rating;

  private String difficulty;

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
