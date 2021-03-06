package com.sorsix.finkicommunity.services;

import com.sorsix.finkicommunity.FinkiCommunityApplication;
import com.sorsix.finkicommunity.domain.entities.Post;
import com.sorsix.finkicommunity.domain.entities.User;
import com.sorsix.finkicommunity.domain.enums.Role;
import com.sorsix.finkicommunity.domain.requests.*;
import com.sorsix.finkicommunity.domain.responses.user.FollowResponse;
import com.sorsix.finkicommunity.domain.responses.user.SearchUserResponse;
import com.sorsix.finkicommunity.domain.responses.user_details.UserDetailsFollow;
import com.sorsix.finkicommunity.domain.responses.user_details.UserDetailsPost;
import com.sorsix.finkicommunity.domain.responses.user_details.UserDetailsResponse;
import com.sorsix.finkicommunity.repository.PostRepository;
import com.sorsix.finkicommunity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {
    private final static Logger log = LoggerFactory.getLogger(FinkiCommunityApplication.class);

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private PostRepository postRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.postRepository = postRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) { return userRepository.findById(id);}

    public Set<Post> getUserPosts(Long id) throws UsernameNotFoundException{
        return userRepository
                .findById(id)
                .map(
                        user -> user.getPosts()
                )
                .orElseThrow(
                        () -> new UsernameNotFoundException("No user found with id " + id)
                );
    }

    public UserDetailsResponse getUserDetails(String username, String loggedInUsername) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(
                        user -> {
                            UserDetailsResponse userDetailsResponse = new UserDetailsResponse();

                            userDetailsResponse.userId = user.getUserId();
                            userDetailsResponse.username = user.getUsername();
                            userDetailsResponse.email = user.getEmail();
                            userDetailsResponse.firstName = user.getFirstName();
                            userDetailsResponse.lastName = user.getLastName();
                            userDetailsResponse.sex = user.getSex();
                            userDetailsResponse.birthdate = user.getBirthdate();
                            userDetailsResponse.role = user.getRole();

                            userDetailsResponse.numberOfPosts = user.getNumberOfPosts();
                            userDetailsResponse.numberOfFollowings = user.getNumberOfFollowings();
                            userDetailsResponse.numberOfFollowers = user.getNumberOfFollowers();

                            // POSTS
                            Set<Post> userPosts = user.getPosts();
                            List<UserDetailsPost> userDetailsPosts = new ArrayList<>();
                            UserDetailsPost userDetailsPost;
                            for (Post post : userPosts) {
                                userDetailsPost = new UserDetailsPost();

                                userDetailsPost.id = post.getPostId();
                                userDetailsPost.timeOfPost = post.getTimestamp();
                                userDetailsPost.courseName = post.getCourse().getCourseName();
                                userDetailsPost.title = post.getTitle();
                                String content = post.getContent();
                                userDetailsPost.content = post.getContent().substring(0, content.length() > 50 ? 50 : content.length());

                                userDetailsPosts.add(userDetailsPost);
                            }
                            userDetailsResponse.userDetailsPosts = userDetailsPosts;

                            // POSTS LIKED
                            Set<Post> userPostsLiked = user.getPostsLiked();

                            userDetailsResponse.numberOfPostsLiked = userPostsLiked.size();

                            List<UserDetailsPost> userDetailsPostsLiked = new ArrayList<>();
                            UserDetailsPost userDetailsPostLiked;
                            for (Post postLiked : userPostsLiked) {
                                userDetailsPostLiked = new UserDetailsPost();

                                userDetailsPostLiked.id = postLiked.getPostId();
                                userDetailsPostLiked.timeOfPost = postLiked.getTimestamp();
                                userDetailsPostLiked.courseName = postLiked.getCourse().getCourseName();
                                userDetailsPostLiked.title = postLiked.getTitle();
                                String content = postLiked.getContent();
                                userDetailsPostLiked.content = content.substring(0, content.length() > 50 ? 50 : content.length());

                                userDetailsPostsLiked.add(userDetailsPostLiked);
                            }
                            userDetailsResponse.userDetailsPostsLiked = userDetailsPostsLiked;

                            // FOLLOWINGS
                            Set<User> userFollowings = user.getFollowings();
                            List<UserDetailsFollow> userDetailsFollows = new ArrayList<>();
                            UserDetailsFollow userDetailsFollow;
                            for (User following : userFollowings) {
                                userDetailsFollow = new UserDetailsFollow();

                                userDetailsFollow.id = following.getUserId();
                                userDetailsFollow.username = following.getUsername();
                                userDetailsFollow.firstName = following.getFirstName();
                                userDetailsFollow.lastName = following.getLastName();

                                userDetailsFollows.add(userDetailsFollow);
                            }
                            userDetailsResponse.userDetailsFollowings = userDetailsFollows;


                            // FOLLOWERS
                            Set<User> userFollowers = user.getFollowers();
                            List<UserDetailsFollow> userDetailsFollowers = new ArrayList<>();
                            UserDetailsFollow userDetailsFollower;
                            for (User follower : userFollowers) {
                                userDetailsFollower = new UserDetailsFollow();

                                userDetailsFollower.id = follower.getUserId();
                                userDetailsFollower.username = follower.getUsername();
                                userDetailsFollower.firstName = follower.getFirstName();
                                userDetailsFollower.lastName = follower.getLastName();

                                userDetailsFollowers.add(userDetailsFollower);
                            }
                            userDetailsResponse.userDetailsFollowers = userDetailsFollowers;

                            if (loggedInUsername != null) {
                                userRepository
                                        .findByUsername(loggedInUsername)
                                        .map(
                                                userLogged -> {
                                                    if (userLogged.getFollowings().contains(user)) {
                                                        userDetailsResponse.isFollowing = true;
                                                    }
                                                    return userLogged;
                                                }
                                        )
                                        .orElseThrow(
                                                ()->new UsernameNotFoundException("No user found with username " + loggedInUsername)
                                        );

                            }
                            return userDetailsResponse;
                        })
                .orElseThrow(
                        ()->new UsernameNotFoundException("No user found with username " + loggedInUsername)
                );
    }

    public Optional<User> createNewUser(NewUserRequest newUserRequest) {
        User user = new User();

        user.setUsername(newUserRequest.username);
        user.setEmail(newUserRequest.email);
        user.setPassword(passwordEncoder.encode(newUserRequest.password));
        user.setFirstName(newUserRequest.firstName);
        user.setLastName(newUserRequest.lastName);
        user.setSex(newUserRequest.sex);
        user.setBirthdate(newUserRequest.birthdate);
        if(newUserRequest.username.equals("admin")){
            user.setRole(Role.ADMIN);
        }
        else if(newUserRequest.username.equals("moderator")){
            user.setRole(Role.MODERATOR);
        }
        else{
            user.setRole(Role.USER);
        }


        try {
            log.info("User with username [" + user.getUsername() + "] added!");
            return Optional.of(userRepository.save(user));
        } catch (RuntimeException ex) {
            return Optional.empty();
        }
    }

    public Optional<FollowResponse> addNewFollowing(NewFollowingRequest newFollowingRequest) {

       User userFollowing = userRepository
               .findByUsername(newFollowingRequest.usernameFollowing)
               .orElseGet(
                       null
               );

       if(userFollowing == null)
           return Optional.empty();

       User userFollowed = userRepository
               .findById(newFollowingRequest.userIdFollowed)
               .orElseGet(
                       null
               );

       if(userFollowed == null){
           return Optional.empty();
       }

        if(userFollowing.getFollowings().contains(userFollowed)) {
            userFollowing.removeFollowing(userFollowed);
            log.info("User [" + userFollowing.getUsername() + "] unfollows [" + userFollowed.getUsername() + "]");
        }else{
            userFollowing.addNewFollowing(userFollowed);
            log.info("User [" + userFollowing.getUsername() + "] follows [" + userFollowed.getUsername() + "]");
        }
        try {
            userRepository.save(userFollowing);
            userRepository.save(userFollowed);

            FollowResponse followResponse = new FollowResponse();
            followResponse.id = userFollowing.getUserId();
            followResponse.username = userFollowing.getUsername();
            followResponse.firstName = userFollowing.getFirstName();
            followResponse.lastName = userFollowing.getLastName();

            return Optional.of(followResponse);
        }catch(Exception ex) {
            return Optional.empty();
        }
    }

    public Optional<NewPostLikeRequest> newPostLike(NewPostLikeRequest newPostLikeRequest) {
        User user = userRepository
                .findByUsername(newPostLikeRequest.username)
                .orElseGet(
                        null
                );

        if(user == null)
            return Optional.empty();

        Post post = postRepository
                .findById(newPostLikeRequest.postId)
                .orElseGet(
                        null
                );

        if(post == null){
            return Optional.empty();
        }

        if(user.getPostsLiked().contains(post)) {
            user.removePostLiked(post);
            log.info("User [" + user.getUsername() + "] unlikes Post [" + post.getPostId() +"]");
        }else{
            user.addPostLiked(post);
            log.info("User [" + user.getUsername() + "] likes Post [" + post.getPostId() + "]");
        }
        try {
            userRepository.save(user);
            postRepository.save(post);
            return Optional.of(newPostLikeRequest);
        }catch(Exception ex) {
            return Optional.empty();
        }
    }

    public List<SearchUserResponse> getResultFromSearch(String q){
        List<User> result = userRepository.findByUsernameContainingOrderByUsername(q);

        List<SearchUserResponse> resultUsers = new ArrayList<>();
        SearchUserResponse searchUserResponse;
        for(User user : result){
            searchUserResponse = new SearchUserResponse();

            searchUserResponse.userId = user.getUserId();
            searchUserResponse.username = user.getUsername();
            searchUserResponse.firstName = user.getFirstName();
            searchUserResponse.lastName = user.getLastName();
            searchUserResponse.role = user.getRole().toString();

            resultUsers.add(searchUserResponse);
        }
        return resultUsers;
    }

    public RoleChangeRequest changeRole(RoleChangeRequest roleChangeRequest) throws UsernameNotFoundException{
            return userRepository
                    .findByUsername(roleChangeRequest.username)
                    .map(
                            user -> {
                                user.setRole(roleChangeRequest.role);
                                userRepository.save(user);
                                return roleChangeRequest;
                            }
                    )
                    .orElseThrow(
                            () -> new UsernameNotFoundException("No user found with username " + roleChangeRequest.username)
                    );
    }
}
