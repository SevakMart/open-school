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
@Table(name = "discussion_answer_mentor")
public class DiscussionAnswerMentor {
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
  private DiscussionQuestionMentor discussionQuestionMentor;

  @Column(name = "create_date", nullable = false)
  private Instant createdDate;

  public DiscussionAnswerMentor() {}

  public DiscussionAnswerMentor(
      Long id,
      String text,
      User user,
      DiscussionQuestionMentor discussionQuestionMentor,
      Instant createdDate) {
    this.id = id;
    this.text = text;
    this.user = user;
    this.discussionQuestionMentor = discussionQuestionMentor;
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

  public DiscussionQuestionMentor getDiscussionQuestionMentor() {
    return discussionQuestionMentor;
  }

  public void setDiscussionQuestionMentor(DiscussionQuestionMentor discussionQuestionMentor) {
    this.discussionQuestionMentor = discussionQuestionMentor;
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
    DiscussionAnswerMentor that = (DiscussionAnswerMentor) o;
    return Objects.equals(id, that.id)
        && Objects.equals(text, that.text)
        && Objects.equals(user, that.user)
        && Objects.equals(discussionQuestionMentor, that.discussionQuestionMentor)
        && Objects.equals(createdDate, that.createdDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, text, user, discussionQuestionMentor, createdDate);
  }
}
