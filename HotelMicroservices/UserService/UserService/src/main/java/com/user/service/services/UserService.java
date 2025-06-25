package com.user.service.services;

import com.user.service.entities.User;

import java.util.List;

public interface UserService {
    //user operations

    //create
    User saveUser (User user);

    // get all user
    List<User> getAllUser();

    //get single user of given user

    User getUser(String UserId);


    //delete

    //update


}
