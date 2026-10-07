# S.A.Y - Smart AI for You

> 团队项目 | COMPSCI 399 Capstone Project | Team 8

## 项目简介

S.A.Y (Smart AI for You) 是一个 AI 驱动的在线学习平台，基于 GPT-4 实现智能问答、自动出题和学习分析功能。支持教师创建课程和测验，学生选课学习并通过 AI 获得个性化的学习辅导。

本仓库为个人备份版本，原始项目为奥克兰大学 COMPSCI 399 课程团队开发作品，此处仅用于个人学习和存档。

## 技术栈

- 后端：Spring Boot + MyBatis + MySQL
- 前端：Thymeleaf 模板 + Vue.js
- AI 能力：OpenAI GPT-4 API
- 构建工具：Maven

## 主要功能

### 用户系统
- 用户注册 / 登录
- 教师 / 学生角色区分

### 课程管理
- 教师创建、编辑、删除课程
- 学生浏览、搜索、选课
- 课程详情页和学习进度跟踪

### AI 智能问答
- 基于 GPT-4 的课程内容问答
- 可配置 AI 角色描述（针对不同课程）
- 聊天历史记录

### Quiz 测验系统
- AI 自动生成测验题目
- 智能批改和反馈
- Quiz 结果分析

### 数据分析
- 点击量统计分析
- 学习报告生成
- 测验结果分析可视化

### 反馈系统
- 学生提交反馈
- 教师查看和处理反馈

## 团队信息

- 课程：COMPSCI 399 - Capstone Project（奥克兰大学）
- 团队：Team 8
- 开发时间：2024年8月
- 个人角色：核心开发者
- 负责模块：AI 问答模块、Quiz 测验模块、数据分析模块

## 代码结构

### 后端 (Spring Boot)

src/main/java/com/xwx/xwx/

controller/ 控制层（15个）
- ChatController - AI 聊天接口
- CourseController - 课程管理接口
- QuizController - 测验接口
- QuizChatController - Quiz AI 对话接口
- UserController - 用户接口
- SearchController - 搜索接口
- FeedbackController - 反馈接口
- ReportController - 报告接口
- ClickCountAnalysisController - 点击统计分析接口
- QuizAnalysisController - 测验分析接口
- InCourseController - 课程内学习接口
- ConfigCourseController - 课程配置接口
- StudentCourseController - 学生选课接口
- MenuController - 菜单接口
- PageController - 页面路由

service/ 业务逻辑层（21个）
- OpenAiService - GPT-4 API 调用封装
- QuizChatService - Quiz 对话业务逻辑（257行，核心模块）
- CourseService - 课程业务
- QuizService - 测验业务
- UserService - 用户业务
- AnswerService - 答案服务
- FeedbackService - 反馈服务
- ReportService - 报告服务
- SearchService - 搜索服务
- MenuService - 菜单服务
- InCourseService - 课程内服务
- ClickCountAnalysisService - 点击统计分析
- QuizAnalysisService - 测验分析
- ConfigCourseService - 课程配置服务
- StudentCourseService - 学生选课服务

mapper/ 数据访问层（13个）
- CourseMapper, UserMapper, QuizMapper, AnswerMapper 等 MyBatis 映射器

pojo/ 实体类（13个）
- User, Course, Quiz, ChatHistory, Feedback 等数据模型

### 前端

frontend/
- src/ - Vue.js 源码
- public/ - 静态资源
- package.json - 依赖配置

### 模板页面

src/main/resources/templates/
- login.html / register.html - 登录注册
- index.html - 首页
- course-detail.html - 课程详情
- quiz.html - 测验页面
- data-analysis.html - 数据分析
- dashboardS.html / dashboardT.html - 学生/教师仪表板
- search.html - 搜索页
- feedback.html - 反馈页
- menu.html - 菜单页
- createModel.html - 创建模型

