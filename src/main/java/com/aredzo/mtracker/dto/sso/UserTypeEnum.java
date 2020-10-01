package com.aredzo.mtracker.dto.sso;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserTypeEnum {
    REGULAR_USER("RegularUser"),
    SERVICE("Service");

    private final String text;

    @JsonValue
    public String getText() {
        return text;
    }

    UserTypeEnum(String text) {
        this.text = text;
    }
}
