package com.wwmike;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {CachedItemAlreadyExistsException.class, ExceptionHandler.class})
public class CachedItemAlreadyExistsExceptionHandler implements ExceptionHandler<CachedItemAlreadyExistsException, HttpResponse<String>> {
    @Override
    public HttpResponse<String> handle(HttpRequest request, CachedItemAlreadyExistsException exception) {
        return HttpResponse.badRequest().body("{ problem: 'Item with id \\'"+exception.getId()+"\\' already exists' }");
    }
}
