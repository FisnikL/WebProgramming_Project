package com.finkicommunity.controller;

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

    @PostMapping("/like")
    public ResponseEntity<UserLikesReplyRequest> likeReply(@RequestBody @Valid UserLikesReplyRequest userLikesReplyRequest){
        return ResponseEntity.ok(replyService.likeReply(userLikesReplyRequest));
    }
}
