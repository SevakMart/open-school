package app.openschool.common.services;

import app.openschool.user.User;

public interface CommunicationService {

  void sendEmailToVerifyUserAccount(User user);
}
