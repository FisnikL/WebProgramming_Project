package com.finkicommunity.repository;

import com.finkicommunity.domain.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupRepository extends CrudRepository<Group, Long> {
    Group findByName(String name);
    List<Group> findAll();
    List<Group> findAllByCodeContainingOrNameContainingOrDescriptionContaining(String searchTerm1, String searchTerm2, String searchTerm3);
    boolean existsGroupByCode(String code);
    boolean existsGroupByName(String name);
    Group findByCode(String code);
}
