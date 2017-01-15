package io.github.vdubois.controller;

import io.github.vdubois.configuration.RibbonConfiguration;
import io.github.vdubois.model.Book;
import io.github.vdubois.model.BookDetails;
import io.github.vdubois.service.BooksIntegrationService;
import io.github.vdubois.service.CommentsIntegrationService;
import io.github.vdubois.service.RecommendationsIntegrationService;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Single;

import java.util.List;

/**
 * Created by vdubois on 08/11/16.
 */
@RestController
@RequestMapping("/api/v1")
@RibbonClient(name = "books-service", configuration = RibbonConfiguration.class)
public class AmazinBookClientRestController {

    private BooksIntegrationService booksIntegrationService;

    private CommentsIntegrationService commentsIntegrationService;

    private RecommendationsIntegrationService recommendationsIntegrationService;

    public AmazinBookClientRestController(BooksIntegrationService booksIntegrationService, CommentsIntegrationService commentsIntegrationService, RecommendationsIntegrationService recommendationsIntegrationService) {
        this.booksIntegrationService = booksIntegrationService;
        this.commentsIntegrationService = commentsIntegrationService;
        this.recommendationsIntegrationService = recommendationsIntegrationService;
    }

    @GetMapping("/books/{isbn}")
    public Single<BookDetails> getBookInformationsByIsbn(@PathVariable String isbn) {
        Observable<BookDetails> details = Observable.zip(
                booksIntegrationService.findBookByIsbn(isbn),
                commentsIntegrationService.findCommentsForBookWithIsbn(isbn),
                recommendationsIntegrationService.findRecommendationsForBookWithIsbn(isbn),
                (book, comments, recommendations) -> {
                    BookDetails resultBook = new BookDetails();
                    resultBook.setBook(book);
                    resultBook.setComments(comments);
                    resultBook.setRecommendations(recommendations);
                    return resultBook;
                }
        );
        return details.toSingle();
    }

    @GetMapping("/books")
    public Single<List<Book>> getBooks() {
        Observable<List<Book>> books = booksIntegrationService.findAllBooks();
        return books.toSingle();
    }
}