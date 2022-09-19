package app.openschool.course.module.quiz.question;

import app.openschool.course.module.quiz.Quiz;
import app.openschool.course.module.quiz.question.answer.AnswerOption;
import app.openschool.course.module.quiz.question.type.QuestionType;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "questions")
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "question", nullable = false)
  private String question;

  @Column(name = "right_answer_count", nullable = false)
  private int rightAnswersCount = 1;

  @ManyToOne
  @JoinColumn(name = "question_type_id", nullable = false)
  private QuestionType questionType;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
  private Set<AnswerOption> answerOptions;

  @ManyToOne
  @JoinColumn(name = "quiz_id")
  private Quiz quiz;

  public static Question getInstance() {
    return new Question();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Question question1 = (Question) o;
    return question.equals(question1.question)
        && questionType.getType().equals(question1.questionType.getType());
  }

  @Override
  public int hashCode() {
    return Objects.hash(question);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public Quiz getQuiz() {
    return quiz;
  }

  public void setQuiz(Quiz quiz) {
    this.quiz = quiz;
  }

  public QuestionType getQuestionType() {
    return questionType;
  }

  public void setQuestionType(QuestionType questionType) {
    this.questionType = questionType;
  }

  public Set<AnswerOption> getAnswerOptions() {
    return answerOptions;
  }

  public void setAnswerOptions(Set<AnswerOption> answerOptions) {
    this.answerOptions = answerOptions;
  }

  public int getRightAnswersCount() {
    return rightAnswersCount;
  }

  public void setRightAnswersCount(int rightAnswersCount) {
    this.rightAnswersCount = rightAnswersCount;
  }
}
