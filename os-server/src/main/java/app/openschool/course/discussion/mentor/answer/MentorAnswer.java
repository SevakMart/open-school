package app.openschool.course.discussion.mentor.answer;

import app.openschool.course.discussion.mentor.question.MentorQuestion;
import app.openschool.user.User;
import java.time.Instant;
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
@Table(name = "mentor_answer")
public class MentorAnswer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "text")
  private String text;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "mentor_question_id")
  private MentorQuestion mentorQuestion;

  @Column(name = "create_date", nullable = false)
  private Instant createdDate;

  public MentorAnswer() {}

  public MentorAnswer(
      Long id, String text, User user, MentorQuestion mentorQuestion, Instant createdDate) {
    this.id = id;
    this.text = text;
    this.user = user;
    this.mentorQuestion = mentorQuestion;
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

  public MentorQuestion getDiscussionQuestionMentor() {
    return mentorQuestion;
  }

  public void setDiscussionQuestionMentor(MentorQuestion mentorQuestion) {
    this.mentorQuestion = mentorQuestion;
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
    MentorAnswer that = (MentorAnswer) o;
    return Objects.equals(id, that.id)
        && Objects.equals(text, that.text)
        && Objects.equals(user, that.user)
        && Objects.equals(mentorQuestion, that.mentorQuestion)
        && Objects.equals(createdDate, that.createdDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, text, user, mentorQuestion, createdDate);
  }
}
