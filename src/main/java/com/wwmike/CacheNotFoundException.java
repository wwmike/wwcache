package com.wwmike;

public class CacheNotFoundException extends RuntimeException {


    String name;

    public CacheNotFoundException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
