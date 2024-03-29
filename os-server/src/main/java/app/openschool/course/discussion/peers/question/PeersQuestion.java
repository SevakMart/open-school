package app.openschool.course.discussion.peers.question;

import app.openschool.course.Course;
import app.openschool.course.discussion.Question;
import app.openschool.course.discussion.peers.answer.PeersAnswer;
import app.openschool.user.User;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
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
@Table(name = "peers_question")
public class PeersQuestion implements Question {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "text", nullable = false)
  private String text;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "learning_path_id")
  private Course course;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "peersQuestion")
  private List<PeersAnswer> peersAnswers;

  @Column(name = "create_date", nullable = false)
  private Instant createdDate;

  public PeersQuestion() {}

  public PeersQuestion(
      Long id,
      String text,
      User user,
      Course course,
      List<PeersAnswer> peersAnswers,
      Instant createdDate) {
    this.id = id;
    this.text = text;
    this.user = user;
    this.course = course;
    this.peersAnswers = peersAnswers;
    this.createdDate = createdDate;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String getText() {
    return text;
  }

  @Override
  public void setText(String text) {
    this.text = text;
  }

  @Override
  public User getUser() {
    return user;
  }

  @Override
  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public Course getCourse() {
    return course;
  }

  @Override
  public void setCourse(Course course) {
    this.course = course;
  }

  public List<PeersAnswer> getDiscussionAnswers() {
    return peersAnswers;
  }

  public void setDiscussionAnswers(List<PeersAnswer> peersAnswers) {
    this.peersAnswers = peersAnswers;
  }

  @Override
  public Instant getCreatedDate() {
    return createdDate;
  }

  @Override
  public void setCreatedDate(Instant createdDate) {
    this.createdDate = createdDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PeersQuestion that = (PeersQuestion) o;
    return Objects.equals(id, that.id)
        && Objects.equals(text, that.text)
        && Objects.equals(user, that.user)
        && Objects.equals(course, that.course)
        && Objects.equals(peersAnswers, that.peersAnswers)
        && Objects.equals(createdDate, that.createdDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, text, user, course, peersAnswers, createdDate);
  }
}
