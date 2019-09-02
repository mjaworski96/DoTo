package org.mjaworski.backend.service;

import org.mjaworski.backend.dto.user.PasswordChangeDto;
import org.mjaworski.backend.dto.user.ResetPasswordDto;
import org.mjaworski.backend.exception.bad_request.InvalidPasswordException;
import org.mjaworski.backend.exception.forbidden.WrongOldPasswordException;
import org.mjaworski.backend.exception.not_found.UserNotFoundException;

public interface PasswordService {
    void changePassword(String username, PasswordChangeDto passwords, String token) throws UserNotFoundException, WrongOldPasswordException, InvalidPasswordException;
    void validatePassword(String password) throws InvalidPasswordException;
    String encode(String password);
    void resetPassword(ResetPasswordDto resetPassword);
}
