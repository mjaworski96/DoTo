package org.mjaworski.backend.service;

import org.mjaworski.backend.exception.bad_request.invalid.user.InvalidEmailException;
import org.mjaworski.backend.exception.conflict.not_unique.EmailNotUniqueException;

public interface EmailService {
    void checkIfEmailIsUnique(int userId, String email) throws EmailNotUniqueException;
    void validateEmail(String email) throws InvalidEmailException;
    void sendEmail(String address, String title, String content);
    void sendRegisterEmail(String address, String username);
    void sendResetPasswordEmail(String address, String username, String newPassword);
}
