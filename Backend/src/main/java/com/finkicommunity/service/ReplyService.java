package com.finkicommunity.service;

import com.finkicommunity.FinkiCommunityApplication;
import com.finkicommunity.domain.Post;
import com.finkicommunity.domain.Reply;
import com.finkicommunity.domain.User;
import com.finkicommunity.domain.request.post.UserThumbsDownPostRequest;
import com.finkicommunity.domain.request.post.UserThumbsUpPostRequest;
import com.finkicommunity.domain.request.reply.NewReplyRequest;
import com.finkicommunity.domain.request.reply.UserLikesReplyRequest;
import com.finkicommunity.domain.response.reply.ReplyResponse;
import com.finkicommunity.exception.reply.ReplyNotFoundException;
import com.finkicommunity.repository.PostRepository;
import com.finkicommunity.repository.ReplyRepository;
import com.finkicommunity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReplyService {
    private final static Logger log = LoggerFactory.getLogger(FinkiCommunityApplication.class);

    private ReplyRepository replyRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private UserService userService;

    public ReplyService(ReplyRepository replyRepository, UserRepository userRepository, PostRepository postRepository, UserService userService) {
        this.replyRepository = replyRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Reply createNewReply(NewReplyRequest newReplyRequest){
        Reply reply = new Reply();

        User user = userRepository.findByUsername(newReplyRequest.username);
        Post post = postRepository.findById(newReplyRequest.postId).get();

        reply.setPost(post);
        reply.setUser(user);
        reply.setContent(newReplyRequest.content);

        Reply r = replyRepository.save(reply);
        log.info("Reply [" + r.getId() + "] added for Post [" + post.getId() + "]");
        return r;
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

//    public UserLikesReplyRequest likeReply(UserLikesReplyRequest userLikesReplyRequest){
//        String username = userLikesReplyRequest.username;
//        Long replyId = userLikesReplyRequest.replyId;
//
//        // If user not found will be thrown exception
//        User user = userService.getUserByUsername(username);
//        // If reply not found will be thrown exception
//        Reply reply = getReplyById(replyId);
//
//        if(userLikesReplyRequest.isLike){
//            reply.getLikes().add(user);
//        }else{
//            reply.getLikes().remove(user);
//        }
//
//        replyRepository.save(reply);
//
//        return userLikesReplyRequest;
//    }

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
        replyResponse.numOfThumbUps = reply.getThumbUps().size();
        replyResponse.numOfThumbDowns = reply.getThumbDowns().size();
        replyResponse.postId = reply.getPost().getId();

        return replyResponse;
    }

    public String thumbUpReply(UserThumbsUpPostRequest userThumbsUpReplyRequest) {
        String username = userThumbsUpReplyRequest.username;
        Long replyId = userThumbsUpReplyRequest.postId;

        User user = userRepository.findByUsername(username);
        Reply reply = replyRepository.findById(replyId).get();

        if(!reply.getThumbUps().contains(user)){
            reply.getThumbDowns().remove(user);
            reply.getThumbUps().add(user);
            log.info("User [" + user.getUsername() + "] thumbuped Reply [" + reply.getId() + "]");
        }
        else{
            reply.getThumbUps().remove(user);
            log.info("User [" + user.getUsername() + "] unthumbuped Reply [" + reply.getId() + "]");
        }

        replyRepository.save(reply);

        return "Successfully saved!";
    }

    public String thumbDownReply(UserThumbsDownPostRequest userThumbsDownReplyRequest) {
        String username = userThumbsDownReplyRequest.username;
        Long replyId = userThumbsDownReplyRequest.postId;

        User user = userRepository.findByUsername(username);
        Reply reply = replyRepository.findById(replyId).get();

        if(!reply.getThumbDowns().contains(user)){
            reply.getThumbUps().remove(user);
            reply.getThumbDowns().add(user);
            log.info("User [" + user.getUsername() + "] thumbdowned Reply [" + reply.getId() + "]");
        }
        else{
            reply.getThumbDowns().remove(user);
            log.info("User [" + user.getUsername() + "] unthumbdowned Reply [" + reply.getId() + "]");
        }

        replyRepository.save(reply);

        return "Successfully saved!";
    }
}
