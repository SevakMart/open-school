package app.openschool.course.api.dto;

import java.util.Set;

public class MentorCourseDto {

  private String title;

  private String description;

  private Double rating;

  private String category;

  private String difficulty;

  private String language;

  private Set<String> keywords;

  public MentorCourseDto(
      String title,
      String description,
      Double rating,
      String category,
      String difficulty,
      String language,
      Set<String> keywords) {
    this.title = title;
    this.description = description;
    this.rating = rating;
    this.category = category;
    this.difficulty = difficulty;
    this.language = language;
    this.keywords = keywords;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getRating() {
    return rating;
  }

  public void setRating(Double rating) {
    this.rating = rating;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(String difficulty) {
    this.difficulty = difficulty;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public Set<String> getKeywords() {
    return keywords;
  }

  public void setKeywords(Set<String> keywords) {
    this.keywords = keywords;
  }
}
