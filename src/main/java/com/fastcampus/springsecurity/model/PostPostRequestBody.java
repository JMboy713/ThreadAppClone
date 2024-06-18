package com.fastcampus.springsecurity.model;

import lombok.Data;

import java.util.Objects;


public record PostPostRequestBody(String body) { // record  : dto 를 위한 형식}
}
//
//@Data // record  : dto 를 위한 형식
//public class PostPostRequestBody { // post를 post방식으로 request 할때 쓰는 body
//    private String body;
//
//    public PostPostRequestBody(String body) {
//        this.body = body;
//    }
//
//    protected PostPostRequestBody() {
//
//    }
//    public String getBody() {
//        return body;
//    }
//
//    public void setBody(String body) {
//        this.body = body;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        PostPostRequestBody that = (PostPostRequestBody) o;
//        return Objects.equals(body, that.body);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(body);
//    }
//}
