package app.openschool.course.module.quiz.question;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "answer_options")
public class AnswerOption {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "answer_option", nullable = false)
  private String answerOption;

  @Column(name = "is_right_answer", nullable = false)
  private Boolean isRightAnswer;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;

  public static AnswerOption getInstance() {
    return new AnswerOption();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AnswerOption that = (AnswerOption) o;
    return answerOption.equals(that.answerOption);
  }

  @Override
  public int hashCode() {
    return Objects.hash(answerOption);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAnswerOption() {
    return answerOption;
  }

  public void setAnswerOption(String answerOption) {
    this.answerOption = answerOption;
  }

  public Boolean isRightAnswer() {
    return isRightAnswer;
  }

  public void setRightAnswer(Boolean rightAnswer) {
    isRightAnswer = rightAnswer;
  }

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }
}
