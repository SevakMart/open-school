package app.openschool.course.api.dto;

import app.openschool.user.api.dto.MentorDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

public class CourseInfoDto {

  @Schema(description = "Course title", example = "Java")
  private String title;

  @Schema(description = "Java Programming training course", example = "In progress")
  private String description;

  @Schema(
      description = "Purpose of the course",
      example =
          "Gain extensive hands-on experience writing, compiling, and executing Java programs")
  private String goal;

  @ArraySchema(schema = @Schema(implementation = CourseInfoModuleDto.class))
  private Set<CourseInfoModuleDto> modules;

  @Schema(description = "Object containing the information of course mentor")
  private MentorDto mentorDto;

  @Schema(description = "Rating of the course", example = "5.5")
  private double rating;

  private int enrolled;

  @Schema(description = "Difficulty level of the course", example = "Advanced")
  private String level;

  @Schema(description = "Course language", example = "English")
  private String language;

  @Schema(description = "Duration of the course", example = "360.5")
  private double duration;

  public CourseInfoDto() {}

  public CourseInfoDto(
      String title,
      String description,
      String goal,
      Set<CourseInfoModuleDto> modules,
      MentorDto mentorDto,
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

  public MentorDto getMentorDto() {
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
