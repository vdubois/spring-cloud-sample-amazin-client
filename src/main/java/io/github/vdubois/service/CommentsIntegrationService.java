package io.github.vdubois.service;

import io.github.vdubois.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.util.List;

/**
 * Created by vdubois on 04/12/16.
 */
@Service
public class CommentsIntegrationService {

    @LoadBalanced
    @Autowired
    private RestTemplate restTemplate;

    public Observable<List<Comment>> findCommentsForBookWithIsbn(String isbn) {
        return rx.Observable.just(restTemplate.exchange("http://comments-service/books/" + isbn + "/comments", HttpMethod.GET, null, new ParameterizedTypeReference<List<Comment>>() {}).getBody());
    }
}
