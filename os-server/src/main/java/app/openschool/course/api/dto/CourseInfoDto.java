package app.openschool.course.api.dto;

import java.util.Set;

public class CourseInfoDto {

  private String title;

  private String description;

  private String goal;

  private Set<CourseInfoModuleDto> modules;

  private CourseInfoMentorDto mentorDto;

  private double rating;

  private int enrolled;

  private String level;

  private String language;

  private double duration;

  public CourseInfoDto() {}

  public CourseInfoDto(
      String title,
      String description,
      String goal,
      Set<CourseInfoModuleDto> modules,
      CourseInfoMentorDto mentorDto,
      double rating,
      int enrolled,
      String level,
      String language,
      double duration) {
    this.title = title;
    this.description = description;
    this.goal = goal;
    this.modules = modules;
    this.mentorDto = mentorDto;
    this.rating = rating;
    this.enrolled = enrolled;
    this.level = level;
    this.language = language;
    this.duration = duration;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getGoal() {
    return goal;
  }

  public Set<CourseInfoModuleDto> getModules() {
    return modules;
  }

  public CourseInfoMentorDto getMentorDto() {
    return mentorDto;
  }

  public double getRating() {
    return rating;
  }

  public int getEnrolled() {
    return enrolled;
  }

  public String getLevel() {
    return level;
  }

  public String getLanguage() {
    return language;
  }

  public double getDuration() {
    return duration;
  }
}
