package com.finkicommunity.service;

import com.finkicommunity.FinkiCommunityApplication;
import com.finkicommunity.domain.Group;
import com.finkicommunity.domain.Post;
import com.finkicommunity.domain.User;
import com.finkicommunity.domain.request.post.NewPostRequest;
import com.finkicommunity.domain.request.post.UserThumbsDownPostRequest;
import com.finkicommunity.domain.request.post.UserThumbsUpPostRequest;
import com.finkicommunity.domain.response.post.GroupDetailsPost;
import com.finkicommunity.domain.response.post.HomePostResponse;
import com.finkicommunity.domain.response.post.PostDetailsResponse;
import com.finkicommunity.domain.response.reply.ReplyResponse;
import com.finkicommunity.exception.post.PostNotFoundException;
import com.finkicommunity.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final static Logger log = LoggerFactory.getLogger(FinkiCommunityApplication.class);

    private PostRepository postRepository;
    private GroupService groupService;
    private UserService userService;
    private ReplyService replyService;

    public PostService(
            PostRepository postRepository,
            GroupService groupService,
            UserService userService,
            ReplyService replyService
    ) {
        this.postRepository = postRepository;
        this.groupService = groupService;
        this.userService = userService;
        this.replyService = replyService;
    }

    public List<HomePostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> convertFromPostToHomePostResponse(post))
                .collect(Collectors.toList());
    }

    public List<HomePostResponse> searchPosts(String searchTerm) {
        return postRepository.findAllByTitleContainingOrContentContaining(searchTerm, searchTerm, searchTerm)
                .stream()
                .map(post -> convertFromPostToHomePostResponse(post))
                .collect(Collectors.toList());
    }

    public List<HomePostResponse> getHomePosts(int page, int size) {

        Sort sort = Sort.by("created").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return postRepository.findAll(pageable).stream()
                .map(post -> convertFromPostToHomePostResponse(post))
                .collect(Collectors.toList());
    }

    public PostDetailsResponse getPostDetailsById(long id){
        Optional<Post> post = postRepository.findById(id);
        if(!post.isPresent()){
            throw new PostNotFoundException("Post with id [" + id + "] doesn't exist.");
        }

        PostDetailsResponse postDetailsResponse = convertFromPostToPostDetailsResponse(post.get());

        return postDetailsResponse;
    }

    public UserThumbsUpPostRequest thumbUpPost(UserThumbsUpPostRequest userThumbsUpPostRequest){

        String username = userThumbsUpPostRequest.username;
        Long postId = userThumbsUpPostRequest.postId;

        // If not found will be thrown exception
        User user = userService.getUserByUsername(username);
        // If post not found will be thrown exception
        Post post = getPostById(postId);

        if(!post.getThumbUps().contains(user)){
            // remove user if he thumb downs the post -> one user cannot thumb up and thumb down a post
            post.getThumbDowns().remove(user);
            post.getThumbUps().add(user);
            log.info("User [" + user.getUsername() + "] thumbuped Post [" + post.getId() + "]");
        }else{
            post.getThumbUps().remove(user);
            log.info("User [" + user.getUsername() + "] unthumbuped Post [" + post.getId() + "]");
        }

        postRepository.save(post);

        return userThumbsUpPostRequest;
    }

    public UserThumbsDownPostRequest thumbDownPost(UserThumbsDownPostRequest userThumbsDownPostRequest) {
        String username = userThumbsDownPostRequest.username;
        Long postId = userThumbsDownPostRequest.postId;

        // If not found will be thrown exception
        User user = userService.getUserByUsername(username);
        // If post not found will be thrown exception
        Post post = getPostById(postId);

        if(!post.getThumbDowns().contains(user)){
            // remove user if he thumb ups the post -> one user cannot thumb up and thumb down a post at the same time
            post.getThumbUps().remove(user);
            post.getThumbDowns().add(user);
            log.info("User [" + user.getUsername() + "] thumbdowned Post [" + post.getId() + "]");
        }else{
            post.getThumbDowns().remove(user);
            log.info("User [" + user.getUsername() + "] unthumbdowned Post [" + post.getId() + "]");
        }

        postRepository.save(post);

        return userThumbsDownPostRequest;
    }

    public HomePostResponse createNewPost(NewPostRequest newPostRequest) {
        Post newPost = new Post();

        newPost.setTitle(newPostRequest.title);
        newPost.setContent(newPostRequest.content);

        // If not found will be thrown exception
        User user = userService.getUserByUsername(newPostRequest.username);
        newPost.setUser(user);

        // If not found will be thrown exception
        Group group = groupService.findByCode(newPostRequest.groupCode);
        newPost.setGroup(group);

        Post post = postRepository.save(newPost);
        log.info("Post [" + post.getTitle() + "] added!");
        return convertFromPostToHomePostResponse(post);
    }

    private HomePostResponse convertFromPostToHomePostResponse(Post post){
        HomePostResponse homePostResponse = new HomePostResponse();

        homePostResponse.id = post.getId();
        homePostResponse.title = post.getTitle();
        homePostResponse.content = post.getContent();
        homePostResponse.created = post.getCreated();
        homePostResponse.groupCode = post.getGroup().getCode();
        homePostResponse.groupName = post.getGroup().getName();
        homePostResponse.username = post.getUser().getUsername();
        homePostResponse.numOfThumbUps = post.getThumbUps().size();
        homePostResponse.numOfThumbDowns = post.getThumbDowns().size();
        homePostResponse.numOfReplies = postRepository.countReplies(post.getId());

        return homePostResponse;
    }

    private PostDetailsResponse convertFromPostToPostDetailsResponse(Post post){
        PostDetailsResponse postDetailsResponse = new PostDetailsResponse();

        postDetailsResponse.id = post.getId();
        postDetailsResponse.title = post.getTitle();
        postDetailsResponse.content = post.getContent();
        postDetailsResponse.created = post.getCreated();
        postDetailsResponse.groupCode = post.getGroup().getCode();
        postDetailsResponse.groupName = post.getGroup().getName();
        postDetailsResponse.userName = post.getUser().getUsername();
        postDetailsResponse.numOfLikes = post.getThumbUps().size();

        List<ReplyResponse> replies = replyService.getRepliesForPostId(post.getId());

        postDetailsResponse.replies = replies;
        postDetailsResponse.numOfReplies = replies.size();

        return postDetailsResponse;
    }

    private Post getPostById(long id){
        Optional<Post> post = postRepository.findById(id);
        if(!post.isPresent()){
            throw new PostNotFoundException("Post with id [" + id + "] not found.");
        }
        return post.get();
    }

    public List<GroupDetailsPost> getPostsFromGroup(String groupCode) {
        List<Post> postsFromGroup = postRepository.getPostsFromGroup(groupCode);
        return convertFromPostsToGroupDetailsPosts(postsFromGroup);
    }

    private List<GroupDetailsPost> convertFromPostsToGroupDetailsPosts(List<Post> postsFromGroup) {
        List<GroupDetailsPost> groupDetailsPosts = new ArrayList<>();

        for(Post post: postsFromGroup) {
            GroupDetailsPost groupDetailsPost = new GroupDetailsPost();
            groupDetailsPost.title = post.getTitle();
            groupDetailsPost.content = post.getContent();
//            groupDetailsPost.username = post.getUser().getUsername();

            groupDetailsPosts.add(groupDetailsPost);
        }

        return groupDetailsPosts;
    }
}
