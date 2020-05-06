package com.finkicommunity.controller;

import com.finkicommunity.domain.Group;
import com.finkicommunity.domain.ImageModel;
import com.finkicommunity.domain.request.group.NewGroupRequest;
import com.finkicommunity.domain.request.group.UploadGroupImage;
import com.finkicommunity.domain.response.group.GroupDetailsResponse;
import com.finkicommunity.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups(@RequestParam(required = false) String searchTerm){
        if(searchTerm == null){
            return ResponseEntity.ok(groupService.getAllGroups());
        }else{
            return ResponseEntity.ok(groupService.searchGroups(searchTerm));
        }
    }

    @PostMapping("/new")
    public ResponseEntity<Group> createNewGroup(@RequestBody @Valid NewGroupRequest newGroupRequest){
        return ResponseEntity.ok(groupService.createNewGroup(newGroupRequest));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Group>> getGroupsFromSearchResult(@RequestParam String q){
        return ResponseEntity.ok(groupService.searchGroups(q));
    }

    @GetMapping("/details/{code}")
    public ResponseEntity<GroupDetailsResponse> getGroupsDetails(@PathVariable String code){
        return groupService.getGroupDetails(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/details/{groupCode}/uploadImage")
    public ResponseEntity<String> uploadImage(@PathVariable String groupCode, @RequestParam MultipartFile imageFile){

        try {
            return groupService.saveImage(groupCode, imageFile);
        } catch (Exception e) {
            e.printStackTrace();
            // LOG THE ERROR
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/details/{groupCode}/groupImage")
    public ResponseEntity<ImageModel> getGroupImage(@PathVariable String groupCode) {
        return groupService.getGroupImage(groupCode);
    }
}
