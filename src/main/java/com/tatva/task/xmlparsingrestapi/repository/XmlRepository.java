package com.tatva.task.xmlparsingrestapi.repository;

import com.tatva.task.xmlparsingrestapi.entities.XmlContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @Repository class to get data from database layer.
 */

@Repository
public interface XmlRepository extends JpaRepository<XmlContent,Long> {
    Page<XmlContent> findByNewspaperNameContainingIgnoreCase(String filterBy, Pageable pageable);
}
