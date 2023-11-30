package com.wwmike;

public class CachedItemAlreadyExistsException extends RuntimeException {
    private final String id;

    public CachedItemAlreadyExistsException(String id) {
        this.id=id;
    }

    public String getId() {
        return id;
    }
}
