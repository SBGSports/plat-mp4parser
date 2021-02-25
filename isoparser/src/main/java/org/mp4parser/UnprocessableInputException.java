package org.mp4parser;

public class UnprocessableInputException extends RuntimeException {
    private static final long serialVersionUID = -6483085581430071397L;

    public UnprocessableInputException(String message) {
        super(message);
    }
}