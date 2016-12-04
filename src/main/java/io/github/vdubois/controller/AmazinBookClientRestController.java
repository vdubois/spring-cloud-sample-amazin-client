package io.github.vdubois.controller;

import io.github.vdubois.configuration.RibbonConfiguration;
import io.github.vdubois.model.BookDetails;
import io.github.vdubois.service.BooksIntegrationService;
import io.github.vdubois.service.CommentsIntegrationService;
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

    public AmazinBookClientRestController(BooksIntegrationService booksIntegrationService, CommentsIntegrationService commentsIntegrationService) {
        this.booksIntegrationService = booksIntegrationService;
        this.commentsIntegrationService = commentsIntegrationService;
    }

    @GetMapping("/books/{isbn}")
    public Single<BookDetails> getBookInformationsByIsbn(@PathVariable String isbn) {
        Observable<BookDetails> details = Observable.zip(
                booksIntegrationService.findBookByIsbn(isbn), commentsIntegrationService.findCommentsForBookWithIsbn(isbn),
                (book, comments) -> {
                    BookDetails resultBook = new BookDetails();
                    resultBook.setBook(book);
                    resultBook.setComments(comments);
                    return resultBook;
                }
        );
        return details.toSingle();
    }

}