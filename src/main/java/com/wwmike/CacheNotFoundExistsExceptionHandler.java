package com.wwmike;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {CacheNotFoundException.class, ExceptionHandler.class})
public class CacheNotFoundExistsExceptionHandler implements ExceptionHandler<CacheNotFoundException, HttpResponse<String>> {
    @Override
    public HttpResponse<String> handle(HttpRequest request, CacheNotFoundException exception) {
        return HttpResponse.notFound("{ problem: 'Cache with name \\'"+exception.getName()+"\\' was not found' }");
    }
}
