package com.dinhhieu.FruitWebApp.repository;

import com.dinhhieu.FruitWebApp.model.DocumentMetadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentMetadata, Long> {
    @Query("select d from DocumentMetadata d where d.name like %:name% ")
    public List<DocumentMetadata> findByName(String name);

//    @Query("select d from DocumentMetadata d where d.extension like %:extention%")
    public List<DocumentMetadata> findByExtension(String extension);

    public List<DocumentMetadata> findByDescription(String description);

    public List<DocumentMetadata> findByCreateDateBetween(Date startDate, Date endDate);

    Page<DocumentMetadata> findAll(Pageable pageable);

    @Query("select d from DocumentMetadata d where d.name like %:name%")
    Page<DocumentMetadata> findByName(Pageable pageable, String name);
}
