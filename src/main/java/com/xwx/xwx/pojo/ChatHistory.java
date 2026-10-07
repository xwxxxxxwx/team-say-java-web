package com.xwx.xwx.pojo;

public class ChatHistory {
    private String role;  // "user", "assistant", "system"
    private String content;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ChatHistory(String role, String content) {
        this.role = role;
        this.content = content;
    }

    // Getters and Setters
}
