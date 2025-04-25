package com.example.demo.reponsitory;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReponsitory extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
}
