package com.yuan.yuanaiagent.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientMessageAggregator;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import reactor.core.publisher.Flux;

/**
 * 自定义日志 Advisor，打印用户输入和 AI 输出
 */
@Slf4j
public class MyLoggerAdvisor implements CallAdvisor, StreamAdvisor {
    /**
     * 同步调用处理
     * @param chatClientRequest
     * @param callAdvisorChain
     * @return
     */
    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        // 1. 记录请求日志
        chatClientRequest = before(chatClientRequest);
        // 2. 执行实际调用
        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);
        // 3. 记录响应日志
        observeAfter(chatClientResponse);
        // 4. 返回响应
        return chatClientResponse;
    }

    /**
     * 流式调用处理
     * @param chatClientRequest
     * @param streamAdvisorChain
     * @return
     */
    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest, StreamAdvisorChain streamAdvisorChain) {
        // 1. 记录请求日志
        chatClientRequest = before(chatClientRequest);
        // 2. 获取流式响应
        Flux<ChatClientResponse> chatClientResponseFlux = streamAdvisorChain.nextStream(chatClientRequest);
        // 3. 聚合流式响应并记录
        return new ChatClientMessageAggregator().aggregateChatClientResponse(chatClientResponseFlux, this::observeAfter);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 顺序，数字越小越先执行
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 请求前打印用户输入
     * @param request
     * @return
     */
    private ChatClientRequest before(ChatClientRequest request) {
        log.info("AI Request: {}", request.prompt());
        return request;
    }

    /**
     * 响应后打印 AI 输出
     * @param response
     */
    private void observeAfter(ChatClientResponse response) {
        log.info("AI Response: {}", response.chatResponse().getResult().getOutput().getText());
    }
}
