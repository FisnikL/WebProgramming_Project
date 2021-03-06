package com.finkicommunity.service;

import com.finkicommunity.FinkiCommunityApplication;
import com.finkicommunity.domain.Role;
import com.finkicommunity.domain.User;
import com.finkicommunity.domain.request.user.FollowRequest;
import com.finkicommunity.domain.request.user.RegisterUserRequest;
import com.finkicommunity.domain.response.user.UserResponse;
import com.finkicommunity.exception.user.SameUsernameFollowingException;
import com.finkicommunity.exception.user.UserNotFoundException;
import com.finkicommunity.repository.RoleRepository;
import com.finkicommunity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private final static Logger log = LoggerFactory.getLogger(FinkiCommunityApplication.class);

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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

    public User saveNewUser(RegisterUserRequest registerUserRequest){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setUsername(registerUserRequest.username);
        user.setFirstName(registerUserRequest.firstName);
        user.setLastName(registerUserRequest.lastName);
        user.setEmail(registerUserRequest.email);
        user.setPassword(passwordEncoder.encode(registerUserRequest.password));
        user.setGender(registerUserRequest.gender);
//        System.out.println(roleRepository.findByRole("USER"));
        user.getRoles().add(roleRepository.findByRole("USER"));
        User u = userRepository.save(user);
        log.info("User " + u.getUsername() + " saved!");

        return u;
    }

    public ResponseEntity<String> follow(FollowRequest followRequest) {

        if(followRequest.usernameFollowing.equals(followRequest.usernameFollowed)){
            String message = "Both usernames [" + followRequest.usernameFollowing + ", " + followRequest.usernameFollowing + "] are the same. You cannot follow yourself.";
            throw new SameUsernameFollowingException(message);
        }

        String usernameFollowing = followRequest.usernameFollowing;
        User userFollowing = getUserByUsername(usernameFollowing); // Throws UserNotFoundException

        String usernameFollowed = followRequest.usernameFollowed;
        User userFollowed = getUserByUsername(usernameFollowed);

        if(userFollowing.getFollowing().contains(userFollowed)){
            userFollowing.getFollowing().remove(userFollowed);
            log.info("Follow request: " + userFollowing.getUsername() + " UNFOLLOWS " + userFollowed.getUsername());
        }else{
            userFollowing.getFollowing().add(userFollowed);
            log.info("Follow request: " + userFollowing.getUsername() + " FOLLOWS " + userFollowed.getUsername());
        }
        userRepository.save(userFollowing);

        return ResponseEntity.ok().build();
    }

    public boolean doesUsernameExists(String username){
        return userRepository.existsByUsername(username);
//        User user = userRepository.findByUsername(username);
//        System.out.println(user);
//        if(user != null){
//            return true;
//        }
//        return false;
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
//        userResponse.profilePictureUrl = user.getProfilePictureUrl();
        userResponse.isBlocked = user.isBlocked();
        userResponse.roles = new TreeSet<Role>(user.getRoles()).first().getRole();

        return userResponse;
    }
}
