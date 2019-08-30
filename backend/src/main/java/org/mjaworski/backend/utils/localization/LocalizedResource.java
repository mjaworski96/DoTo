package org.mjaworski.backend.utils.localization;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class LocalizedResource {
    private ResourceBundle resourceBundle;

    public LocalizedResource(String path) {
        resourceBundle = ResourceBundle
                .getBundle(path,
                        Locale.getDefault());
    }

    public String getLocalizedContent(String key) {
        if (key == null || key.equals("")) {
            return "";
        }

        try {
            return new String(resourceBundle.getString(key)
                    .getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return resourceBundle.getString(key);
        }
    }
}
