package com.labodc.userprofile.repository;


import com.labodc.userprofile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {


// Tim user theo username
Optional<User> findByUsername(String username);


// Kiem tra username da ton tai hay chua
boolean existsByUsername(String username);
}