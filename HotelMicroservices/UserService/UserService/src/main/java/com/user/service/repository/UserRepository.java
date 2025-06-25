package com.user.service.repository;

import com.user.service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User , String> {
    //if you want to emplement any custom methos and
}
