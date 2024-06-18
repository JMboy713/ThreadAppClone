package com.fastcampus.springsecurity.exception.user;

import com.fastcampus.springsecurity.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ClientErrorException {
    public UserAlreadyExistsException(String message) {
        super( HttpStatus.CONFLICT,message);
    }

    public UserAlreadyExistsException(Long userId) {
        super(HttpStatus.CONFLICT,"user with id "+userId+" is already exists");
    }
    public UserAlreadyExistsException() {
        super(HttpStatus.CONFLICT,"user already exists");
    }
}
