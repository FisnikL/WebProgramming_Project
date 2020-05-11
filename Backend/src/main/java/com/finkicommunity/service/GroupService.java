package com.finkicommunity.service;

import com.finkicommunity.FinkiCommunityApplication;
import com.finkicommunity.domain.Group;
import com.finkicommunity.domain.ImageModel;
import com.finkicommunity.domain.User;
import com.finkicommunity.domain.request.group.AddGroupModeratorRequest;
import com.finkicommunity.domain.request.group.NewGroupRequest;
import com.finkicommunity.domain.response.group.GroupDetailsResponse;
import com.finkicommunity.domain.response.group.GroupModeratorResponse;
import com.finkicommunity.exception.group.GroupCodeAlreadyExistsException;
import com.finkicommunity.exception.group.GroupDoesntExistException;
import com.finkicommunity.exception.group.GroupNameAlreadyExistsException;
import com.finkicommunity.repository.GroupRepository;
import com.finkicommunity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class GroupService {
    private GroupRepository groupRepository;
    private UserRepository userRepository;

    private final static Logger log = LoggerFactory.getLogger(FinkiCommunityApplication.class);

    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public List<Group> getAllGroups(){
        List<Group> groups = groupRepository.findAll();
        for(Group group: groups) {
            if(group.getGroupPicture() != null) {
                group.getGroupPicture().setPicByte(decompressBytes(group.getGroupPicture().getPicByte()));
            }
        }
        return groups;
    }

    public Group findByCode(String code){
        Optional<Group> group = groupRepository.findByCode(code);
        if(!group.isPresent()){
            throw new GroupDoesntExistException("Group with code [" + code + "] not found.");
        }
        return group.get();
    }

    public Group createNewGroup(NewGroupRequest newGroupRequest){

        if(groupRepository.existsGroupByCode(newGroupRequest.code)){
            throw new GroupCodeAlreadyExistsException("Group with code [" + newGroupRequest.code + "] already exists!");
        }
        if(groupRepository.existsGroupByName(newGroupRequest.name)){
            throw new GroupNameAlreadyExistsException("Group with name [" + newGroupRequest.name + "] already exists!");
        }

        Group group = new Group();

        group.setCode(newGroupRequest.code);
        group.setName(newGroupRequest.name);
        group.setDescription(newGroupRequest.description);

        Group g = groupRepository.save(group);
        log.info("Group " + g.getName() + " saved!");
        return g;
    }

    public List<Group> searchGroups(String searchTerm) {
        return groupRepository.findAllByCodeContainingOrNameContainingOrDescriptionContaining(searchTerm, searchTerm, searchTerm);
    }

    public Optional<GroupDetailsResponse> getGroupDetails(String code) {
        Optional<Group> group = groupRepository.findByCode(code);
        if(!group.isPresent()){
            return Optional.empty();
        }

        return Optional.of(covertFromGroupToGroupDetailsResponse(group.get()));
    }

    private GroupDetailsResponse covertFromGroupToGroupDetailsResponse(Group group) {
        GroupDetailsResponse groupDetailsResponse = new GroupDetailsResponse();

        groupDetailsResponse.id = group.getId();
        groupDetailsResponse.code = group.getCode();
        groupDetailsResponse.name = group.getName();
        groupDetailsResponse.description = group.getDescription();
//        groupDetailsResponse.groupPicture = group.getGroupPicture();
//        if(groupDetailsResponse.groupPicture != null){
//            groupDetailsResponse.groupPicture.setPicByte(decompressBytes(groupDetailsResponse.groupPicture.getPicByte()));
//        }
        groupDetailsResponse.moderators = convertFromUserToGroupModeratorResponse(group.getModerators());
        return groupDetailsResponse;
    }

    private Set<GroupModeratorResponse> convertFromUserToGroupModeratorResponse(Set<User> moderators) {
        return moderators.stream().map(
                user -> {
                    GroupModeratorResponse groupModeratorResponse = new GroupModeratorResponse();
                    groupModeratorResponse.username = user.getUsername();
                    groupModeratorResponse.firstName = user.getFirstName();
                    groupModeratorResponse.lastName = user.getLastName();
                    return groupModeratorResponse;
                }
        ).collect(Collectors.toSet());
    }

    public ResponseEntity<String> saveImage(String groupCode, MultipartFile imageFile) throws Exception {
        System.out.println("Original Image Byte Size - " + imageFile.getBytes().length);
        ImageModel img = new ImageModel(imageFile.getOriginalFilename(), imageFile.getContentType(), compressBytes(imageFile.getBytes()));
        Optional<Group> groupOptional = groupRepository.findByCode(groupCode);
        if(groupOptional.isPresent()){
            Group group = groupOptional.get();
            group.setGroupPicture(img);
            groupRepository.save(group);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<ImageModel> getGroupImage(String groupCode) {
        Optional<Group> groupOptional = groupRepository.findByCode(groupCode);
        if(groupOptional.isPresent()){
            Group group = groupOptional.get();
            ImageModel img = group.getGroupPicture();
            if(img != null){
                img.setPicByte(decompressBytes(img.getPicByte()));
                return ResponseEntity.ok(img);
            }
            return ResponseEntity.ok(new ImageModel());
        }
        return ResponseEntity.ok(new ImageModel());
    }

    // compress the image bytes before storing it in the database
    private static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while(!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        }catch(IOException e){

        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    private static byte[] decompressBytes(byte[] data){
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while(!inflater.finished()){
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        }catch (IOException ioe) {

        }catch (DataFormatException e){

        }
        return outputStream.toByteArray();
    }


    public ResponseEntity<String> addGroupModerator(AddGroupModeratorRequest addGroupModeratorRequest) {
        Optional<Group> groupOptional = groupRepository.findByCode(addGroupModeratorRequest.groupCode);
        if(!groupOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }

        User user = userRepository.findByUsername(addGroupModeratorRequest.username);
        if(user == null){
            return ResponseEntity.notFound().build();
        }

        Group group = groupOptional.get();

        group.addModerator(user);

        log.info("User [" +user.getUsername() + "] added as moderator on group [" + group.getName() + "]");
        return ResponseEntity.ok().build();
    }
}
