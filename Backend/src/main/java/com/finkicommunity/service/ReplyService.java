package com.finkicommunity.service;

import com.finkicommunity.domain.Reply;
import com.finkicommunity.domain.User;
import com.finkicommunity.domain.request.reply.UserLikesReplyRequest;
import com.finkicommunity.domain.response.reply.ReplyResponse;
import com.finkicommunity.exception.reply.ReplyNotFoundException;
import com.finkicommunity.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReplyService {
    private ReplyRepository replyRepository;
    private UserService userService;

    public ReplyService(ReplyRepository replyRepository, UserService userService) {
        this.replyRepository = replyRepository;
        this.userService = userService;
    }

    public Reply createNewReply(Reply reply){
        return replyRepository.save(reply);
    }

    public List<ReplyResponse> getAllReplies(){
        return replyRepository.findAll()
                .stream()
                .map(reply -> convertFromReplyToReplyResponse(reply))
                .collect(Collectors.toList());
    }

    public List<ReplyResponse> getRepliesForPostId(long postId){
        List<ReplyResponse> replies = replyRepository
                .findAllByPostId(postId)
                .stream()
                .map(reply -> convertFromReplyToReplyResponse(reply))
                .collect(Collectors.toList());

        return replies;
    }

    public UserLikesReplyRequest likeReply(UserLikesReplyRequest userLikesReplyRequest){
        String username = userLikesReplyRequest.username;
        Long replyId = userLikesReplyRequest.replyId;

        // If user not found will be thrown exception
        User user = userService.getUserByUsername(username);
        // If reply not found will be thrown exception
        Reply reply = getReplyById(replyId);

        if(userLikesReplyRequest.isLike){
            reply.getLikes().add(user);
        }else{
            reply.getLikes().remove(user);
        }

        replyRepository.save(reply);

        return userLikesReplyRequest;
    }

    public Reply getReplyById(Long replyId){
        Optional<Reply> reply = replyRepository.findById(replyId);
        if(!reply.isPresent()){
            throw new ReplyNotFoundException("Reply with id [" + replyId + "] not found.");
        }
        return reply.get();
    }

    private ReplyResponse convertFromReplyToReplyResponse(Reply reply){
        ReplyResponse replyResponse = new ReplyResponse();

        replyResponse.id = reply.getId();
        replyResponse.content = reply.getContent();
        replyResponse.created = reply.getCreated();
        replyResponse.username = reply.getUser().getUsername();
        replyResponse.numOfLikes = reply.getLikes().size();
        replyResponse.postId = reply.getPost().getId();

        return replyResponse;
    }
}
