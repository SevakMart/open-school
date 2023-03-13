package app.openschool.course.discussion.peers.answer;

import app.openschool.course.discussion.peers.question.PeersQuestion;
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
@Table(name = "peers_answer")
public class PeersAnswer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "text")
  private String text;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "peers_question_id")
  private PeersQuestion peersQuestion;

  @Column(name = "create_date", nullable = false)
  private Instant createdDate;

  public PeersAnswer() {}

  public PeersAnswer(
      Long id, String text, User user, PeersQuestion peersQuestion, Instant createdDate) {
    this.id = id;
    this.text = text;
    this.user = user;
    this.peersQuestion = peersQuestion;
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

  public PeersQuestion getDiscussionQuestion() {
    return peersQuestion;
  }

  public void setDiscussionQuestion(PeersQuestion peersQuestion) {
    this.peersQuestion = peersQuestion;
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
    PeersAnswer that = (PeersAnswer) o;
    return Objects.equals(id, that.id)
        && Objects.equals(text, that.text)
        && Objects.equals(user, that.user)
        && Objects.equals(peersQuestion, that.peersQuestion)
        && Objects.equals(createdDate, that.createdDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, text, user, peersQuestion, createdDate);
  }
}
