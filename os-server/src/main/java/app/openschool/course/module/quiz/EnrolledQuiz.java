package app.openschool.course.module.quiz;

import app.openschool.course.module.EnrolledModule;
import app.openschool.course.module.quiz.status.QuizStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "enrolled_quiz")
public class EnrolledQuiz {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "student_grade")
  private int studentGrade = 0;

  @ManyToOne
  @JoinColumn(name = "quiz_status_id")
  private QuizStatus quizStatus;

  @ManyToOne
  @JoinColumn(name = "quiz_id")
  private Quiz quiz;

  @ManyToOne
  @JoinColumn(name = "enrolled_module_id")
  private EnrolledModule enrolledModule;

  public EnrolledQuiz() {}

  public EnrolledQuiz(QuizStatus quizStatus, Quiz quiz, EnrolledModule enrolledModule) {
    this.quizStatus = quizStatus;
    this.quiz = quiz;
    this.enrolledModule = enrolledModule;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public QuizStatus getQuizStatus() {
    return quizStatus;
  }

  public void setQuizStatus(QuizStatus quizStatus) {
    this.quizStatus = quizStatus;
  }

  public Quiz getQuiz() {
    return quiz;
  }

  public void setQuiz(Quiz quiz) {
    this.quiz = quiz;
  }

  public EnrolledModule getEnrolledModule() {
    return enrolledModule;
  }

  public void setEnrolledModule(EnrolledModule enrolledModule) {
    this.enrolledModule = enrolledModule;
  }

  public int getStudentGrade() {
    return studentGrade;
  }

  public void setStudentGrade(int studentGrade) {
    this.studentGrade = studentGrade;
  }
}
