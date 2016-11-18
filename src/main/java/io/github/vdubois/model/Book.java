package io.github.vdubois.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vdubois on 17/11/16.
 */
@Getter
@Setter
public class Book {

    private Long id;

    private String name;

    private Date publicationDate;

    private Set<Author> authors = new HashSet<>();

    private BigDecimal price;

    private String editor;

    private Integer numberOfPages;

    private String isbn;
}
