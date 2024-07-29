package com.dinhhieu.FruitWebApp.repository;

import com.dinhhieu.FruitWebApp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryName(String categoryName);

//    @Query(value = "SELECT c FROM Category c " +
//            " WHERE (:categoryName IS NULL  OR lower(c.categoryName) LIKE %:categoryName%) " +
//            " AND (:startCreateDate IS NULL or :endCreateDate is null OR c.createdAt between :startCreateDate and :endCreateDate) " +
//            " AND (:startUpdateDate IS NULL OR :endUpdateDate is null or  c.updatedAt between :startUpdateDate and :endUpdateDate)" )
//    List<Category> searchCategory(@Param("categoryName") String categoryName,
//                                            @Param("startCreateDate") Date startCreateDate,
//                                            @Param("endCreateDate") Date endCreateDate,
//                                            @Param("startUpdateDate") Date startUpdateDate,
//                                            @Param("endUpdateDate") Date endUpdateDate
//                                );

//    @Query(value = "SELECT c FROM Category c " +
//            " WHERE (:categoryName IS NULL OR LOWER(c.categoryName) LIKE CONCAT('%', :categoryName, '%')) " +
//            " AND (:startCreateDate IS NULL OR :endCreateDate IS NULL OR c.createdAt BETWEEN :startCreateDate AND :endCreateDate) " +
//            " AND (:startUpdateDate IS NULL OR :endUpdateDate IS NULL OR c.updatedAt BETWEEN :startUpdateDate AND :endUpdateDate)")
//    List<Category> searchCategory(
//            @Param("categoryName") String categoryName,
//            @Param("startCreateDate") Date startCreateDate,
//            @Param("endCreateDate") Date endCreateDate,
//            @Param("startUpdateDate") Date startUpdateDate,
//            @Param("endUpdateDate") Date endUpdateDate);

    @Query(value = "SELECT c FROM Category c " +
            " WHERE (:categoryName IS NULL OR LOWER(c.categoryName) LIKE CONCAT('%', :categoryName, '%')) " +
            " AND (COALESCE(:startCreateAt, c.createdAt) = c.createdAt OR c.createdAt BETWEEN :startCreateAt AND COALESCE(:endCreateAt, c.createdAt)) " +
            " AND (COALESCE(:startUpdateAt, c.updatedAt) = c.updatedAt OR c.updatedAt BETWEEN :startUpdateAt AND COALESCE(:endUpdateAt, c.updatedAt))")
    List<Category> searchCategory(
            @Param("categoryName") String categoryName,
            @Param("startCreateAt") Date startCreateAt,
            @Param("endCreateAt") Date endCreateAt,
            @Param("startUpdateAt") Date startUpdateAt,
            @Param("endUpdateAt") Date endUpdateAt);

//    @Query(value = "SELECT c FROM Category c " +
//            " WHERE (:categoryName IS NULL OR LOWER(c.categoryName) LIKE CONCAT('%', :categoryName, '%')) " )
//    List<Category> searchCategory(
//            @Param("categoryName") String categoryName
//           );


}
