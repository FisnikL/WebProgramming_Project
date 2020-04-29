package com.finkicommunity.controller;

import com.finkicommunity.domain.request.post.NewPostRequest;
import com.finkicommunity.domain.request.post.UserLikesPostRequest;
import com.finkicommunity.domain.response.HomePostResponse;
import com.finkicommunity.domain.response.PostDetailsResponse;
import com.finkicommunity.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<HomePostResponse>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/home")
    public ResponseEntity<List<HomePostResponse>> getHomePosts(
            @RequestParam Integer page,
            @RequestParam Integer size
    ){
        return ResponseEntity.ok(postService.getHomePosts(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailsResponse> getPostDetailsById(@PathVariable long id){
        return ResponseEntity.ok(postService.getPostDetailsById(id));
    }

    @PostMapping("/new")
    public ResponseEntity<HomePostResponse> createNewPost(@RequestBody @Valid NewPostRequest newPostRequest){
        return ResponseEntity.ok(postService.createNewPost(newPostRequest));
    }

    @PostMapping("/like")
    public ResponseEntity<UserLikesPostRequest> createNewPost(@RequestBody @Valid UserLikesPostRequest userLikesPostRequest){
        return ResponseEntity.ok(postService.likePost(userLikesPostRequest));
    }

}
