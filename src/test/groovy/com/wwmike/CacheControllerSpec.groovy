package com.wwmike

import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Header
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class CacheControllerSpec extends Specification {

    @Inject
    @Client("/")
    @Header(name = HttpHeaders.ACCEPT, value = "text/plain")
    HttpClient client;


    def "random cache name returns not found"() {
        when:
        HttpStatus status = sendGetRequestAndRetrieveStatus("not_existing_cache", "random_id");

        then:
        status == HttpStatus.NOT_FOUND
    }

    def "simple string item is accepted to cache"() {
        when:
        HttpStatus status = sendPostRequestAndRetrieveStatus("cacheName", "body_id", "body");

        then:
        status == HttpStatus.CREATED
    }

    def "data can be read back from cache"() {
        given:
        def userDataString = "{ name: \"Peter Folk\", job: \"actor\"}"
        def cacheName = "user"
        def cacheId = "user-id-1"


        when:
        def response = sendPostRequestAndRetrieveStatus(cacheName, cacheId, userDataString);

        then:
        response == HttpStatus.CREATED

        when:
        def lookupResponse = sendGetRequestAndRetrieveResponse(cacheName, cacheId);

        then:
        lookupResponse == userDataString
    }


    private HttpStatus sendGetRequestAndRetrieveStatus(String name, String id) {
        try {
            return client.toBlocking().retrieve(HttpRequest.GET("/cache/" + name + "/" + id), HttpStatus.class)
        }
        catch (HttpClientResponseException e) {
            return e.getStatus();
        }
    }

    private HttpStatus sendPostRequestAndRetrieveStatus(String name, String id, String body) {
        try {
            return client.toBlocking().retrieve(HttpRequest.POST("/cache/" + name + "/" + id, body).contentType("text/plain"), HttpStatus.class)
        }
        catch (HttpClientResponseException e) {
            return e.getStatus();
        }
    }

    private String sendGetRequestAndRetrieveResponse(String name, String id) {
        try {
            return client.toBlocking().retrieve(HttpRequest.GET("/cache/" + name + "/" + id), String.class)
        }
        catch (HttpClientResponseException e) {
            return e.getMessage()
        }
    }

}
