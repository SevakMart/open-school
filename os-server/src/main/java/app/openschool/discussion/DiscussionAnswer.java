package app.openschool.discussion;

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
@Table(name = "discussion_answer_peers")
public class DiscussionAnswer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "text")
  private String text;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "discussion_question_id")
  private DiscussionQuestion discussionQuestion;

  @Column(name = "create_date", nullable = false)
  private Instant createdDate;

  public DiscussionAnswer() {}

  public DiscussionAnswer(
      Long id, String text, User user, DiscussionQuestion discussionQuestion, Instant createdDate) {
    this.id = id;
    this.text = text;
    this.user = user;
    this.discussionQuestion = discussionQuestion;
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

  public DiscussionQuestion getDiscussionQuestion() {
    return discussionQuestion;
  }

  public void setDiscussionQuestion(DiscussionQuestion discussionQuestion) {
    this.discussionQuestion = discussionQuestion;
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
    DiscussionAnswer that = (DiscussionAnswer) o;
    return Objects.equals(id, that.id)
        && Objects.equals(text, that.text)
        && Objects.equals(user, that.user)
        && Objects.equals(discussionQuestion, that.discussionQuestion)
        && Objects.equals(createdDate, that.createdDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, text, user, discussionQuestion, createdDate);
  }
}
