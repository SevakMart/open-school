package app.openschool.user.api.dto;

import java.util.Set;

public class UserWithSavedMentorsDto {

  private final Long userId;
  private final String email;
  private final Set<String> mentors;

  public UserWithSavedMentorsDto(Long userId, String email, Set<String> mentors) {
    this.userId = userId;
    this.email = email;
    this.mentors = mentors;
  }

  public Long getUserId() {
    return userId;
  }

  public String getEmail() {
    return email;
  }

  public Set<String> getMentors() {
    return mentors;
  }
}
