package app.openschool.course.discussion.mentor.question;

import app.openschool.course.Course;
import app.openschool.course.discussion.mentor.answer.MentorAnswer;
import app.openschool.user.User;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "mentor_question")
public class MentorQuestion {

  // ToDo this class and the components used in it will be changed in the future
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "validation.notBlank")
  @Column(name = "text", nullable = false)
  private String text;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "learning_path_id")
  private Course course;

  @OneToMany(mappedBy = "mentorQuestion")
  private List<MentorAnswer> mentorAnswerList;

  @Column(name = "create_date", nullable = false)
  private Instant createdDate;

  public MentorQuestion() {}

  public MentorQuestion(
      Long id,
      @NotBlank(message = "validation.notBlank") String text,
      User user,
      Course course,
      List<MentorAnswer> mentorAnswerList,
      Instant createdDate) {
    this.id = id;
    this.text = text;
    this.user = user;
    this.course = course;
    this.mentorAnswerList = mentorAnswerList;
    this.createdDate = createdDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public List<MentorAnswer> getDiscussionAnswerMentorList() {
    return mentorAnswerList;
  }

  public void setDiscussionAnswerMentorList(List<MentorAnswer> mentorAnswerList) {
    this.mentorAnswerList = mentorAnswerList;
  }

  public Instant getCreatedDate() {
    return createdDate;
  }

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
    MentorQuestion that = (MentorQuestion) o;
    return Objects.equals(id, that.id)
        && Objects.equals(text, that.text)
        && Objects.equals(user, that.user)
        && Objects.equals(course, that.course)
        && Objects.equals(mentorAnswerList, that.mentorAnswerList)
        && Objects.equals(createdDate, that.createdDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, text, user, course, mentorAnswerList, createdDate);
  }
}
