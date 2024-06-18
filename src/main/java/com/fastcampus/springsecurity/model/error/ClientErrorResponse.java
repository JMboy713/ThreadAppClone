package com.fastcampus.springsecurity.model.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ClientErrorResponse(HttpStatus status, Object message) {


}
