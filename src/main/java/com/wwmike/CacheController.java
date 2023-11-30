package com.wwmike;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.net.URI;
import java.util.Map;

@Controller("/cache")
public class CacheController {

    @Inject
    private CacheService cacheService;

    @Get("/{name}/{id}")
    @Produces("text/plain")
    public HttpResponse<String> getItemFromCache(String name, String id) {
        Map<String, String> cache = cacheService.getCache(name);
        if (!cache.containsKey(id)) {
            throw new CachedItemNotFoundException();
        }
        return HttpResponse.ok(cache.get(id));
    }

    @Post("/{name}/{id}")
    @Produces("text/plain")
    public HttpResponse<String> saveItemToCache(String name, String id, @Body String body) {
        Map<String, String> cache = cacheService.getCache(name);
        if (cache.containsKey(id)) {
            throw new CachedItemAlreadyExistsException(id);
        }

        cache.put(id, body);

        return HttpResponse
                .created(body)
                .headers(headers -> headers.location(toUri(name, id)));
    }

    @Put("/")
    public HttpResponse<Void> updateItemInCache(String name, String id, @Body String body) {

        Map<String, String> cache = cacheService.getCache(name);
        if (!cache.containsKey(id)) {
            throw new CachedItemNotFoundException();
        }
        cache.put(id, body);

        return HttpResponse
                .<Void>noContent()
                .header(HttpHeaders.LOCATION, toUri(name, id).getPath());
    }


    @Delete("/{name}")
    public HttpResponse<Void> delete(String name) {
        cacheService.deleteCache(name);
        return HttpResponse.noContent();
    }

    @Delete("/{name}/{id}")
    public HttpResponse<Void> deleteItem(String name, String id) {
        Map<String, String> cache = cacheService.getCache(name);
        if (!cache.containsKey(id)) {
            throw new CachedItemNotFoundException();
        }
        cache.remove(id);
        return HttpResponse.noContent();
    }

    private URI toUri(String name, String id) {
        return URI.create("/" + name + "/" + id);
    }


}
