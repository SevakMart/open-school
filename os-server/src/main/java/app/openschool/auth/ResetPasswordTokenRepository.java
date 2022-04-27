package app.openschool.auth;

import antlr.Token;
import app.openschool.auth.entity.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Token> {}
