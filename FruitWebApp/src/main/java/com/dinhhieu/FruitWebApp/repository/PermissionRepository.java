package com.dinhhieu.FruitWebApp.repository;

import com.dinhhieu.FruitWebApp.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,String> {

}
