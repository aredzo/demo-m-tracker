package com.aredzo.mtracker.exception;

public class MTrackerServiceException extends RuntimeException {

    private final MTrackerServiceError error;

    public MTrackerServiceException(MTrackerServiceError error) {
        this.error = error;
    }

    public MTrackerServiceError getError() {
        return error;
    }

    @Override
    public String toString() {
        return "MTrackerServiceException{" +
                "error=" + error +
                '}';
    }
}
