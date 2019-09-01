package org.mjaworski.backend.service;

import org.mjaworski.backend.exception.bad_request.InvalidEmailException;
import org.mjaworski.backend.exception.conflict.EmailNotUniqueException;

public interface EmailService {
    void checkIfEmailIsUnique(int userId, String email) throws EmailNotUniqueException;
    void validateEmail(String email) throws InvalidEmailException;
    void sendEmail(String address, String title, String content);
    void sendRegisterEmail(String address, String username);
    void sendResetPasswordEmail(String address, String username, String newPassword);
}
