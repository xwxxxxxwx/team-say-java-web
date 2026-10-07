package com.xwx.xwx;

import com.xwx.xwx.mapper.UserMapper;
import com.xwx.xwx.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class XwxApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    @Transactional
    public void testInsertAndFindUser() {
        // 创建测试用户
        User testUser = new User();
        testUser.setUserId(9999999L); // 设置测试用户ID为 long 类型
        testUser.setUsername("testuser"); // 将用户名设置为 testuser
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("password");
        testUser.setUserType(User.UserType.student);

        // 插入用户
        userMapper.insert(testUser);

        // 通过邮箱查找用户
        User foundUser = userMapper.findByEmail(testUser.getEmail());

        // 断言
        assertNotNull(foundUser, "User should not be null");
        assertEquals("testuser", foundUser.getUsername(), "Username should match");
        assertEquals("testuser@example.com", foundUser.getEmail(), "Email should match");

        // 打印用户信息
        System.out.println("Found User: " + foundUser.getUsername() + ", Email: " + foundUser.getEmail());

        // 再次通过邮箱查找用户
        User foundByEmail = userMapper.findByEmail("testuser@example.com");

        // 断言
        assertNotNull(foundByEmail, "User found by email should not be null");
        assertEquals("testuser", foundByEmail.getUsername(), "Username of user found by email should match");

        System.out.println("User found by email: " + foundByEmail.getUsername());
    }
}
