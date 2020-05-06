package com.finkicommunity.repository;

import com.finkicommunity.domain.Group;
import com.finkicommunity.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    Post findByTitle(String title);
    List<Post> findAll();

    @Query("MATCH (p:Post)-[reply:POST_REPLY]-(r:Reply) " +
            "WHERE ID(p)= {0} " +
            "RETURN COUNT(reply)")
    int countReplies(long postId);

    List<Post> findAllByTitleContainingOrContentContaining(String searchTerm1, String searchTerm2, String searchTerm3);

    @Query("MATCH (p:Post)-[has_group:GROUP_POST]-(g:Group) " +
            "WHERE g.code = {0} " +
            "RETURN p")
    List<Post> getPostsFromGroup(String groupCode);
}
