package app.openschool.discussion.api.dto;

import java.util.Objects;

public class DiscussionAnswerRequestDto {
  private String text;

  private Long questionId;

  public DiscussionAnswerRequestDto() {}

  public DiscussionAnswerRequestDto(String text, Long questionId) {
    this.text = text;
    this.questionId = questionId;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Long questionId) {
    this.questionId = questionId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiscussionAnswerRequestDto that = (DiscussionAnswerRequestDto) o;
    return Objects.equals(text, that.text) && Objects.equals(questionId, that.questionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, questionId);
  }
}
