package com.coding.flux.sk.common.exception;

import java.time.LocalDateTime;

public class NotFoundException extends RuntimeException {
    public NotFoundException (String message) {
        super(message);
    }
}
