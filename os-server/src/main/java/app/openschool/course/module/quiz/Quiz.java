package app.openschool.course.module.quiz;

import app.openschool.course.module.Module;
import app.openschool.course.module.quiz.question.Question;
import app.openschool.course.module.quiz.status.QuizStatus;
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
@Table(name = "quiz")
public class Quiz {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "max_grade", nullable = false)
  private int maxGrade;

  @Column(name = "student_grade")
  private int studentGrade;

  @Column(name = "passing_score", nullable = false)
  private int passingScore;

  @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
  private Set<Question> questions;

  @ManyToOne
  @JoinColumn(name = "quiz_status_id")
  private QuizStatus quizStatus;

  @ManyToOne
  @JoinColumn(name = "module_id")
  private Module module;

  public static Quiz getInstance() {
    return new Quiz();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getMaxGrade() {
    return maxGrade;
  }

  public void setMaxGrade(int maxGrade) {
    this.maxGrade = maxGrade;
  }

  public int getStudentGrade() {
    return studentGrade;
  }

  public void setStudentGrade(int studentGrade) {
    this.studentGrade = studentGrade;
  }

  public int getPassingScore() {
    return passingScore;
  }

  public void setPassingScore(int passingScore) {
    this.passingScore = passingScore;
  }

  public Set<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(Set<Question> questions) {
    this.questions = questions;
  }

  public QuizStatus getQuizStatus() {
    return quizStatus;
  }

  public void setQuizStatus(QuizStatus quizStatus) {
    this.quizStatus = quizStatus;
  }

  public Module getModule() {
    return module;
  }

  public void setModule(Module module) {
    this.module = module;
  }
}
