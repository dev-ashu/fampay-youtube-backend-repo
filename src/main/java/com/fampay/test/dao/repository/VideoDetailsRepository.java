package com.fampay.test.dao.repository;

import com.fampay.test.dao.entities.VideoDetailsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface VideoDetailsRepository extends CrudRepository<VideoDetailsEntity, Integer>, PagingAndSortingRepository<VideoDetailsEntity, Integer> {

    @Query("select vd from video_details vd where title like :title%")
    Page<VideoDetailsEntity> findByTitle(Pageable pageable, @Param("title") String title);

    @Query("select count(vd) from video_details vd")
    int getTotalRecords();
}
