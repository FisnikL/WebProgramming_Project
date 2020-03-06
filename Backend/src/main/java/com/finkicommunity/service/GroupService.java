package com.finkicommunity.service;

import com.finkicommunity.domain.Group;
import com.finkicommunity.domain.request.group.NewGroupRequest;
import com.finkicommunity.exception.group.GroupCodeAlreadyExistsException;
import com.finkicommunity.exception.group.GroupDoesntExistException;
import com.finkicommunity.exception.group.GroupNameAlreadyExistsException;
import com.finkicommunity.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> getAllGroups(){
        return groupRepository.findAll();
    }

    public Group findByCode(String code){
        Group group = groupRepository.findByCode(code);
        if(group == null){
            throw new GroupDoesntExistException("Group with code [" + code + "] not found.");
        }
        return group;
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

        return groupRepository.save(group);
    }

    public List<Group> searchGroups(String searchTerm) {
        return groupRepository.findAllByCodeContainingOrNameContainingOrDescriptionContaining(searchTerm, searchTerm, searchTerm);
    }
}
