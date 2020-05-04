package com.finkicommunity.controller;

import com.finkicommunity.domain.Group;
import com.finkicommunity.domain.request.group.NewGroupRequest;
import com.finkicommunity.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
