package io.github.vdubois.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by vdubois on 04/12/16.
 */
@Getter
@Setter
public class BookDetails {

    private Book book;

    private List<Comment> comments;

    private List<Recommendation> recommendations;
}
