package com.xwx.xwx.controller;

import com.xwx.xwx.pojo.Course;
import com.xwx.xwx.pojo.User;
import com.xwx.xwx.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final CourseService courseService;
    private final StudentCourseService studentCourseService;

    @Autowired
    public UserController(UserService userService, CourseService courseService, StudentCourseService studentCourseService) {
        this.userService = userService;
        this.courseService = courseService;
        this.studentCourseService = studentCourseService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.registerUser(user);
            model.addAttribute("successMessage", "Registration successful. Please log in.");
            return "redirect:/api/users/login";
        } catch (EmailAlreadyExistsException e) {
            logger.warn("Registration failed: Email already exists", e);
            model.addAttribute("error", "This email is already registered. Please use a different email.");
        } catch (InvalidUserDataException e) {
            logger.error("Registration failed: Invalid user data", e);
            model.addAttribute("error", "Invalid user data. Please check your input and try again.");
        } catch (Exception e) {
            logger.error("Registration failed: Unexpected error", e);
            model.addAttribute("error", "An unexpected error occurred. Please try again later.");
        }
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        try {
            User user = userService.loginUser(email, password);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userType", user.getUserType());
            session.setAttribute("userId", user.getUserId());

            return switch (user.getUserType()) {
                case teacher -> "redirect:/api/users/dashboardT";
                case student -> "redirect:/api/users/dashboardS";
                default -> {
                    model.addAttribute("error", "Unknown user type");
                    yield "login";
                }
            };
        } catch (UserNotFoundException | IncorrectPasswordException e) {
            logger.error("Login failed: ", e);
            model.addAttribute("error", "Invalid username or password");
        } catch (Exception e) {
            logger.error("Login failed: ", e);
            model.addAttribute("error", "An unexpected error occurred. Please try again later.");
        }
        return "login";
    }

    @GetMapping("/dashboardT")
    public String showDashboardT(Model model, HttpSession session,
                                 @RequestParam(required = false) Boolean creationSuccess) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            logger.warn("UserId not found in session");
            return "redirect:/api/users/login";
        }

        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                logger.error("User not found: " + userId);
                return "redirect:/api/users/login";
            }
            model.addAttribute("user", user);
            if (Boolean.TRUE.equals(creationSuccess)) {
                model.addAttribute("successMessage", "Course created successfully!");
            }
            List<Course> courses = courseService.getCoursesByTeacherId(userId);
            model.addAttribute("courses", courses);

            return "dashboardT";
        } catch (Exception e) {
            logger.error("Failed to get user information: ", e);
            return "redirect:/api/users/login";
        }
    }

    @GetMapping("/dashboardS")
    public String showDashboardS(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            logger.warn("UserId not found in session");
            return "redirect:/api/users/login";
        }

        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                logger.error("User not found: " + userId);
                return "redirect:/api/users/login";
            }
            model.addAttribute("user", user);

            List<Course> courses = studentCourseService.getCoursesByUserId(userId.toString());
            model.addAttribute("courses", courses);

            return "dashboardS";
        } catch (Exception e) {
            logger.error("Failed to get user information: ", e);
            return "redirect:/api/users/login";
        }
    }
}