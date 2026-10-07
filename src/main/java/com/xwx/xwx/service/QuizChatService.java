package com.xwx.xwx.service;

import com.xwx.xwx.mapper.MenuMapper;
import com.xwx.xwx.mapper.QuizMapper;
import com.xwx.xwx.pojo.AnswerRequest;
import com.xwx.xwx.pojo.GPTResponse;
import com.xwx.xwx.pojo.Quiz;
import com.xwx.xwx.pojo.QuizResult;
import com.xwx.xwx.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class QuizChatService {

    @Autowired
    private QuizService quizService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RestTemplate restTemplate;

    // 存储用户测验历史和上下文记录
    private Map<String, List<String>> userQuizHistory = new HashMap<>();

    /**
     * 初始化 Quiz，查询题目列表并生成 GPT 开场白
     */
    public Map<String, Object> initiateQuiz(String quizNumber, String userId) {
        // 根据 quizNumber 从数据库获取题目列表
        List<Quiz> quizList = quizService.getQuizzesByQuizNumber(quizNumber);
        String apiKey = quizService.getAiApiByQuizNumber(quizNumber);

        // 查询当前最大 times 值
        Integer currentMaxTimes = quizService.getMaxTimes(userId, quizNumber);

        // 如果有记录，则新的 times 为 currentMaxTimes + 1，否则为 1
        int newTimes = (currentMaxTimes != null) ? currentMaxTimes + 1 : 1;

        // 将 times 存储在后端的 userQuizHistory 供后续使用
        userQuizHistory.put(userId + "_times", Collections.singletonList(String.valueOf(newTimes)));

        // 初始化用户的历史记录
        userQuizHistory.put(userId, new ArrayList<>());

        // 生成 GPT 开场白并将第一个问题加入提示
        String openingMessage = generateGPTOpening(quizNumber, quizList, apiKey);

        Map<String, Object> quizData = new HashMap<>();
        quizData.put("openingMessage", openingMessage);
        quizData.put("firstQuestion", quizList.get(0).getQuestion());
        System.out.println(apiKey);
        return quizData;
    }


    public Map<String, Object> processAnswer(AnswerRequest answerRequest) {
        // 获取该用户的测验历史记录
        List<String> history = userQuizHistory.get(answerRequest.getUserId());
        // 检查是否有下一道题
        int maxOrder = quizService.findMaxQuestionOrder(answerRequest.getQuizNumber());
        String quizNumber = answerRequest.getQuizNumber();
        String apiKey = quizService.getAiApiByQuizNumber(quizNumber);

        if (history != null) {
            history = new ArrayList<>(history);
        }

        // 根据当前的题目顺序获取当前题目
        String currentQuestion = quizService.findQuestionByOrder(answerRequest.getQuizNumber(), answerRequest.getQuestionOrder());
        String correctAnswer = quizService.findAnswerByOrder(answerRequest.getQuizNumber(), answerRequest.getQuestionOrder());

        // 将更新后的历史记录放回 userQuizHistory
        userQuizHistory.put(answerRequest.getUserId(), history);

        // Construct GPT prompt with the question and history context
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are an assistant named Say. You are now evaluating a student's quiz answers, and you need to give a score between 0 and 10 based on the student's responses, and provide an explanation. " +
                "For low scores, you may provide only an explanation without mentioning the score.\n" +
                "If the student's answer is something like 'I don't know', you should recognize this is not a valid answer and explain the question to them. Your tone should be tactful and encouraging, motivating them to keep learning.\n" +
                "At the beginning, clearly return the score in the format: \n" +
                "'Score: X/10', where X is the score.\n");
        if(answerRequest.getQuestionOrder() < maxOrder) {
            String nextQuestionAsk = quizService.findQuestionByOrder(answerRequest.getQuizNumber(), answerRequest.getQuestionOrder() + 1);
            prompt.append("Finally, ask the next question: " + nextQuestionAsk);
        }
        for (String question : history) {
            prompt.append("Previous Question: ").append(question).append("\n");
        }
        prompt.append("Current Question: ").append(currentQuestion).append("\n");
        prompt.append("Student's Answer: ").append(answerRequest.getAnswer()).append("\n");
        prompt.append("Correct Answer: ").append(correctAnswer).append("\n");


        // 调用 OpenAI API 进行评估
        String gptResponse = callOpenAIApi(prompt.toString(), apiKey);

        // 假设 gptResponse 返回了分数和解释，将返回的 JSON 数据解析成对象
        GPTResponse response = parseGptResponse(gptResponse);  // 您需要实现解析逻辑
        Double score = response.getScore();  // 提取 GPT 返回的分数

        // 更新历史记录
        history.add(currentQuestion);

        // 从 userQuizHistory 中获取存储的 times 值
        List<String> timesList = userQuizHistory.get(answerRequest.getUserId() + "_times");
        int times = Integer.parseInt(timesList.get(0));

        // 保存答案到数据库
        quizService.saveAnswer(answerRequest.getQuizNumber(), answerRequest.getQuestionOrder(), answerRequest.getAnswer(), answerRequest.getUserId(), times, score);

        Map<String, Object> result = new HashMap<>();
        result.put("evaluation", gptResponse);


        if (answerRequest.getQuestionOrder() < maxOrder) {
            // 还有下一道题
            String nextQuestion = quizService.findQuestionByOrder(answerRequest.getQuizNumber(), answerRequest.getQuestionOrder() + 1);
            result.put("nextQuestion", nextQuestion);
        } else {
            // 最后一道题，生成总结反馈和正确率
            System.out.println(times);
            List<QuizResult> quizResults = quizService.getQuizResults(answerRequest.getQuizNumber(), answerRequest.getUserId(), times);
            String finalFeedback = generateFinalFeedback(quizResults, apiKey);
            result.put("finalFeedback", finalFeedback);
        }

        return result;
    }


    /**
     * 调用 OpenAI API 生成 GPT 开场白
     */
    private String generateGPTOpening(String quizNumber, List<Quiz> quizList, String apiKey) {
        // 获取 quiz 的题目数量
        int totalQuestions = quizList.size();
        String chapterName = menuService.getMenuNodeByQuizNumber(quizNumber);

        // 构建 GPT 提示语，介绍 Quiz
        String prompt = "You are an assistant named Say, and now the student is about to take the quiz for the chapter: " + chapterName + ". First, you need to give a brief introduction to the quiz.\n" +
                "This quiz consists of " + totalQuestions + " questions in total. Now you need to generate a short introduction that is encouraging.\n" +
                "Finally, ask the student the first question: " + quizList.get(0).getQuestion() + ". For example: It looks like you've completed the study of chapter " + chapterName + ". Now let's do some questions to check your understanding. You must respond in English.\n" ;



        // 调用 OpenAI API
        return callOpenAIApi(prompt, apiKey);
    }


    /**
     * 调用OpenAI API进行交互
     */
    private String callOpenAIApi(String prompt, String apiKey) {
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4");
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", "You are a quiz evaluator. You must respond in English."));
        messages.add(Map.of("role", "user", "content", prompt));
        requestBody.put("messages", messages);

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 发送请求
        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, requestEntity, Map.class);
        Map<String, Object> responseBody = response.getBody();

        // 解析 GPT 返回的结果（注意：choices 是一个列表，需要正确解析）
        List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
        if (choices != null && !choices.isEmpty()) {
            Map<String, Object> firstChoice = choices.get(0);
            Map<String, String> message = (Map<String, String>) firstChoice.get("message");
            return message.get("content"); // 获取返回的消息内容
        } else {
            throw new RuntimeException("Invalid response from OpenAI API");
        }
    }


    /**
     * 生成最终反馈和总的正确率
     * 通过 GPT 上下文历史来生成最终评分和反馈
     */
    private String generateFinalFeedback(List<QuizResult> quizResults, String apiKey) {
        StringBuilder prompt = new StringBuilder();

        // 构建 GPT 提示语，告知其要根据学生的答案评估整体表现
        prompt.append("You are an assistant named Say. Below are all the questions, the student's answers, and the correct answers. Please give a score between 0-100 based on the information provided, and offer feedback.\n" +
                "I expect the feedback and evaluation to be encouraging and tactful. You must respond in English.");


        // 将每个问题、学生答案和标准答案加入到 GPT 的上下文中
        for (QuizResult result : quizResults) {
            System.out.println(result.getQuestion());
            System.out.println(result.getStudentAnswer());
            System.out.println(result.getCorrectAnswer());
            prompt.append("Question: ").append(result.getQuestion()).append("\n");
            prompt.append("Student's Answer: ").append(result.getStudentAnswer()).append("\n");
            prompt.append("Correct Answer: ").append(result.getCorrectAnswer()).append("\n\n");
        }

        // 调用 OpenAI API 进行整体评估
        String gptResponse = callOpenAIApi(prompt.toString(), apiKey);


        // 解析 GPT 的返回内容
        return gptResponse;
    }

    private GPTResponse parseGptResponse(String gptResponse) {
        // 使用正则表达式提取 "Score: X/10" 格式的分数
        Pattern pattern = Pattern.compile("Score: (\\d+(\\.\\d+)?)/10");
        Matcher matcher = pattern.matcher(gptResponse);

        double score = 0.0;
        String explanation = "";
        if (matcher.find()) {
            score = Double.parseDouble(matcher.group(1));  // 提取并解析分数
        }

        // 提取解释内容，假设解释在 "Explanation: " 之后
        int explanationIndex = gptResponse.indexOf("Explanation: ");
        if (explanationIndex != -1) {
            explanation = gptResponse.substring(explanationIndex + "Explanation: ".length()).trim();
        }

        GPTResponse response = new GPTResponse();
        response.setScore(score);
        response.setExplanation(explanation);

        return response;
    }



}
