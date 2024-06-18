package com.fastcampus.springsecurity.exception.user;

import com.fastcampus.springsecurity.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ClientErrorException {
    public UserNotFoundException(String message) {
        super( HttpStatus.NOT_FOUND,message);
    }

    public UserNotFoundException(Long userId) {
        super(HttpStatus.NOT_FOUND,"user with id "+userId+" is not found");
    }
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND,"user is not found");
    }
}
