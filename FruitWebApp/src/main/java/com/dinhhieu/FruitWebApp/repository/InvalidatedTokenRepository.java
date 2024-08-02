package com.dinhhieu.FruitWebApp.repository;

import com.dinhhieu.FruitWebApp.model.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
