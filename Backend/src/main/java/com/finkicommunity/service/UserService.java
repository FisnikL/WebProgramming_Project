package com.finkicommunity.service;

import com.finkicommunity.domain.User;
import com.finkicommunity.domain.request.user.FollowRequest;
import com.finkicommunity.domain.response.UserResponse;
import com.finkicommunity.exception.user.SameUsernameFollowingException;
import com.finkicommunity.exception.user.UserNotFoundException;
import com.finkicommunity.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers(){
        return userRepository.findAll().stream()
                .map(user -> convertFromUsertoUserResponse(user))
                .collect(Collectors.toList());
    }

    public User getUserByUsername(String username){
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UserNotFoundException("User with username [" + username + "] not found.");
        }
        return user;
    }

    public FollowRequest follow(FollowRequest followRequest) {
        if(followRequest.usernameFollowing.equals(followRequest.usernameFollowed)){
            String message = "Both usernames [" + followRequest.usernameFollowing + ", " + followRequest.usernameFollowing + "] are the same. You cannot follow yourself.";
            throw new SameUsernameFollowingException(message);
        }
        String usernameFollowing = followRequest.usernameFollowing;
        User userFollowing = getUserByUsername(usernameFollowing); // Throws UserNotFoundException

        String usernameFollowed = followRequest.usernameFollowed;
        User userFollowed = getUserByUsername(usernameFollowed);

        if(followRequest.isFollow){
            userFollowing.getFollowing().add(userFollowed);
        }else{
            userFollowing.getFollowing().remove(userFollowed);
        }
        userRepository.save(userFollowing);

        return followRequest;
    }

    private UserResponse convertFromUsertoUserResponse(User user){
        UserResponse userResponse = new UserResponse();

        userResponse.id = user.getId();
        userResponse.username = user.getUsername();
        userResponse.password = user.getPassword();
        userResponse.email = user.getEmail();
        userResponse.firstName = user.getFirstName();
        userResponse.lastName = user.getLastName();
        userResponse.gender = user.getGender();
        userResponse.birthdate = user.getBirthdate();
        userResponse.created = user.getCreated();
        userResponse.numFollowings = user.getFollowing().size();
        userResponse.numFollowers = userRepository.countFollowers(user.getId());
        userResponse.rating = user.getRating();
        userResponse.profilePictureUrl = user.getProfilePictureUrl();
        userResponse.isBlocked = user.isBlocked();
        userResponse.roles = user.getRoles();

        return userResponse;
    }
}
