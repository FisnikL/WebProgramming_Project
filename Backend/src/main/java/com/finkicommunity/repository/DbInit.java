package com.finkicommunity.repository;

import com.finkicommunity.FinkiCommunityApplication;
import com.finkicommunity.domain.*;
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
    private PostRepository postRepository;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private ReplyRepository replyRepository;
    private final static Logger log = LoggerFactory.getLogger(FinkiCommunityApplication.class);


    public DbInit(GroupRepository groupRepository, PostRepository postRepository, RoleRepository roleRepository, UserRepository userRepository, ReplyRepository replyRepository){
        this.groupRepository = groupRepository;
        this.postRepository = postRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.replyRepository = replyRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // CLEAN DATABASE
        groupRepository.deleteAll();
        postRepository.deleteAll();
        roleRepository.deleteAll();
        userRepository.deleteAll();
        replyRepository.deleteAll();


        log.info("Starting linking up with Neo4j...");

        // CREATING GROUPS
        List<Group> groups = new ArrayList<>();
        Group group;
        for(int i = 0; i < 20; ++i){
            group = new Group();
            group.setCode("code" + (i+1));
            group.setName("name" + (i+1));
            group.setDescription("description" + (i+1));
            groups.add(group);
        }
        groupRepository.saveAll(groups);
        log.info("Lookup each group by name...");
        groups.stream().forEach(g -> log.info("\t" + groupRepository.findByName(g.getName()).toString()));


        // CREATING ROLES
        List<Role> roles = new ArrayList<>(2);
        Role userRole = new Role("USER");
        // Role moderatorRole = new Role("MODERATOR");
        Role adminRole = new Role("ADMIN");


        roles.add(userRole);
        //roles.add(moderatorRole);
        roles.add(adminRole);

        roleRepository.saveAll(roles);
        log.info("Lookup each role by name...");
        // roles = roleRepository.findAll();
        roles.stream().forEach(r -> log.info("\t" + roleRepository.findByRole(r.getRole()).toString()));

        // CREATING USERS
        Random random = new Random();
        List<User> users = new ArrayList<>();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user;
        for(int i = 1; i <= 30; ++i){
            Set<Role> r = new HashSet<>();
            r.add(roleRepository.findByRole("USER"));
            if(random.nextBoolean()){
                r.add(roleRepository.findByRole("ADMIN"));
            }

            user = new User("username" + i, passwordEncoder.encode("password" + i), r);
//                user.setUsername("username" + i);
//                user.setPassword("password" + i);
            user.setEmail("email" + i);
            user.setFirstName("firstname" + i);
            user.setLastName("lastname" + i);
            user.setBirthdate(System.currentTimeMillis());
            user.setGender(random.nextInt(2) == 1 ? 'F' : 'M');
            users.add(user);
        }
        users.addAll(users);
        userRepository.saveAll(users);

        log.info("Lookup each user by username...");
        users.stream().forEach(u -> log.info("\t" + userRepository.findByUsername(u.getUsername()).toString()));

        // CREATE GROUP MODERATORS
        for(Group tempGroup: groups){
            for(int i = 0; i < 3; ++i){
                User u = users.get(random.nextInt(users.size()));
                tempGroup.addModerator(u);
            }
        }

        groupRepository.saveAll(groups);
        log.info("Saved groups");

        // CREATE FOLLOWS
        users = userRepository.findAll();
        for(int i = 0; i<50; ++i){
            User user1 = users.get(random.nextInt(users.size()));
            User user2 = users.get(random.nextInt(users.size()));
            if(!user1.getUsername().equals(user2.getUsername())){
                user1.getFollowing().add(user2);
                log.info("\t" + user1.getUsername() + " FOLLOWING " + user2.getUsername());
            }else{
                i--;
            }
        }

        // CREATING POSTS
        List<Post> posts = new ArrayList<>();
        Post post;

        for(int i = 1; i<=50; ++i){
            post = new Post();
            post.setTitle("title" + i);
            post.setContent("content" + i);
            Group g = groups.get(random.nextInt(groups.size()));
            post.setGroup(g);
            User u = users.get(random.nextInt(users.size()));
            post.setUser(u);
            int numThumbUps = random.nextInt(6);
            for(int j = 0; j < numThumbUps; ++j){
                u = users.get(random.nextInt(users.size()));
                post.getThumbUps().add(u);
            }
            int numThumbDowns = random.nextInt(6);
            for(int j = 0; j < numThumbDowns; ++j){
                u = users.get(random.nextInt(users.size()));
                if(!post.getThumbUps().contains(u)){
                    post.getThumbDowns().add(u);
                }

            }
            posts.add(post);
        }
        postRepository.saveAll(posts);
        log.info("Lookup each post by name...");
        posts.stream().forEach(p -> log.info("\t" + postRepository.findByTitle(p.getTitle()).toString()));


        // CREATING REPLIES
        List<Reply> replies = new ArrayList<>();
        for(int i = 1; i<= 200; ++i){
            Reply reply = new Reply();
            reply.setContent("replyContent" + i);
            reply.setUser(users.get(random.nextInt(users.size())));
            reply.setPost(posts.get(random.nextInt(posts.size())));

            int numLikes = random.nextInt(6);
            for(int j = 0; j < numLikes; ++j){
                User u = users.get(random.nextInt(users.size()));
                reply.getLikes().add(u);
            }

            replies.add(reply);
        }
        replyRepository.saveAll(replies);
        log.info("Lookup each reply...");
        replies.stream().forEach(r -> log.info("\t" + replyRepository.findByContent(r.getContent()).toString()));
    }
}
