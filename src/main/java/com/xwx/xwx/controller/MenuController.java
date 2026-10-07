package com.xwx.xwx.controller;

import com.xwx.xwx.pojo.Menu;
import com.xwx.xwx.pojo.Course;
import com.xwx.xwx.pojo.Quiz;
import com.xwx.xwx.service.InCourseService;
import com.xwx.xwx.service.MenuService;
import com.xwx.xwx.service.CourseService;
import com.xwx.xwx.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private InCourseService inCourseService;
    @GetMapping("/edit")
    public String editCourseMenu(@RequestParam String courseId, Model model) {
        // 获取课程详情和目录
        Course course = inCourseService.getCourseById(courseId);
        List<Menu> courseMenu = inCourseService.getCourseMenuById(courseId);

        // 检查课程是否存在
        if (course != null) {
            // 添加到模型中，供前端渲染
            model.addAttribute("course", course);
            model.addAttribute("courseMenu", courseMenu);

            // 打印目录信息（可选，调试用）
            for (Menu menu : courseMenu) {
                System.out.println("Menu node: " + menu.getMenuNode());
                System.out.println("Quiz Number: " + menu.getQuizNumber());

                // 如果 Menu 对象中包含 quiz_list 信息，打印测验信息
                if (menu.getQuizList() != null) {
                    for (Quiz quiz : menu.getQuizList()) {
                        System.out.println("Quiz ID: " + quiz.getId());
                        System.out.println("Question Order: " + quiz.getQuestionOrder());
                        System.out.println("Question: " + quiz.getQuestion());
                        System.out.println("Answer: " + quiz.getAnswer());
                        System.out.println("Question Type: " + quiz.getQuestionType());
                    }
                } else {
                    System.out.println("No quizzes for this menu item.");
                }
            }

            return "menu"; // 渲染 `menu.html` 页面
        } else {
            return "error"; // 如果课程不存在，返回错误页面
        }
    }

    @GetMapping("/view")
    public String viewMenuPage() {
        return "menu";
    }

    @PostMapping("/saveAll")
    public ResponseEntity<String> saveAll(@RequestBody Map<String, Object> payload) {
        System.out.println(payload);
        String courseId = String.valueOf(payload.get("courseId"));

        menuService.deleteByCourseId(courseId);
        quizService.deleteByCourseId(courseId);
        List<Map<String, Object>> chapters = (List<Map<String, Object>>) payload.get("chapters");

        boolean isMenuInserted = false;

        for (Map<String, Object> chapterData : chapters) {
            String chapterNodeName = (String) chapterData.get("menuNode");

            Integer chapterId = menuService.findNodeIdByMenuNode(courseId, chapterNodeName);
            if (chapterId == null) {
                Menu chapter = new Menu();
                chapter.setCourseId(courseId);
                chapter.setMenuNode(chapterNodeName);
                chapter.setParentId(null);
                chapter.setCategory("chapter");
                chapter.setDescription((String) chapterData.get("description"));
                chapter.setClickCount(0);
                if(chapterData.get("quizNumber") != null){
                    chapter.setQuizNumber(courseId+"-"+chapterData.get("quizNumber"));
                    System.out.println(chapter.getQuizNumber());
                }
//                quizService.deleteByQuizNumber(chapter.getQuizNumber());
                menuService.createMenu(chapter);
                chapterId = chapter.getNodeId();
                isMenuInserted = true;
            }

            List<Map<String, Object>> quizList = (List<Map<String, Object>>) chapterData.get("quizList");
            String quizNumber = String.valueOf(chapterData.get("quizNumber"));

            saveQuizData(courseId, quizNumber, quizList, chapterId);

            List<Map<String, Object>> topics = (List<Map<String, Object>>) chapterData.get("topics");
            for (Map<String, Object> topicData : topics) {
                String topicNodeName = (String) topicData.get("menuNode");

                Integer topicId = menuService.findNodeIdByMenuNode(courseId, topicNodeName);
                if (topicId == null) {
                    Menu topic = new Menu();
                    topic.setCourseId(courseId);
                    topic.setMenuNode(topicNodeName);
                    topic.setParentId(chapterId);
                    topic.setCategory("topic");
                    topic.setDescription((String) topicData.get("description"));
                    topic.setClickCount((Integer) topicData.get("clickCount"));
                    menuService.createMenu(topic);
                    topicId = topic.getNodeId();
                    isMenuInserted = true;
                }

                List<Map<String, Object>> subTopics = (List<Map<String, Object>>) topicData.get("subTopics");
                for (Map<String, Object> subTopicData : subTopics) {
                    String subTopicNodeName = (String) subTopicData.get("menuNode");

                    Integer subTopicId = menuService.findNodeIdByMenuNode(courseId, subTopicNodeName);
                    if (subTopicId == null) {
                        Menu subTopic = new Menu();
                        subTopic.setCourseId(courseId);
                        subTopic.setMenuNode(subTopicNodeName);
                        subTopic.setParentId(topicId);
                        subTopic.setCategory("subtopic");
                        subTopic.setClickCount((Integer) subTopicData.get("clickCount"));
                        menuService.createMenu(subTopic);
                        isMenuInserted = true;
                    }
                }
            }
        }

        if (isMenuInserted) {
            Course course = courseService.findById(courseId);
            course.setReal(true);
            courseService.updateCourse(course);
        }

        return ResponseEntity.ok("All menus and quizzes saved successfully");
    }

    private void saveQuizData(String courseId, String quizNumber, List<Map<String, Object>> quizList, Integer parentId) {
        if (quizList != null && !quizList.isEmpty()) {
            for (Map<String, Object> quizData : quizList) {
                Quiz quiz = new Quiz();
                quiz.setQuizNumber(courseId + "-" + quizNumber);
                quiz.setQuestionOrder((Integer) quizData.get("questionOrder"));
                quiz.setQuestion((String) quizData.get("question"));
                quiz.setAnswer((String) quizData.get("answer"));

                String questionTypeStr = (String) quizData.get("questionType");
                if (questionTypeStr != null && !questionTypeStr.isEmpty()) {
                    quiz.setQuestionType(questionTypeStr);
                } else {
                    throw new IllegalArgumentException("questionType is null or empty");
                }

                quiz.setCreatedAt(LocalDateTime.now());
                quizService.saveQuiz(quiz);
            }
        }
    }

    @PostMapping("/record-click")
    public ResponseEntity<?> recordClick(@RequestBody ClickRecordRequest request) {
        try {
            menuService.incrementClickCount(request.getNodeId(), request.getCourseId());
            return ResponseEntity.ok().body(new ClickRecordResponse("Click recorded successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ClickRecordResponse("Failed to record click: " + e.getMessage()));
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<Void> upload(@RequestParam String courseId) {
        System.out.println(courseId);
        // Move the logic to update is_real to this endpoint
        Course course = courseService.findById(courseId);
        if (course != null) {
            course.setReal(true);  // Set is_real to true
            courseService.updateCourse(course);  // Update the course in the database
        }
        return ResponseEntity.ok().build();
    }
}

class ClickRecordRequest {
    private int nodeId;
    private String courseId;

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}

class ClickRecordResponse {
    private String message;

    public ClickRecordResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}