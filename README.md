# Yuan AI Agent-AI超级智能体

## 第一章：AI模型接入

**本章学习**

1. 学习AI大模型的核心概念与分类。
2. 掌握云服务与自部署的AI接入方式。
3. 完成Java后端项目初始化与依赖整合。

### **AI大模型的概念**

AI大模型是超大规模参数的[深度学习](https://www.mianshiya.com/bank/1821834656568348674)模型，经大量数据训练，能处理多模态数据，有逻辑推理和代码编写等涌现能力。

**常见大模型举例**

- **OpenAI**：GPT - 4o（多模态）、GPT - 4（文本 + 图像）、GPT - 3.5 Turbo。
- **Anthropic**：Claude 3系列。
- **Google**：Gemini Ultra/Pro/Nano。
- **Meta**：Llama 3、Llama 2。
- **国内**：百度文心一言、阿里通义千问等。

**大模型分类**

按模态、开源性、规模、用途划分。如按模态分单模态和多模态；按开源性分闭源和开源；按规模分超大规模和中小规模；按用途分通用和特定领域。

### **接入AI大模型**

**使用途径**

有[云服务](https://www.mianshiya.com/bank/1812069165910065153)和自部署两种。云服务无需考虑基础设施，按需付费；自部署数据隐私高，但成本高。个人适合云服务，企业适合自部署。

**接入方式**

1. **AI应用平台接入**：如阿里云百炼，可创建AI应用并集成到项目。
2. **AI软件客户端接入**：如Cherry Studio和Cursor。
3. **程序接入**：可直接调用大模型或调用平台创建的应用，个人小项目用后者，企业级项目考虑扩展性选前者

### **后端项目初始化**

**环境准备**

安装JDK 17或21，推荐21版本。

OpenJDK 官方安装：<https://jdk.java.net/java-se-ri/21>

**新建项目**

在IDEA用[Spring](https://www.mianshiya.com/bank/1790683494127804418) Initializr模板新建项目，选[Java](https://www.mianshiya.com/bank/1860871861809897474) 21、Spring Boot 3.4.4，可添加依赖。若Lombok依赖报错，手动指定版本。

**整合依赖**

可整合Hutool工具库和Knife4j接口文档



**pom 文件**

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.4.4</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.yuan</groupId>
    <artifactId>yuan-ai-agent</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>yuan-ai-agent</name>
    <description>yuan-ai-agent</description>
    <url/>
    <licenses>
        <license/>
    </licenses>
    <developers>
        <developer/>
    </developers>
    <scm>
        <connection/>
        <developerConnection/>
        <tag/>
        <url/>
    </scm>
    <properties>
        <java.version>21</java.version>
    </properties>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.alibaba.cloud.ai</groupId>
                <artifactId>spring-ai-alibaba-bom</artifactId>
                <version>1.0.0.2</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.ai</groupId>
                <artifactId>spring-ai-bom</artifactId>
                <version>1.0.0</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.36</version>
            <optional>true</optional>
        </dependency>

        <!-- Knife4j -->
        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>knife4j-openapi3-jakarta-spring-boot-starter</artifactId>
            <version>4.4.0</version>
        </dependency>

        <!-- hutool -->
        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>5.8.37</version>
        </dependency>

        <!-- LangChain4J DashScope -->
        <dependency>
            <groupId>dev.langchain4j</groupId>
            <artifactId>langchain4j-community-dashscope</artifactId>
            <version>1.0.0-beta2</version>
        </dependency>

        <!-- Spring AI Alibaba -->
        <dependency>
            <groupId>com.alibaba.cloud.ai</groupId>
            <artifactId>spring-ai-alibaba-starter-dashscope</artifactId>
        </dependency>

        <!-- https://docs.spring.io/spring-ai/reference/api/chat/ollama-chat.html -->
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-starter-model-ollama</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-markdown-document-reader</artifactId>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

**application.yaml 配置文件**

```
spring:
  application:
    name: yuan-ai-agent
  profiles:
    active: local
  ai:
    dashscope:
      api-key: ${API-KEY}
      chat:
        options:
          model: qwen-plus
    ollama:
      base-url: http://localhost:11434
      chat:
        model: deepseek-r1:7b

server:
  port: 8123
  servlet:
    context-path: /api

# springdoc-openapi config
springdoc:
  swagger-ui:
    path: /swagger-ui.html
    tags-sorter: alpha
    operations-sorter: alpha
  api-docs:
    path: /v3/api-docs
  group-configs:
    - group: 'default'
      paths-to-match: '/**'
      packages-to-scan: com.yuan.yuanaiagent.controller

# knife4j config
knife4j:
  enable: true
  setting:
    language: zh_cn

logging:
  level:
    org.springframework.ai: DEBUG
```

**四种调用AI方式**

**1. SDK AI 调用**

```java
package com.yuan.yuanaiagent.demo.invoke;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;

import java.util.Arrays;

public class SdkAiInvoke {
    public static GenerationResult callWithMessage() throws NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content("You are a helpful assistant.")
                .build();
        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content("你好，我是程序员yuan，正在开最新的原创项目 - AI 超级智能体")
                .build();
        GenerationParam param = GenerationParam.builder()
                .apiKey(TestApiKey.API_KEY)
                .model("qwen-plus")
                .messages(Arrays.asList(systemMsg, userMsg))
                .build();
        return gen.call(param);
    }

    public static void main(String[] args) {
        try {
            GenerationResult result = callWithMessage();
            System.out.println(JsonUtils.toJson(result));
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            // 使用日志框架记录异常信息
            System.err.println("An error occurred while calling the generation service: " + e.getMessage());
        }
        System.exit(0);
    }
}
```

**2. Http AI 调用**

```java

```

**3. Spring AI 调用**

```java
package com.yuan.yuanaiagent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Spring AI 框架调用 AI 大模型（阿里）
 */
// 取消注释后，项目启动时会执行
@Component
public class SpringAiAiInvoke implements CommandLineRunner {

    @Resource
    private ChatModel dashscopeChatModel;

    @Override
    public void run(String... args) throws Exception {
        AssistantMessage assistantMessage = dashscopeChatModel.call(new Prompt("你好，我是yuan"))
                .getResult()
                .getOutput();
        System.out.println(assistantMessage.getText());
    }
}
```

**4. LangChain4j 调用**

```java
package com.yuan.yuanaiagent.demo.invoke;

import dev.langchain4j.community.model.dashscope.QwenChatModel;

public class LangChainAiInvoke {
    public static void main(String[] args) {
        QwenChatModel qwenChatModel = QwenChatModel.builder()
                .apiKey(TestApiKey.API_KEY)
                .modelName("qwen-max")
                .build();
        String answer = qwenChatModel.chat("我是程序员yuan，这是我自创的一个 AI 超级智能体项目");
        System.out.println(answer);
    }
}
```

