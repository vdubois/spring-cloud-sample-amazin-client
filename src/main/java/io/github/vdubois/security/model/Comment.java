package io.github.vdubois.security.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by vdubois on 17/11/16.
 */
@Getter
@Setter
public class Comment {

    private Integer rating;

    private String content;

    private String customerName;
}
