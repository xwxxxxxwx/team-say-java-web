package com.xwx.xwx.service;

import com.xwx.xwx.pojo.ChatHistory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class OpenAiService {

    public String sendChatToOpenAi(List<Map<String, String>> chatHistory, String apiKey, String roleDescription) {
        List<Map<String, Object>> messages = new ArrayList<>();

        // 添加系统消息（角色描述）到消息列表中，作为第一条消息
        messages.add(Map.of("role", "system", "content", roleDescription));

        // 添加历史记录到消息中
        for (Map<String, String> history : chatHistory) {
            messages.add(Map.of("role", history.get("role"), "content", history.get("content")));
        }

        // 构建请求体
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4",
                "messages", messages
        );

        // 调用 OpenAI API
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity("https://api.openai.com/v1/chat/completions", request, Map.class);

        // 解析并返回回复
        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null) {
                // 解析choices
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> firstChoice = choices.get(0);
                    Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
                    if (message != null) {
                        return (String) message.get("content");  // 返回AI的回复内容
                    }
                }
            }
        }

        return "Error: Unable to fetch response from OpenAI.";
    }
}

