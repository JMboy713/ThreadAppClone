package com.fastcampus.springsecurity.exception.post;

import com.fastcampus.springsecurity.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class PostNotFoundException extends ClientErrorException {
    public PostNotFoundException(String message) {
        super( HttpStatus.NOT_FOUND,message);
    }

    public PostNotFoundException(Long postId) {
        super(HttpStatus.NOT_FOUND,"post with id "+postId+" is not found");
    }
    public PostNotFoundException() {
        super(HttpStatus.NOT_FOUND,"post is not found");
    }


}
