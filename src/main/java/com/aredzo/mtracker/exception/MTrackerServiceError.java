package com.aredzo.mtracker.exception;

public enum MTrackerServiceError {
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Object Not Found"),
    NOT_AUTHORIZED(403, "User Not Authorized"),
    USER_NOT_FOUND(404, "User Not Found"),
    SERVICE_TOKEN_NOT_FOUND(404, "Service Token Not Found"),
    TOKEN_NOT_FOUND(404, "Token Not Found"),
    USER_NOT_AUTHORIZED(403, "User Not Authorized"),
    SERVICE_NOT_AUTHORIZED(403, "Service Not Authorized"),
    INTERNAL_ERROR(500, "Internal Server Error");

    private final int code;
    private final String message;

    MTrackerServiceError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static MTrackerServiceError fromString(String text) {
        for (MTrackerServiceError cs : MTrackerServiceError.values()) {
            if (cs.message.equalsIgnoreCase(text))
                return cs;
        }
        return null;
    }

    @Override
    public String toString() {
        return "MTrackerServiceError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
