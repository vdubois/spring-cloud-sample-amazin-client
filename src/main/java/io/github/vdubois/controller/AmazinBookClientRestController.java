package io.github.vdubois.controller;

import io.github.vdubois.configuration.RibbonConfiguration;
import io.github.vdubois.security.model.BookDetails;
import io.github.vdubois.security.service.BooksIntegrationService;
import io.github.vdubois.security.service.CommentsIntegrationService;
import io.github.vdubois.security.service.RecommendationsIntegrationService;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Single;

/**
 * Created by vdubois on 08/11/16.
 */
@RestController
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

}