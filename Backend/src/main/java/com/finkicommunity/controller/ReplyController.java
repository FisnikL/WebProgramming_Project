package com.finkicommunity.controller;

import com.finkicommunity.domain.Reply;
import com.finkicommunity.domain.request.post.UserThumbsDownPostRequest;
import com.finkicommunity.domain.request.post.UserThumbsUpPostRequest;
import com.finkicommunity.domain.request.reply.NewReplyRequest;
import com.finkicommunity.domain.request.reply.UserLikesReplyRequest;
import com.finkicommunity.domain.response.reply.ReplyResponse;
import com.finkicommunity.service.ReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/replies")
public class ReplyController {
    private ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping
    public ResponseEntity<List<ReplyResponse>> getAllReplies(@RequestParam(required = false) Long postId){
        if(postId == null){
            return ResponseEntity.ok(replyService.getAllReplies());
        }else{
            return ResponseEntity.ok(replyService.getRepliesForPostId(postId));
        }
    }

    @PostMapping("/new")
    public ResponseEntity<Reply> createNewReply(@RequestBody @Valid NewReplyRequest newReplyRequest){
        return ResponseEntity.ok(replyService.createNewReply(newReplyRequest));
    }

    @PostMapping("/thumbup")
    public ResponseEntity<String> thumbUpReply(@RequestBody @Valid UserThumbsUpPostRequest userThumbsUpReplyRequest){
        return ResponseEntity.ok(replyService.thumbUpReply(userThumbsUpReplyRequest));
    }

    @PostMapping("/thumbdown")
    public ResponseEntity<String> thumbDownReply(@RequestBody @Valid UserThumbsDownPostRequest userThumbsDownReplyRequest){
        return ResponseEntity.ok(replyService.thumbDownReply(userThumbsDownReplyRequest));
    }

}
