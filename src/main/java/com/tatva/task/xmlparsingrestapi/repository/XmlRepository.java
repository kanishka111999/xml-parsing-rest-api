package com.tatva.task.xmlparsingrestapi.repository;

import com.tatva.task.xmlparsingrestapi.entities.XmlContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/* @Repository class to get data from database layer.*/
@Repository
public interface XmlRepository extends JpaRepository<XmlContent,Long> {
    Page<XmlContent> findByNewspaperNameContainingIgnoreCase(String filterBy, Pageable pageable);

    Page<XmlContent> findByWidth(int width, Pageable pageable);

    Page<XmlContent> findByHeight(int height, Pageable pageable);

    Page<XmlContent> findByDpi(int dpi, Pageable pageable);

    Page<XmlContent> findByUploadTimeBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    Page<XmlContent> findByFilenameContainingIgnoreCase(String filename, Pageable pageable);


}
