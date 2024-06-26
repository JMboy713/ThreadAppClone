package com.fastcampus.springsecurity.Controller;

import com.fastcampus.springsecurity.model.Post;
import com.fastcampus.springsecurity.model.PostPatchRequestBody;
import com.fastcampus.springsecurity.model.PostPostRequestBody;
import com.fastcampus.springsecurity.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {


    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;





    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        logger.info("GET /api/v1v/posts ");
        var posts = postService.getPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostByPostId(@PathVariable Long postId) {
        logger.info("GET /api/v1v/posts/{}",postId);
        Optional<Post> post = postService.getPostByPostId(postId);

        return post.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); // 없으면 404 error를 던짐
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostPostRequestBody postPostRequestBody) {

        logger.info("post /api/v1v/posts");
        var post = postService.createPost(postPostRequestBody); // 타입추론이 가능할 경우 사용 var ( java 10 )
        return ResponseEntity.ok(post); // 201은 바디에 값을 넣어서 보내지 못한다.
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId,@RequestBody PostPatchRequestBody postPatchRequestBody) {

        logger.info("patch /api/v1v/posts/{} ",postId);
        var post = postService.updatePost(postId, postPatchRequestBody);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }



}
