package com.xwx.xwx.service;

import com.xwx.xwx.pojo.User;
import com.xwx.xwx.pojo.User.UserType;
import com.xwx.xwx.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public void registerUser(User user) throws EmailAlreadyExistsException, InvalidUserDataException {
        if (user == null || user.getEmail() == null || user.getPassword() == null) {
            throw new InvalidUserDataException("User data is incomplete");
        }

        if (userMapper.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        user.setCreatedAt(LocalDateTime.now());
        if (user.getUserType() == null) {
            user.setUserType(UserType.student);
        }
        userMapper.insert(user);
    }

    @Override
    public User loginUser(String email, String password) throws UserNotFoundException, IncorrectPasswordException {
        User user = userMapper.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        if (!user.getPassword().equals(password)) {
            throw new IncorrectPasswordException("Incorrect password");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.list();
    }

    @Override
    public List<User> getUsersByType(UserType userType) {
        return userMapper.listByUserType(userType);
    }

    @Override
    public User getUserById(Long userId) {
        return userMapper.findByUserId(userId);
    }
}