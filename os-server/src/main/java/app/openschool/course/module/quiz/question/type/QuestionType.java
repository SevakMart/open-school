package app.openschool.course.module.quiz.question.type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "question_type")
public class QuestionType {

  private static final String MATCHING = "MATCHING";
  private static final String MULTIPLE_CHOICE = "MULTIPLE_CHOICE";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "type", nullable = false)
  private String type;

  public QuestionType() {}

  public QuestionType(Long id, String type) {
    this.id = id;
    this.type = type;
  }

  public static QuestionType isMatching() {
    return new QuestionType(1L, MATCHING);
  }

  public static QuestionType isMultipleChoice() {
    return new QuestionType(2L, MULTIPLE_CHOICE);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
