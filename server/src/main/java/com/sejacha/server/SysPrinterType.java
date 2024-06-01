package com.sejacha.server;

public enum SysPrinterType {

    INFO("INFO"),
    ERROR("ERROR"),
    WARNING("WARNING");

    private final String nameOfType;

    SysPrinterType(String nameOfType) {
        this.nameOfType = nameOfType;
    }

    public String getNameOfType() {
        return nameOfType;
    }

    public static SysPrinterType fromString(String text) {
        for (SysPrinterType type : SysPrinterType.values()) {
            if (type.nameOfType.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with text " + text);
    }
}
