package com.aredzo.mtracker.exception;

public enum MTrackerServiceError {
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Object Not Found"),
    NOT_AUTHORIZED(403, "User Not Authorized"),
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

    @Override
    public String toString() {
        return "MTrackerServiceError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
