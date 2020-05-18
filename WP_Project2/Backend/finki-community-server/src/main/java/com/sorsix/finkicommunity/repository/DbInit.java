package com.sorsix.finkicommunity.repository;

import com.sorsix.finkicommunity.controller.CourseController;
import com.sorsix.finkicommunity.controller.PostController;
import com.sorsix.finkicommunity.controller.UserController;
import com.sorsix.finkicommunity.domain.entities.Course;
import com.sorsix.finkicommunity.domain.entities.Post;
import com.sorsix.finkicommunity.domain.entities.User;
import com.sorsix.finkicommunity.domain.enums.*;
import com.sorsix.finkicommunity.domain.requests.*;
import com.sorsix.finkicommunity.domain.responses.user.FollowResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class DbInit implements CommandLineRunner {
    private UserRepository userRepository;
    private CourseRepository courseRepository;
    private PasswordEncoder passwordEncoder;
    private PostRepository postRepository;

    private UserController userController;
    private PostController postController;
    private CourseController courseController;

    public DbInit(UserRepository userRepository, CourseRepository courseRepository, PasswordEncoder passwordEncoder, PostRepository postRepository,
                  UserController userController, PostController postController, CourseController courseController)
    {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
        this.postRepository = postRepository;

        this.userController = userController;
        this.postController = postController;
        this.courseController = courseController;
    }

    @Override
    public void run(String... args) {

        // REMOVE DATA
        this.userRepository.deleteAll();
        this.courseRepository.deleteAll();
        this.postRepository.deleteAll();


        // ADD USERS    TOTAL = 15
        addUsers();

        // ADD FOLLOWINGS   TOTAL = 5
        addFollowings();

        // ADD COURSES  TOTAL = 16
        addCourses();

        // POSTS    TOTAL = 50
        addPosts();

        // USERS LIKES      TOTAL = 22
        addUsersLikes();

    }

    private void addUsersLikes() {
        User fisnik = userRepository.findByUsername("fisnik").get();
        User moderator = userRepository.findByUsername("moderator").get();
        User user5 = userRepository.findByUsername("user5").get();
        User user6 = userRepository.findByUsername("user6").get();
        User user7 = userRepository.findByUsername("user7").get();
        User user8 = userRepository.findByUsername("user8").get();
        User user9 = userRepository.findByUsername("user9").get();
        User user10 = userRepository.findByUsername("user10").get();

        List<Post> posts = postRepository.findAll();

        NewPostLikeRequest newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = fisnik.getUsername();
        newPostLikeRequest.postId = posts.get(0).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = fisnik.getUsername();
        newPostLikeRequest.postId = posts.get(1).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = fisnik.getUsername();
        newPostLikeRequest.postId = posts.get(2).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = moderator.getUsername();
        newPostLikeRequest.postId = posts.get(3).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = moderator.getUsername();
        newPostLikeRequest.postId = posts.get(4).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = moderator.getUsername();
        newPostLikeRequest.postId = posts.get(5).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = moderator.getUsername();
        newPostLikeRequest.postId = posts.get(0).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = user5.getUsername();
        newPostLikeRequest.postId = posts.get(0).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = user5.getUsername();
        newPostLikeRequest.postId = posts.get(1).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = user5.getUsername();
        newPostLikeRequest.postId = posts.get(2).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = user5.getUsername();
        newPostLikeRequest.postId = posts.get(3).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = user5.getUsername();
        newPostLikeRequest.postId = posts.get(4).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = user5.getUsername();
        newPostLikeRequest.postId = posts.get(5).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = user5.getUsername();
        newPostLikeRequest.postId = posts.get(6).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = user5.getUsername();
        newPostLikeRequest.postId = posts.get(7).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = user5.getUsername();
        newPostLikeRequest.postId = posts.get(8).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = user5.getUsername();
        newPostLikeRequest.postId = posts.get(9).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = user6.getUsername();
        newPostLikeRequest.postId = posts.get(0).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = user7.getUsername();
        newPostLikeRequest.postId = posts.get(0).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = user8.getUsername();
        newPostLikeRequest.postId = posts.get(0).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = user9.getUsername();
        newPostLikeRequest.postId = posts.get(0).getPostId();
        userController.newPostLike(newPostLikeRequest);

        newPostLikeRequest = new NewPostLikeRequest();
        newPostLikeRequest.username = user10.getUsername();
        newPostLikeRequest.postId = posts.get(0).getPostId();
        userController.newPostLike(newPostLikeRequest);
    }

    private void addPosts() {
        String content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

        List<Post> posts;
        List<Course> courses = courseRepository.findAll();

        NewPostRequest newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title00";
        newPostRequest.content = "Post00 - " + content;
        newPostRequest.username = "fisnik";
        newPostRequest.courseName = courses.get(9).getCourseName();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title01";
        newPostRequest.content = "Post01 - " + content;
        newPostRequest.username = "fisnik";
        newPostRequest.courseName = courses.get(0).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title02";
        newPostRequest.content = "Post02 - " + content;
        newPostRequest.username = "fisnik";
        newPostRequest.courseName = courses.get(1).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title04";
        newPostRequest.content = "Post04 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.courseName = courses.get(2).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title05";
        newPostRequest.content = "Post05 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.courseName = courses.get(3).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title06";
        newPostRequest.content = "Post06 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.courseName = courses.get(4).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title07";
        newPostRequest.content = "Post07 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.courseName = courses.get(5).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title11";
        newPostRequest.content = "Post11 - " + content;
        newPostRequest.username = "admin";
        newPostRequest.courseName = courses.get(6).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title13";
        newPostRequest.content = "Post13 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.courseName = courses.get(7).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title14";
        newPostRequest.content = "Post14 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.courseName = courses.get(8).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title16";
        newPostRequest.content = "Post16 - " + content;
        newPostRequest.username = "fisnik";
        newPostRequest.courseName = courses.get(9).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title17";
        newPostRequest.content = "Post17 - " + content;
        newPostRequest.username = "fisnik";
        newPostRequest.courseName = courses.get(0).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title18";
        newPostRequest.content = "Post18 - " + content;
        newPostRequest.username = "fisnik";
        newPostRequest.courseName = courses.get(1).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title27";
        newPostRequest.content = "Post27 - " + content;
        newPostRequest.username = "admin";
        newPostRequest.courseName = courses.get(6).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title29";
        newPostRequest.content = "Post29 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.courseName = courses.get(7).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title30";
        newPostRequest.content = "Post30 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.courseName = courses.get(8).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title32";
        newPostRequest.content = "Post32 - " + content;
        newPostRequest.username = "fisnik";
        newPostRequest.courseName = courses.get(9).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title33";
        newPostRequest.content = "Post33 - " + content;
        newPostRequest.username = "fisnik";
        newPostRequest.courseName = courses.get(0).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title34";
        newPostRequest.content = "Post34 - " + content;
        newPostRequest.username = "fisnik";
        newPostRequest.courseName = courses.get(1).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title43";
        newPostRequest.content = "Post43 - " + content;
        newPostRequest.username = "admin";
        newPostRequest.courseName = courses.get(6).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title45";
        newPostRequest.content = "Post45 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.courseName = courses.get(7).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title46";
        newPostRequest.content = "Post46 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.courseName = courses.get(8).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title48";
        newPostRequest.content = "Post48 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.courseName = courses.get(7).getCourseName();;
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title49";
        newPostRequest.content = "Post49 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.courseName = courses.get(8).getCourseName();;
        postController.createNewPost(newPostRequest);

        for(int i = 51; i<= 60; ++i){
            newPostRequest = new NewPostRequest();
            newPostRequest.title = "Title" + i;
            newPostRequest.content = "Post" + i + " - " + content;
            newPostRequest.username = "moderator";
            newPostRequest.courseName = courses.get(15).getCourseName();;
            postController.createNewPost(newPostRequest);
        }

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title61";
        newPostRequest.content = "Post61 - " + content;
        newPostRequest.username = "fisnik";
        newPostRequest.courseName = courses.get(15).getCourseName();;
        postController.createNewPost(newPostRequest);



        // REPLIES
        posts = postRepository.findAll();
        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title03";
        newPostRequest.content = "Post03 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.replyToPostId = posts.get(1).getPostId();
        postController.createNewPost(newPostRequest);


        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title08";
        newPostRequest.content = "Post08 - " + content;
        newPostRequest.username = "fisnik";
        newPostRequest.replyToPostId = posts.get(5).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title09";
        newPostRequest.content = "Post09 - " + content;
        newPostRequest.username = "admin";
        newPostRequest.replyToPostId = posts.get(5).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title10";
        newPostRequest.content = "Post10 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.replyToPostId = posts.get(5).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title12";
        newPostRequest.content = "Post12 - " + content;
        newPostRequest.username = "admin";
        newPostRequest.replyToPostId = posts.get(11).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title15";
        newPostRequest.content = "Post15 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.replyToPostId = posts.get(14).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title19";
        newPostRequest.content = "Post19 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.replyToPostId = posts.get(1).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title20";
        newPostRequest.content = "Post20 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.replyToPostId = posts.get(1).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title21";
        newPostRequest.content = "Post21 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.replyToPostId = posts.get(1).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title22";
        newPostRequest.content = "Post22 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.replyToPostId = posts.get(1).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title23";
        newPostRequest.content = "Post23 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.replyToPostId = posts.get(1).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title24";
        newPostRequest.content = "Post24 - " + content;
        newPostRequest.username = "fisnik";
        newPostRequest.replyToPostId = posts.get(5).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title25";
        newPostRequest.content = "Post25 - " + content;
        newPostRequest.username = "admin";
        newPostRequest.replyToPostId = posts.get(1).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title26";
        newPostRequest.content = "Post26 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.replyToPostId = posts.get(5).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title28";
        newPostRequest.content = "Post28 - " + content;
        newPostRequest.username = "admin";
        newPostRequest.replyToPostId = posts.get(11).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title31";
        newPostRequest.content = "Post31 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.replyToPostId = posts.get(14).getPostId();
        postController.createNewPost(newPostRequest);


        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title35";
        newPostRequest.content = "Post35 - " + content;
        newPostRequest.username = "user5";
        newPostRequest.replyToPostId = posts.get(1).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title36";
        newPostRequest.content = "Post36 - " + content;
        newPostRequest.username = "user5";
        newPostRequest.replyToPostId = posts.get(1).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title37";
        newPostRequest.content = "Post37 - " + content;
        newPostRequest.username = "user5";
        newPostRequest.replyToPostId = posts.get(1).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title38";
        newPostRequest.content = "Post38 - " + content;
        newPostRequest.username = "user5";
        newPostRequest.replyToPostId = posts.get(1).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title39";
        newPostRequest.content = "Post39 - " + content;
        newPostRequest.username = "user5";
        newPostRequest.replyToPostId = posts.get(1).getPostId();
        postController.createNewPost(newPostRequest);

        posts = postRepository.findAll();
        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title40";
        newPostRequest.content = "Post40 - " + content;
        newPostRequest.username = "fisnik";
        newPostRequest.replyToPostId = posts.get(5).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title41";
        newPostRequest.content = "Post41 - " + content;
        newPostRequest.username = "admin";
        newPostRequest.replyToPostId = posts.get(1).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title42";
        newPostRequest.content = "Post42 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.replyToPostId = posts.get(5).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title43";
        newPostRequest.content = "Post43 - " + content;
        newPostRequest.username = "admin";
        newPostRequest.replyToPostId = posts.get(11).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title47";
        newPostRequest.content = "Post47 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.replyToPostId = posts.get(14).getPostId();
        postController.createNewPost(newPostRequest);

        newPostRequest = new NewPostRequest();
        newPostRequest.title = "Title50";
        newPostRequest.content = "Post50 - " + content;
        newPostRequest.username = "moderator";
        newPostRequest.replyToPostId = posts.get(14).getPostId();
        postController.createNewPost(newPostRequest);
}

    private void addCourses() {
        String programs = "";
        programs = Program.KNI.toString() + "," + Program.IKI.toString();
        NewCourseRequest newCourseRequest = new NewCourseRequest();
        newCourseRequest.code = "sp";
        newCourseRequest.courseName = "StrukturnoProgramiranje";
        newCourseRequest.courseDescription = "courseDescription1";
        newCourseRequest.programs = programs;
        newCourseRequest.studyYear = StudyYear.FRESHMAN;
        newCourseRequest.semester = Semester.WINTER;
        newCourseRequest.courseType = CourseType.MANDATORY;
        courseController.createNewCourse(newCourseRequest);

        newCourseRequest = new NewCourseRequest();
        newCourseRequest.code = "vvi";
        newCourseRequest.courseName = "VovedVoInformatika";
        newCourseRequest.courseDescription = "courseDescription2";
        newCourseRequest.programs = Program.KNI.toString();
        newCourseRequest.studyYear = StudyYear.FRESHMAN;
        newCourseRequest.semester = Semester.WINTER;
        newCourseRequest.courseType = CourseType.MANDATORY;
        courseController.createNewCourse(newCourseRequest);

        programs = Program.KNI.toString() + "," + Program.IKI.toString() + "," + Program.MT.toString();
        newCourseRequest = new NewCourseRequest();
        newCourseRequest.code = "dm1";
        newCourseRequest.courseName = "DiskretnaMatematika 1";
        newCourseRequest.courseDescription = "courseDescription3";
        newCourseRequest.programs = programs;
        newCourseRequest.studyYear = StudyYear.FRESHMAN;
        newCourseRequest.semester = Semester.WINTER;
        newCourseRequest.courseType = CourseType.MANDATORY;
        courseController.createNewCourse(newCourseRequest);

        newCourseRequest = new NewCourseRequest();
        newCourseRequest.code = "oop";
        newCourseRequest.courseName = "Objektno-OrientiranoProgramiranje";
        newCourseRequest.courseDescription = "courseDescription4";
        newCourseRequest.programs = Program.KNI.toString();
        newCourseRequest.studyYear = StudyYear.FRESHMAN;
        newCourseRequest.semester = Semester.SUMMER;
        newCourseRequest.courseType = CourseType.MANDATORY;
        courseController.createNewCourse(newCourseRequest);

        programs = Program.KNI.toString() + "," + Program.IKI.toString() + "," + Program.KN.toString();
        newCourseRequest = new NewCourseRequest();
        newCourseRequest.code = "onvd";
        newCourseRequest.courseName = "OsnoviNaVebDizajn";
        newCourseRequest.courseDescription = "courseDescription5";
        newCourseRequest.programs = programs;
        newCourseRequest.studyYear = StudyYear.FRESHMAN;
        newCourseRequest.semester = Semester.SUMMER;
        newCourseRequest.courseType = CourseType.OPTIONAL;
        courseController.createNewCourse(newCourseRequest);

        newCourseRequest = new NewCourseRequest();
        newCourseRequest.code = "aips";
        newCourseRequest.courseName = "AlgoritmiIPodatocniStrukturi";
        newCourseRequest.courseDescription = "courseDescription6";
        newCourseRequest.programs = Program.KNI.toString();
        newCourseRequest.studyYear = StudyYear.SOPHOMORE;
        newCourseRequest.semester = Semester.WINTER;
        newCourseRequest.courseType = CourseType.MANDATORY;
        courseController.createNewCourse(newCourseRequest);

        newCourseRequest = new NewCourseRequest();
        newCourseRequest.code = "np";
        newCourseRequest.courseName = "NaprednoProgramiranje";
        newCourseRequest.courseDescription = "courseDescription7";
        newCourseRequest.programs = Program.KNI.toString();
        newCourseRequest.studyYear = StudyYear.SOPHOMORE;
        newCourseRequest.semester = Semester.WINTER;
        newCourseRequest.courseType = CourseType.OPTIONAL;
        courseController.createNewCourse(newCourseRequest);

        newCourseRequest = new NewCourseRequest();
        newCourseRequest.code = "os";
        newCourseRequest.courseName = "OperativniSistemi";
        newCourseRequest.courseDescription = "courseDescription8";
        newCourseRequest.programs = Program.KNI.toString();
        newCourseRequest.studyYear = StudyYear.SOPHOMORE;
        newCourseRequest.semester = Semester.SUMMER;
        newCourseRequest.courseType = CourseType.MANDATORY;
        courseController.createNewCourse(newCourseRequest);

        newCourseRequest = new NewCourseRequest();
        newCourseRequest.code = "it";
        newCourseRequest.courseName = "InternetTehnologii";
        newCourseRequest.courseDescription = "courseDescription9";
        newCourseRequest.programs = Program.KNI.toString();
        newCourseRequest.studyYear = StudyYear.SOPHOMORE;
        newCourseRequest.semester = Semester.SUMMER;
        newCourseRequest.courseType = CourseType.OPTIONAL;
        courseController.createNewCourse(newCourseRequest);

        newCourseRequest = new NewCourseRequest();
        newCourseRequest.code = "bnp";
        newCourseRequest.courseName = "BaziNaPodatoci";
        newCourseRequest.courseDescription = "courseDescription10";
        newCourseRequest.programs = Program.KNI.toString();
        newCourseRequest.studyYear = StudyYear.JUNIOR;
        newCourseRequest.semester = Semester.WINTER;
        newCourseRequest.courseType = CourseType.MANDATORY;
        courseController.createNewCourse(newCourseRequest);

        newCourseRequest = new NewCourseRequest();
        newCourseRequest.code = "mu";
        newCourseRequest.courseName = "MashinskoUcenje";
        newCourseRequest.courseDescription = "courseDescription11";
        newCourseRequest.programs = Program.KNI.toString();
        newCourseRequest.studyYear = StudyYear.JUNIOR;
        newCourseRequest.semester = Semester.WINTER;
        newCourseRequest.courseType = CourseType.OPTIONAL;
        courseController.createNewCourse(newCourseRequest);

        newCourseRequest = new NewCourseRequest();
        newCourseRequest.code = "k";
        newCourseRequest.courseName = "Kriptografija";
        newCourseRequest.courseDescription = "courseDescription12";
        newCourseRequest.programs = Program.KNI.toString();
        newCourseRequest.studyYear = StudyYear.JUNIOR;
        newCourseRequest.semester = Semester.WINTER;
        newCourseRequest.courseType = CourseType.OPTIONAL;
        courseController.createNewCourse(newCourseRequest);

        newCourseRequest = new NewCourseRequest();
        newCourseRequest.code = "r";
        newCourseRequest.courseName = "Robotika";
        newCourseRequest.courseDescription = "courseDescription13";
        newCourseRequest.programs = Program.KNI.toString();
        newCourseRequest.studyYear = StudyYear.JUNIOR;
        newCourseRequest.semester = Semester.SUMMER;
        newCourseRequest.courseType = CourseType.OPTIONAL;
        courseController.createNewCourse(newCourseRequest);

        newCourseRequest = new NewCourseRequest();
        newCourseRequest.code = "ke";
        newCourseRequest.courseName = "KompjuterskaEtika";
        newCourseRequest.courseDescription = "courseDescription14";
        newCourseRequest.programs = Program.KNI.toString();
        newCourseRequest.studyYear = StudyYear.SENIOR;
        newCourseRequest.semester = Semester.WINTER;
        newCourseRequest.courseType = CourseType.OPTIONAL;
        courseController.createNewCourse(newCourseRequest);

        newCourseRequest = new NewCourseRequest();
        newCourseRequest.code = "vbs";
        newCourseRequest.courseName = "VebBaziraniSistemi";
        newCourseRequest.courseDescription = "courseDescription15";
        newCourseRequest.programs = Program.KNI.toString();
        newCourseRequest.studyYear = StudyYear.SENIOR;
        newCourseRequest.semester = Semester.WINTER;
        newCourseRequest.courseType = CourseType.OPTIONAL;
        courseController.createNewCourse(newCourseRequest);

        newCourseRequest = new NewCourseRequest();
        newCourseRequest.code = "vvb";
        newCourseRequest.courseName = "VovedVoBioinformatika";
        newCourseRequest.courseDescription = "courseDescription16";
        newCourseRequest.programs = Program.KNI.toString();
        newCourseRequest.studyYear = StudyYear.SENIOR;
        newCourseRequest.semester = Semester.SUMMER;
        newCourseRequest.courseType = CourseType.OPTIONAL;
        courseController.createNewCourse(newCourseRequest);
    }

    private void addFollowings() {
        User fisnik = userRepository.findByUsername("fisnik").get();
        User moderator = userRepository.findByUsername("moderator").get();
        User admin = userRepository.findByUsername("admin").get();
        User user5 = userRepository.findByUsername("user5").get();
        User user6 = userRepository.findByUsername("user6").get();

        NewFollowingRequest newFollowingRequest = new NewFollowingRequest();
        newFollowingRequest.usernameFollowing = fisnik.getUsername();
        newFollowingRequest.userIdFollowed = moderator.getUserId();
        userController.addNewFollowing(newFollowingRequest);

        newFollowingRequest = new NewFollowingRequest();
        newFollowingRequest.usernameFollowing = fisnik.getUsername();
        newFollowingRequest.userIdFollowed = admin.getUserId();
        userController.addNewFollowing(newFollowingRequest);

        newFollowingRequest = new NewFollowingRequest();
        newFollowingRequest.usernameFollowing = admin.getUsername();
        newFollowingRequest.userIdFollowed = fisnik.getUserId();
        userController.addNewFollowing(newFollowingRequest);

        newFollowingRequest = new NewFollowingRequest();
        newFollowingRequest.usernameFollowing = moderator.getUsername();
        newFollowingRequest.userIdFollowed = fisnik.getUserId();
        userController.addNewFollowing(newFollowingRequest);

        newFollowingRequest = new NewFollowingRequest();
        newFollowingRequest.usernameFollowing = moderator.getUsername();
        newFollowingRequest.userIdFollowed = admin.getUserId();
        userController.addNewFollowing(newFollowingRequest);

        newFollowingRequest = new NewFollowingRequest();
        newFollowingRequest.usernameFollowing = user5.getUsername();
        newFollowingRequest.userIdFollowed = user6.getUserId();
        userController.addNewFollowing(newFollowingRequest);

        newFollowingRequest = new NewFollowingRequest();
        newFollowingRequest.usernameFollowing = user5.getUsername();
        newFollowingRequest.userIdFollowed = fisnik.getUserId();
        userController.addNewFollowing(newFollowingRequest);
    }

    private void addUsers() {
        NewUserRequest newUserRequest = new NewUserRequest();

        newUserRequest.username = "fisnik";
        newUserRequest.password = "fisnik123";
        newUserRequest.email = "fisnik_email@gmail.com";
        newUserRequest.firstName = "Fisnik";
        newUserRequest.lastName = "Limani";
        newUserRequest.sex = 'M';
        newUserRequest.birthdate = System.currentTimeMillis();
        userController.createNewUser(newUserRequest);

        newUserRequest = new NewUserRequest();
        newUserRequest.username = "admin";
        newUserRequest.password = "admin123";
        newUserRequest.email = "admin_email@gmail.com";
        newUserRequest.firstName = "AdminName";
        newUserRequest.lastName = "AdminSurname";
        newUserRequest.sex = 'M';
        newUserRequest.birthdate = System.currentTimeMillis();
        userController.createNewUser(newUserRequest);

        newUserRequest = new NewUserRequest();
        newUserRequest.username = "moderator";
        newUserRequest.password = "moderator123";
        newUserRequest.email = "moderator_email@gmail.com";
        newUserRequest.firstName = "ModeratorName";
        newUserRequest.lastName = "ModeratorSurname";
        newUserRequest.sex = 'F';
        newUserRequest.birthdate = System.currentTimeMillis();
        userController.createNewUser(newUserRequest);

        Random random = new Random();

        for(int i = 4; i <= 15; ++i){
            newUserRequest = new NewUserRequest();

            newUserRequest.username = "user" + i;
            newUserRequest.password = "user" + i + "123";
            newUserRequest.email = "user" + i + "@gmail.com";
            newUserRequest.firstName = "User" + i + "Name";
            newUserRequest.lastName = "User" + i + "Surname";
            if(random.nextBoolean()){
                newUserRequest.sex = 'F';
            }else{
                newUserRequest.sex = 'M';
            }
            newUserRequest.birthdate = System.currentTimeMillis();

            userController.createNewUser(newUserRequest);
        }
    }
}
