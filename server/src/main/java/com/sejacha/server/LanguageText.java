package com.sejacha.server;

public enum LanguageText {
    EMAIL_VERIFY_SUBJECT("email.verify_mail_subject");

    private final String nameOfType;

    LanguageText(String nameOfType) {
        this.nameOfType = nameOfType;
    }

    public String getNameOfType() {
        return nameOfType;
    }

    public static LanguageText fromString(String text) {
        for (LanguageText type : LanguageText.values()) {
            if (type.nameOfType.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with text " + text);
    }

}
