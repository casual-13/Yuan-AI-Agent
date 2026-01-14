package com.yuan.yuanaiagent.rag;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetriever;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetrieverOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoveAppRagCloudAdvisorConfig {

    // 从配置文件读取 DashScope API 密钥
    @Value("${spring.ai.dashscope.api-key}")
    private String dashScopeApiKey;

    // 定义 Bean 方法，创建检索增强顾问
    @Bean
    public Advisor loveAppRagCloudAdvisor() {
        // 创建 DashScope API 客户端
        DashScopeApi dashScopeApi = DashScopeApi.builder()
                .apiKey(dashScopeApiKey)
                .build();
        // 知识库名称
        final String KNOWLEDGE_INDEX = "智能分配交友";
        // 创建文档检索器，连接指定知识库
        DashScopeDocumentRetriever dashScopeDocumentRetriever = new DashScopeDocumentRetriever(dashScopeApi,
                DashScopeDocumentRetrieverOptions.builder()
                        .withIndexName(KNOWLEDGE_INDEX)
                        .build());
        // 构建并返回检索增强顾问
        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(dashScopeDocumentRetriever)
                .build();
    }
}
