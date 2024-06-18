package com.fastcampus.springsecurity.service;

import com.fastcampus.springsecurity.exception.post.PostNotFoundException;
import com.fastcampus.springsecurity.model.Post;
import com.fastcampus.springsecurity.model.PostPatchRequestBody;
import com.fastcampus.springsecurity.model.PostPostRequestBody;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private static final List<Post> posts = new ArrayList<>();
    static{
        posts.add(new Post(1L, "Post 1",ZonedDateTime.now()));
        posts.add(new Post(2L, "Post 2", ZonedDateTime.now()));
        posts.add(new Post(3L, "Post 3", ZonedDateTime.now()));
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Optional<Post> getPostByPostId(Long postId) {
        return posts.stream().filter(post -> post.getPostId().equals(postId)).findFirst();
    }


    public Post createPost(PostPostRequestBody postPostRequestBody) {
        var newPostId = posts.stream().mapToLong(Post::getPostId).max().orElse(0L) + 1;
        var newPost = new Post(newPostId, postPostRequestBody.body(), ZonedDateTime.now());
        posts.add(newPost);

        return newPost;
    }

    public Post updatePost(Long postId, PostPatchRequestBody postPatchRequestBody) {
        Optional<Post> postOptional = posts.stream().filter(post -> post.getPostId().equals(postId)).findFirst();
        if(postOptional.isPresent()){
            Post postToUpdate = postOptional.get();
            postToUpdate.setBody(postPatchRequestBody.body());
            return postToUpdate;
        }else{
            throw new PostNotFoundException(postId);// 원하는 http status code를 던질 수 있음
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Post Not Found");// 원하는 http status code를 던질 수 있음
        }

    }

    public void deletePost(Long postId) {
        Optional<Post> postOptional = posts.stream().filter(post -> post.getPostId().equals(postId)).findFirst();
        if(postOptional.isPresent()) {
            posts.remove(postOptional.get());
        }else{
            throw new PostNotFoundException(postId);// 원하는 http status code를 던질 수 있음

//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Post Not Found");// 원하는 http status code를 던질 수 있음
        }
    }
}
