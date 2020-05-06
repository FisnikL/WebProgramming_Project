package com.finkicommunity.controller;

import com.finkicommunity.domain.request.post.NewPostRequest;
import com.finkicommunity.domain.request.post.UserThumbsDownPostRequest;
import com.finkicommunity.domain.request.post.UserThumbsUpPostRequest;
import com.finkicommunity.domain.response.post.GroupDetailsPost;
import com.finkicommunity.domain.response.post.HomePostResponse;
import com.finkicommunity.domain.response.post.PostDetailsResponse;
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

    @PostMapping("/thumbup")
    public ResponseEntity<UserThumbsUpPostRequest> thumbUpPost(@RequestBody @Valid UserThumbsUpPostRequest userThumbsUpPostRequest){
        return ResponseEntity.ok(postService.thumbUpPost(userThumbsUpPostRequest));
    }

    @PostMapping("/thumbdown")
    public ResponseEntity<UserThumbsDownPostRequest> thumbDownPost(@RequestBody @Valid UserThumbsDownPostRequest userThumbsDownPostRequest){
        return ResponseEntity.ok(postService.thumbDownPost(userThumbsDownPostRequest));
    }

    @GetMapping("/search")
    public ResponseEntity<List<HomePostResponse>> getPostsFromSearchResult(@RequestParam String q){
        return ResponseEntity.ok(postService.searchPosts(q));
    }

    @GetMapping("/posts-from-group")
    public ResponseEntity<List<GroupDetailsPost>> getPostsFromGroup(@RequestParam String groupCode) {
//        return ResponseEntity.ok().build();
        return ResponseEntity.ok(postService.getPostsFromGroup(groupCode));
    }

}
