package com.finkicommunity.repository;

import com.finkicommunity.FinkiCommunityApplication;
import com.finkicommunity.controller.GroupController;
import com.finkicommunity.controller.PostController;
import com.finkicommunity.controller.ReplyController;
import com.finkicommunity.controller.UserController;
import com.finkicommunity.domain.*;
import com.finkicommunity.domain.request.group.AddGroupModeratorRequest;
import com.finkicommunity.domain.request.group.NewGroupRequest;
import com.finkicommunity.domain.request.post.NewPostRequest;
import com.finkicommunity.domain.request.post.UserThumbsDownPostRequest;
import com.finkicommunity.domain.request.post.UserThumbsUpPostRequest;
import com.finkicommunity.domain.request.reply.NewReplyRequest;
import com.finkicommunity.domain.request.user.FollowRequest;
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
    private PostController postController;
    private ReplyController replyController;

    private final static Logger log = LoggerFactory.getLogger(FinkiCommunityApplication.class);

    public DbInit(GroupRepository groupRepository, RoleRepository roleRepository, PostRepository postRepository,
                  UserRepository userRepository, ReplyRepository replyRepository, ImageModelRepository imageModelRepository,
                  GroupController groupController, UserController userController, PostController postController, ReplyController replyController) {

        this.groupRepository = groupRepository;
        this.roleRepository = roleRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.replyRepository = replyRepository;
        this.imageModelRepository = imageModelRepository;

        this.groupController = groupController;
        this.userController = userController;
        this.postController = postController;
        this.replyController = replyController;
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

        // CREATE FOLLOWS
        createFollows();

        // CREATE POSTS
        createPosts();

        // CREATE POST THUMBUPS/DOWN
        createPostsThumbsUpsAndDowns();

        // CREAT REPLIES
        createReplies();

        // CREATE REPLY THUMBUPS/DOWNS
        createRepliesThumbsUpsAndDowns();
    }

    private void createRepliesThumbsUpsAndDowns() {
        List<Reply> replies = replyRepository.findAll();
        List<User> users = userRepository.findAll();
        Random random = new Random();

        for(Reply reply: replies) {
            // THUMBUPS
            int numThumbUps = random.nextInt(6);
            for(int j = 0; j < numThumbUps; ++j){
                User u = users.get(random.nextInt(users.size()));

                UserThumbsUpPostRequest thumbsUpPostRequest = new UserThumbsUpPostRequest();
                thumbsUpPostRequest.postId = reply.getId();
                thumbsUpPostRequest.username = u.getUsername();

                replyController.thumbUpReply(thumbsUpPostRequest);
            }

            // THUMBDOWNS
            int numThumbDowns = random.nextInt(6);
            for(int j = 0; j < numThumbDowns; ++j){
                User u = users.get(random.nextInt(users.size()));

                UserThumbsDownPostRequest thumbsDownPostRequest = new UserThumbsDownPostRequest();
                thumbsDownPostRequest.postId = reply.getId();
                thumbsDownPostRequest.username = u.getUsername();

                replyController.thumbDownReply(thumbsDownPostRequest);
            }
        }
    }

    private void createReplies() {
        List<User> users = userRepository.findAll();
        List<Post> posts = postRepository.findAll();
        Random random = new Random();

        for(int i = 1; i <= 200; ++i) {
            NewReplyRequest newReplyRequest = new NewReplyRequest();
            newReplyRequest.content = "replyContent" + i;
            newReplyRequest.username = users.get(random.nextInt(users.size())).getUsername();
            newReplyRequest.postId = posts.get(random.nextInt(posts.size())).getId();

            replyController.createNewReply(newReplyRequest);
        }
    }

    private void createPostsThumbsUpsAndDowns() {
        List<Post> posts = postRepository.findAll();
        List<User> users = userRepository.findAll();
        Random random = new Random();

        for(Post post: posts) {
            // THUMBUPS
            int numThumbUps = random.nextInt(6);
            for(int j = 0; j < numThumbUps; ++j){
                User u = users.get(random.nextInt(users.size()));

                UserThumbsUpPostRequest thumbsUpPostRequest = new UserThumbsUpPostRequest();
                thumbsUpPostRequest.postId = post.getId();
                thumbsUpPostRequest.username = u.getUsername();

                postController.thumbUpPost(thumbsUpPostRequest);
            }

            // THUMBDOWNS
            int numThumbDowns = random.nextInt(6);
            for(int j = 0; j < numThumbDowns; ++j){
                User u = users.get(random.nextInt(users.size()));

                UserThumbsDownPostRequest thumbsDownPostRequest = new UserThumbsDownPostRequest();
                thumbsDownPostRequest.postId = post.getId();
                thumbsDownPostRequest.username = u.getUsername();

                postController.thumbDownPost(thumbsDownPostRequest);
            }
        }
    }


    private void createPosts() {
        List<Group> groups = groupRepository.findAll();
        List<User> users = userRepository.findAll();

        Random random = new Random();
        NewPostRequest newPostRequest;

        for(int i = 1; i<=50; ++i) {
            newPostRequest = new NewPostRequest();
            newPostRequest.title = "title" + i;
            newPostRequest.content = "content" + i;
            Group g = groups.get(random.nextInt(groups.size()));
            newPostRequest.groupCode = g.getCode();
            User user = users.get(random.nextInt(users.size()));
            newPostRequest.username = user.getUsername();

            postController.createNewPost(newPostRequest);
        }
    }

    private void createFollows() {
        List<User> users = userRepository.findAll();
        Random random = new Random();

        for(int i = 0; i<50; ++i){
            User user1 = users.get(random.nextInt(users.size()));
            User user2 = users.get(random.nextInt(users.size()));

            if(!user1.getUsername().equals(user2.getUsername())){
                FollowRequest followRequest = new FollowRequest();
                followRequest.usernameFollowing = user1.getUsername();
                followRequest.usernameFollowed = user2.getUsername();
                userController.follow(followRequest);
            }
        }
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

        roles.add(userRole);
        roles.add(adminRole);

        roleRepository.saveAll(roles);
        log.info("Role [USER] added");
        log.info("Role [ADMIN] added");

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
