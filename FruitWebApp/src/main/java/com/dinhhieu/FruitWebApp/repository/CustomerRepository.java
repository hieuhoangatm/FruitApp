package com.dinhhieu.FruitWebApp.repository;

import com.dinhhieu.FruitWebApp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository< Customer,Long> {
    boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);

    @Query("SELECT c from Customer c where (:startDate is null or c.createdAt > :startDate) and (:endDate is null or c.createdAt <:endDate)")
    List<Customer> findCustomersByCreatedAtBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

//    @Query(value = "SELECT * FROM fruit_web.customers c where c.address like %:address% and c.first_name like %:firstName%  and c.email like %:email%",
//            nativeQuery = true)
//    List<Customer> search(String firstName,
//                          String address,
//                          String email);

    @Query(value = "SELECT c FROM Customer c " +
            " WHERE (:firstName IS NULL OR lower(c.firstName) LIKE %:firstName%) " +
            " AND (:address IS NULL OR lower(c.address) LIKE %:address%) " +
            " AND (:email IS NULL OR lower(c.email) LIKE %:email%)")
    List<Customer> search(String firstName,
                          String address,
                          String email);



//    @Query("SELECT c FROM Customer c " +
//            "WHERE (:firstName IS NULL OR lower(c.firstName) LIKE lower(concat('%', :firstName, '%'))) " +
//            "AND (:address IS NULL OR lower(c.address) LIKE lower(concat('%', :address, '%'))) " +
//            "AND (:email IS NULL OR lower(c.email) LIKE lower(concat('%', :email, '%')))")
//    List<Customer> search(@Param("firstName") String firstName,
//                          @Param("address") String address,
//                          @Param("email") String email);

    @Query("SELECT u FROM Customer u JOIN u.roles r JOIN r.permissions p WHERE r.name = :roleName AND p.name = :permissionName")
    List<Customer> findCustomerByRole(String roleName, String permissionName);

    @Query("SELECT u FROM Customer u where (:googleId is null or u.googleId = :googleId)")
    Optional<Customer> findCustomerByGoogleId(String googleId);
}
