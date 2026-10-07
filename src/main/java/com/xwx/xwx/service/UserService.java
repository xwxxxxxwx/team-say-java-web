package com.xwx.xwx.service;

import com.xwx.xwx.pojo.User;
import com.xwx.xwx.pojo.User.UserType;

import java.util.List;

public interface UserService {
    void registerUser(User user) throws EmailAlreadyExistsException, InvalidUserDataException;
    User loginUser(String email, String password) throws UserNotFoundException, IncorrectPasswordException;
    List<User> getAllUsers();
    List<User> getUsersByType(UserType userType);
    User getUserById(Long userId);
}

