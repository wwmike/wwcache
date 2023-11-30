package com.wwmike;

import io.micronaut.context.annotation.Requirements;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {CachedItemNotFoundException.class, ExceptionHandler.class})
public class CachedItemNotFoundExceptionHandler implements ExceptionHandler<CachedItemNotFoundException, HttpResponse<Void>> {
    @Override
    public HttpResponse<Void> handle(HttpRequest request, CachedItemNotFoundException exception) {
        return HttpResponse.notFound();
    }
}
