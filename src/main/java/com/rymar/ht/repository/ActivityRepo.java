package com.rymar.ht.repository;

import com.rymar.ht.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ActivityRepo extends JpaRepository<Activity,UUID> {
    @Query("SELECT a FROM Activity a WHERE a.createdAt >= :weekAgo AND (:name IS NULL OR LOWER(a.name) = LOWER(:name))")
    List<Activity> findByWeekAndNameIgnoreCase(@Param("weekAgo") LocalDateTime weekAgo, @Param("name") String name);
}
