package org.mjaworski.backend.service.impl;

import org.mjaworski.backend.exception.bad_request.invalid.user.InvalidEmailException;
import org.mjaworski.backend.exception.conflict.not_unique.EmailNotUniqueException;
import org.mjaworski.backend.persistance.entity.User;
import org.mjaworski.backend.persistance.repository.UserRepository;
import org.mjaworski.backend.service.EmailService;
import org.mjaworski.backend.utils.localization.LocalizedEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {
    private static Logger logger = LoggerFactory.getLogger(EmailService.class);

    private JavaMailSenderImpl mailSender;
    private UserRepository userRepository;

    @Autowired
    public EmailServiceImpl(JavaMailSenderImpl mailSender,
                            UserRepository userRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    @Override
    public void checkIfEmailIsUnique(int userId, String email) throws EmailNotUniqueException {
        Optional<User> userOptional = userRepository.getByUsername(email);
        if (userOptional.isPresent() && userOptional.get().getId() != userId) {
            throw new EmailNotUniqueException();
        }
    }

    @Override
    public void validateEmail(String email) throws InvalidEmailException {
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException e) {
            throw new InvalidEmailException(e);
        }
    }

    @Override
    public void sendEmail(String address, String title, String content) {
        new Thread(() -> {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(address);
                message.setSubject(title);
                message.setText(content);
                mailSender.send(message);
            } catch (Exception exception) {
                logger.error("Can't send email!", exception);
            }
        }).start();
    }

    @Override
    public void sendRegisterEmail(String address, String username) {
        sendEmail(address,
                LocalizedEmail.getInstance().getLocalizedEmail("registration-title"),
                LocalizedEmail.getInstance().getLocalizedEmail("registration", username));
    }

    @Override
    public void sendResetPasswordEmail(String address, String username, String newPassword) {
        sendEmail(address,
                LocalizedEmail.getInstance().getLocalizedEmail("password-reset-title"),
                LocalizedEmail.getInstance().getLocalizedEmail("password-reset", username, newPassword));
    }
}
