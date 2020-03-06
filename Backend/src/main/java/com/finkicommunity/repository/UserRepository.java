package com.finkicommunity.repository;

import com.finkicommunity.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAll();
    @Query("MATCH (u1:User)-[following:FOLLOWING]->(u2:User) " +
            "WHERE ID(u2)= {0} " +
            "RETURN COUNT(following)")
    int countFollowers(long user2Id);
}
