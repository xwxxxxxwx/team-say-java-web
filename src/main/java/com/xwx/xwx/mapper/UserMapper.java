package com.xwx.xwx.mapper;

import com.xwx.xwx.pojo.User;
import com.xwx.xwx.pojo.User.UserType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (user_id, username, major, email, user_type, password, created_at) " +
            "VALUES (#{userId}, #{username}, #{major}, #{email}, #{userType}, #{password}, #{createdAt})")
    void insert(User user);

    @Select("SELECT * FROM user WHERE email = #{email}")
    User findByEmail(String email);

    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    User findByUserId(Long userId);

    @Select("SELECT * FROM user")
    List<User> list();

    @Select("SELECT * FROM user WHERE user_type = #{userType}")
    List<User> listByUserType(UserType userType);
}