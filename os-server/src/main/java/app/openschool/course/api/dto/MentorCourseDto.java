package app.openschool.course.api.dto;

import java.util.Set;

public class MentorCourseDto {

    private String title;

    private String description;

    private Double rating;

    private String categoryTitle;

    private String difficultyTitle;

    private String languageTitle;

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
        this.categoryTitle = category;
        this.difficultyTitle = difficulty;
        this.languageTitle = language;
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

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getDifficultyTitle() {
        return difficultyTitle;
    }

    public void setDifficultyTitle(String difficultyTitle) {
        this.difficultyTitle = difficultyTitle;
    }

    public String getLanguageTitle() {
        return languageTitle;
    }

    public void setLanguageTitle(String languageTitle) {
        this.languageTitle = languageTitle;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

}
