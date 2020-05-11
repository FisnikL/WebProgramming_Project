package com.finkicommunity.repository;

import com.finkicommunity.domain.Reply;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ReplyRepository extends PagingAndSortingRepository<Reply, Long> {
    List<Reply> findAll();
    Reply findByContent(String content);
    List<Reply> findAllByPostId(long id);
}
