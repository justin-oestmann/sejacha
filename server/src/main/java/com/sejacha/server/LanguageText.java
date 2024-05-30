package com.sejacha.server;

/**
 * Enum representing various language texts used in the application.
 */
public enum LanguageText {
    EMAIL_VERIFY_SUBJECT("email.verify_mail_subject");

    private final String nameOfType;

    /**
     * Constructs a new LanguageText enum constant with the specified name.
     *
     * @param nameOfType the name of the language text
     */
    LanguageText(String nameOfType) {
        this.nameOfType = nameOfType;
    }

    /**
     * Retrieves the name of the language text.
     *
     * @return the name of the language text
     */
    public String getNameOfType() {
        return nameOfType;
    }

    /**
     * Retrieves the LanguageText enum constant associated with the specified text.
     *
     * @param text the text associated with the enum constant
     * @return the LanguageText enum constant
     * @throws IllegalArgumentException if no enum constant is found with the
     *                                  specified text
     */
    public static LanguageText fromString(String text) {
        for (LanguageText type : LanguageText.values()) {
            if (type.nameOfType.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with text " + text);
    }
}
