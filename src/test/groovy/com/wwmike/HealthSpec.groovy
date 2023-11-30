package com.wwmike;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest;
import jakarta.inject.Inject
import spock.lang.Specification;

@MicronautTest
class HealthSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient client;


    def "health endpoint responds HTTP 200"() {
        when:
        HttpStatus status = callHealthEndpoint();

        then:
        status == HttpStatus.OK
    }

    private HttpStatus callHealthEndpoint() {
        try {
            return client.toBlocking().retrieve(HttpRequest.GET("/health"), HttpStatus.class)
        }
        catch (HttpClientResponseException e) {
            return e.getStatus();
        }
    }

}
