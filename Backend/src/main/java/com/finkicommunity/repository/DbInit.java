package com.finkicommunity.repository;

import com.finkicommunity.FinkiCommunityApplication;
import com.finkicommunity.controller.GroupController;
import com.finkicommunity.controller.UserController;
import com.finkicommunity.domain.*;
import com.finkicommunity.domain.request.group.AddGroupModeratorRequest;
import com.finkicommunity.domain.request.group.NewGroupRequest;
import com.finkicommunity.domain.request.user.RegisterUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DbInit implements CommandLineRunner {
    private GroupRepository groupRepository;
    private RoleRepository roleRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;
    private ReplyRepository replyRepository;
    private ImageModelRepository imageModelRepository;

    private GroupController groupController;
    private UserController userController;

    private final static Logger log = LoggerFactory.getLogger(FinkiCommunityApplication.class);

    public DbInit(GroupRepository groupRepository, RoleRepository roleRepository, PostRepository postRepository,
                  UserRepository userRepository, ReplyRepository replyRepository, ImageModelRepository imageModelRepository,
                  GroupController groupController, UserController userController) {
        this.groupRepository = groupRepository;
        this.roleRepository = roleRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.replyRepository = replyRepository;
        this.imageModelRepository = imageModelRepository;

        this.groupController = groupController;
        this.userController = userController;
    }

    @Override
    public void run(String... args) throws Exception {

        // CLEAN DATABASE
        groupRepository.deleteAll();
        postRepository.deleteAll();
        roleRepository.deleteAll();
        userRepository.deleteAll();
        replyRepository.deleteAll();
        imageModelRepository.deleteAll();

        log.info("Starting linking up with Neo4j...");

        // CREATING GROUPS
        addGroups();

        // CREATING ROLES
        addRoles();

        // CREATING USERS
        addUsers();

        // CREATE GROUP MODERATORS
        addGroupsModerators();
//
//        // CREATE FOLLOWS
//        users = userRepository.findAll();
//        for(int i = 0; i<50; ++i){
//            User user1 = users.get(random.nextInt(users.size()));
//            User user2 = users.get(random.nextInt(users.size()));
//            if(!user1.getUsername().equals(user2.getUsername())){
//                user1.getFollowing().add(user2);
//                log.info("\t" + user1.getUsername() + " FOLLOWING " + user2.getUsername());
//            }else{
//                i--;
//            }
//        }
//
//        // CREATING POSTS
//        List<Post> posts = new ArrayList<>();
//        Post post;
//
//        for(int i = 1; i<=50; ++i){
//            post = new Post();
//            post.setTitle("title" + i);
//            post.setContent("content" + i);
//            Group g = groups.get(random.nextInt(groups.size()));
//            post.setGroup(g);
//            User u = users.get(random.nextInt(users.size()));
//            post.setUser(u);
//            int numThumbUps = random.nextInt(6);
//            for(int j = 0; j < numThumbUps; ++j){
//                u = users.get(random.nextInt(users.size()));
//                post.getThumbUps().add(u);
//            }
//            int numThumbDowns = random.nextInt(6);
//            for(int j = 0; j < numThumbDowns; ++j){
//                u = users.get(random.nextInt(users.size()));
//                if(!post.getThumbUps().contains(u)){
//                    post.getThumbDowns().add(u);
//                }
//
//            }
//            posts.add(post);
//        }
//        postRepository.saveAll(posts);
//        log.info("Lookup each post by name...");
//        posts.stream().forEach(p -> log.info("\t" + postRepository.findByTitle(p.getTitle()).toString()));
//
//
//        // CREATING REPLIES
//        List<Reply> replies = new ArrayList<>();
//        for(int i = 1; i<= 200; ++i){
//            Reply reply = new Reply();
//            reply.setContent("replyContent" + i);
//            reply.setUser(users.get(random.nextInt(users.size())));
//            reply.setPost(posts.get(random.nextInt(posts.size())));
//
//            int numLikes = random.nextInt(6);
//            for(int j = 0; j < numLikes; ++j){
//                User u = users.get(random.nextInt(users.size()));
//                reply.getLikes().add(u);
//            }
//
//            replies.add(reply);
//        }
//        replyRepository.saveAll(replies);
//        log.info("Lookup each reply...");
//        replies.stream().forEach(r -> log.info("\t" + replyRepository.findByContent(r.getContent()).toString()));
    }

    private void addGroupsModerators() {

        List<Group> groups = groupRepository.findAll();
        List<User> users = userRepository.findAll();

        Random random = new Random();

        for(Group tempGroup: groups){
            for(int i = 0; i < 3; ++i){
                User u = users.get(random.nextInt(users.size()));
                AddGroupModeratorRequest addGroupModeratorRequest = new AddGroupModeratorRequest();
                addGroupModeratorRequest.groupCode = tempGroup.getCode();
                addGroupModeratorRequest.username = u.getUsername();
                groupController.addGroupModerator(addGroupModeratorRequest);
            }
        }
    }

    private void addUsers() {
        Random random = new Random();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        RegisterUserRequest registerUserRequest;

        for(int i = 1; i <= 30; ++i){
            Set<Role> r = new HashSet<>();
            r.add(roleRepository.findByRole("USER"));
            if(random.nextBoolean()){
                r.add(roleRepository.findByRole("ADMIN"));
            }

            registerUserRequest = new RegisterUserRequest();

            registerUserRequest.username = "username" + i;
            registerUserRequest.password = passwordEncoder.encode("password" + i);
//            user.setUsername("username" + i);
//            user.setPassword("password" + i);
            registerUserRequest.email = "email" + i;
            registerUserRequest.firstName = "firstname" + i;
            registerUserRequest.lastName = "lastname" + i;
            // registerUserRequest.birthdate =  System.currentTimeMillis());
            registerUserRequest.gender = random.nextInt(2) == 1 ? 'F' : 'M';

            userController.register(registerUserRequest);
        }
    }

    private void addRoles() {
        List<Role> roles = new ArrayList<>(2);
        Role userRole = new Role("USER");
        // Role moderatorRole = new Role("MODERATOR");
        Role adminRole = new Role("ADMIN");

        roleRepository.saveAll(roles);
        log.info("Lookup each role by name...");
        // roles = roleRepository.findAll();
        roles.stream().forEach(r -> log.info("\t" + roleRepository.findByRole(r.getRole()).toString()));
    }

    private void addGroups() {
        NewGroupRequest newGroupRequest;
        for(int i = 0; i < 20; ++i){
            newGroupRequest = new NewGroupRequest();

            newGroupRequest.code = "code" + (i+1);
            newGroupRequest.name = "name" + (i+1);
            newGroupRequest.description = "description" + (i+1);

            groupController.createNewGroup(newGroupRequest);
        }
    }
}
