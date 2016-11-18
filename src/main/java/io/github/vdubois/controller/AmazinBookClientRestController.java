package io.github.vdubois.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.github.vdubois.configuration.RibbonConfiguration;
import io.github.vdubois.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vdubois on 08/11/16.
 */
@RestController
@RibbonClient(name = "books-service", configuration = RibbonConfiguration.class)
public class AmazinBookClientRestController {

    @Autowired
    private RestTemplate restTemplate;

//    @HystrixCommand(fallbackMethod = "fallbackHello")
//    @RequestMapping(value = "/hi", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public String hello() {
//        return restTemplate.getForObject("http://recommendations-service/hello", String.class);
//    }
//
//    public String fallbackHello() {
//        return "Oh hey, nice to see you !";
//    }

    @GetMapping("/books/{isbn}")
    @HystrixCommand(fallbackMethod = "findBookByIsbnFallback")
    public Book getBookInformationsByIsbn(String isbn) {
        Book bookBasicInfo = findBookByIsbn(isbn);
        return bookBasicInfo;
    }

    @HystrixCommand(fallbackMethod = "findBookByIsbnFallback")
    public Book findBookByIsbn(String isbn) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("isbn", isbn);
        return restTemplate.getForObject("http://books-service/books/search/findOneByIsbn", Book.class, urlVariables);
    }

    public Book findBookByIsbnFallback(String isbn) {
        return new Book();
    }

//    public Set<Author> getAuthors
}
