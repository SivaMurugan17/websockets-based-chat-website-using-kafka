package com.abored.swe.chat_app.repository;

import com.abored.swe.chat_app.entity.MessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<MessageEntity,Long>,
                                            CrudRepository<MessageEntity,Long> {
    List<MessageEntity> findAllByOrderByTimeDesc(Pageable pageable);
}
