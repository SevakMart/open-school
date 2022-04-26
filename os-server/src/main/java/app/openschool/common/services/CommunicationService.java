package app.openschool.common.services;

import app.openschool.user.User;
import java.util.TimeZone;

public interface CommunicationService {

  void sendEmailToVerifyUserAccount(User user, TimeZone timeZone);
}
