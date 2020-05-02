package com.finkicommunity.repository;

import com.finkicommunity.domain.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(String role);
    List<Role> findAll();
}
