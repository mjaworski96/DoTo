package org.mjaworski.backend.utils.localization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mjaworski.backend.dto.ApiErrorDto;

public class LocalizedMessage extends LocalizedResource {

    private static LocalizedMessage instance;

    public static synchronized LocalizedMessage getInstance() {
        if (instance == null) {
            instance = new LocalizedMessage();
        }
        return instance;
    }

    private LocalizedMessage() {
        super("org.mjaworski.backend.i18n.messages.LocalizedMessages");
    }

    public String getLocalizedMessage(String key) {
        return getLocalizedContent(key);
    }
    public String getLocalizedMessageAsJsonString(int code, String key) {
        return buildJsonString(code, getLocalizedMessage(key));
    }
    private String buildJsonString(int code, String errorMessage) {
        try {
            return new ObjectMapper()
                    .writeValueAsString(ApiErrorDto
                            .builder()
                            .code(code)
                            .message(errorMessage)
                            .build());
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
