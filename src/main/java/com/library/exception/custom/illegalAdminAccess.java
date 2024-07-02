package com.library.exception.custom;

public class illegalAdminAccess extends Exception {
    public illegalAdminAccess(String message) {
        super(message);
    }
}