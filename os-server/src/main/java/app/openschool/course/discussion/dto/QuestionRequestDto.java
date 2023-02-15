package app.openschool.course.discussion.dto;

import java.util.Objects;

public class QuestionRequestDto {
  private String text;
  private Long courseId;

  public QuestionRequestDto() {}

  public QuestionRequestDto(String text, Long courseId) {
    this.text = text;
    this.courseId = courseId;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QuestionRequestDto that = (QuestionRequestDto) o;
    return Objects.equals(text, that.text) && Objects.equals(courseId, that.courseId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, courseId);
  }

  @Override
  public String toString() {
    return "DiscussionQuestionRequestDto{"
        + "text='"
        + text
        + '\''
        + ", courseId="
        + courseId
        + '}';
  }
}
