package org.mjaworski.backend.exception;

import org.mjaworski.backend.dto.ApiErrorDto;
import org.mjaworski.backend.exception.internal_server_error.InternalServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

public abstract class ExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    public static ResponseEntity handle(Exception exception) {
        if (exception instanceof LocalizedException) {
            logger.warn("Handled exception", exception);
            return ResponseEntity
                    .status(((LocalizedException) exception).getMessageCode())
                    .body(ApiErrorDto.builder()
                        .code(((LocalizedException) exception).getMessageCode())
                        .message(exception.getLocalizedMessage())
                        .build());
        } else {
            logger.error("Fatal error", exception);
            return ResponseEntity
                    .status(500)
                    .body(ApiErrorDto.builder()
                        .code(500)
                        .message(
                            new InternalServerErrorException().getLocalizedMessage()
                        )
                        .build()
                    );
        }
    }
}
