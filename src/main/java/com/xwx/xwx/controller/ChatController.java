package com.xwx.xwx.controller;

import com.xwx.xwx.pojo.ChatHistory;
import com.xwx.xwx.pojo.Course;
import com.xwx.xwx.service.CourseService;
import com.xwx.xwx.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private OpenAiService openAiService;

    private Map<String, List<Map<String, String>>> chatCache = new ConcurrentHashMap<>();

    @PostMapping
    public ResponseEntity<Map<String, String>> handleChat(@RequestBody Map<String, Object> payload) {
        String nodeId = (String) payload.get("nodeId");
        String menuNode = (String) payload.get("menuNode");
        String courseId = (String) payload.get("courseId");
        String message = (String) payload.get("message");
        System.out.println(payload);

        // 1. 获取课程信息
        Course course = courseService.findById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Course not found"));
        }

        String cacheKey = nodeId;

        // 2. 初始化聊天历史
        List<Map<String, String>> chatHistory = chatCache.getOrDefault(cacheKey, new ArrayList<>());

        // 3. 添加用户消息到聊天历史
        chatHistory.add(Map.of("role", "user", "content", message));

        // 4. 生成角色描述（不存储在历史中，只传递给API）
        String roleDescription = String.format(
                "You are a %s teacher assistant called Say, you need to have a brief introduction about yourself when you first connect with the student, and you will discuss the topic of %s with your student under the background of %s within a socratic method.",
                course.getCourseName(), menuNode, course.getCourseDescription()
        );

        // 5. 调用 OpenAI API 获取回复
        String openAiResponse = openAiService.sendChatToOpenAi(chatHistory, course.getAiApi(), roleDescription);

        // 6. 将AI回复添加到聊天历史
        chatHistory.add(Map.of("role", "assistant", "content", openAiResponse));

        // 7. 更新缓存
        chatCache.put(cacheKey, chatHistory);

        // 8. 返回AI的回复
        return ResponseEntity.ok(Map.of("reply", openAiResponse));
    }
}
