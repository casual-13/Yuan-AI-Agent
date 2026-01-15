package com.yuan.yuanaiagent.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class PgVectorVectorStoreConfigTest {

    @Resource(name = "pgVectorVectorStore")
    private VectorStore pgVectorStore;

    @Test
    void pgVectorVectorStore() {
        List<Document> documents = List.of(
                new Document("阿源 是一位热爱技术的开发者，专注于人工智能、Java 后端开发和系统架构设计。他的博客涵盖了 Spring Boot、AI 应用开发、数据库优化以及 DevOps 等热门主题。",
                        Map.of("author", "阿源")),
                new Document("阿源 维护的一个技术分享平台，内容涵盖 Java、Spring AI、PostgreSQL、向量数据库应用等前沿技术，适合对智能系统感兴趣的开发者学习参考。",
                        Map.of("source", "aYuan.github.io")),
                new Document("在 阿源 博客中，你可以找到关于如何使用 Spring AI 构建智能代理（Agent）、集成向量数据库如 pgVector，并实现本地化的 RAG 检索增强生成系统等内容。",
                        Map.of("topic", "RAG", "tags", "Spring AI, PostgreSQL")));
        // 添加文档
//        pgVectorStore.add(documents);
        // 相似度查询
        List<Document> result = pgVectorStore.similaritySearch(SearchRequest.builder().query("阿源").topK(3).build());
        System.out.println(result);
        Assertions.assertNotNull(result);
    }
}
