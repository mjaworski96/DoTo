package org.mjaworski.backend.utils.localization;

public class LocalizedEmail extends LocalizedResource {
    private static LocalizedEmail instance;

    public static  synchronized LocalizedEmail getInstance() {
        if (instance == null) {
            instance = new LocalizedEmail();
        }
        return instance;
    }

    public LocalizedEmail() {
        super("org.mjaworski.backend.i18n.email.LocalizedEmail");
    }

    public String getLocalizedEmail(String key, Object... args) {
        return String.format(getLocalizedContent(key),
                args);
    }

}
