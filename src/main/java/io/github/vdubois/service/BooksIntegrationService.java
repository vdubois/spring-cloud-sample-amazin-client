package io.github.vdubois.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.github.vdubois.model.Author;
import io.github.vdubois.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import rx.Observable;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


/**
 * Created by vdubois on 21/11/16.
 */
@Service
public class BooksIntegrationService {

    @LoadBalanced
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "findBookByIsbnFallback")
    public Observable<Book> findBookByIsbn(String isbn) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://books-service/books/" + isbn);
        return Observable.just(restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, null, Book.class).getBody());
    }

    public Observable<Book> findBookByIsbnFallback(String isbn) {
        Book book = new Book();
        book.setName("White Fang");
        book.setIsbn(isbn);
        book.setPublicationDate(new Date());
        book.setEditor("William Collins");
        book.setNumberOfPages(148);
        book.setPrice(new BigDecimal("3.12"));
        Author author = new Author();
        author.setName("Jack London");
        book.setAuthors(new HashSet<Author>(){{add(author);}});
        return Observable.just(book);
    }

    @HystrixCommand(fallbackMethod = "findAllBooksFallback")
    public Observable<List<Book>> findAllBooks() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://books-service/books");
        ParameterizedTypeReference<List<Book>> mappingType = new ParameterizedTypeReference<List<Book>>() {
        };
        return Observable.just(restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, null, mappingType).getBody());
    }

    public Observable<List<Book>> findAllBooksFallback() {
        return Observable.just(Collections.emptyList());
    }
}
