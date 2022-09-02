package app.openschool.course.module.quiz.status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "quiz_status")
public class QuizStatus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "status_type", nullable = false)
  private String type;

  public QuizStatus() {}

  public QuizStatus(Long id, String type) {
    this.id = id;
    this.type = type;
  }

  public static QuizStatus isInitial() {
    return new QuizStatus(1L, "INITIAL");
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
