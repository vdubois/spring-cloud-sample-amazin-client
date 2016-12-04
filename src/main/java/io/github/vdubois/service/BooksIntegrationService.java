package io.github.vdubois.service;

import io.github.vdubois.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import rx.Observable;


/**
 * Created by vdubois on 21/11/16.
 */
@Service
public class BooksIntegrationService {

    @LoadBalanced
    @Autowired
    private RestTemplate restTemplate;

    public Observable<Book> findBookByIsbn(String isbn) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://books-service/books/" + isbn);
        return Observable.just(restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, null, Book.class).getBody());
    }
}
