package org.example.literalura.model;


public enum Languages {
    UNKNOWN("nd"),
    SPANISH("es"),
    ENGLISH("en"),
    FRENCH("fr"),
    ITALIAN("it");

    private final String languageCode;

    Languages(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public static Languages fromString(String text) {
        for (Languages lang : Languages.values()) {
            if (lang.languageCode.equalsIgnoreCase(text)) {
                return lang;
            }
        }
        throw new IllegalArgumentException("No language found: " + text);
    }
}
