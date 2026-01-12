package com.yuan.yuanaiagent.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 违禁词校验 Advisor
 * 检查用户输入是否包含违禁词
 */
@Slf4j
public class ProhibitedWordAdvisor implements CallAdvisor, StreamAdvisor {

    private static final String DEFAULT_PROHIBITED_WORDS_FILE = "prohibited-words.txt";
    private final List<String> prohibitedWords;

    /**
     * 创建默认违禁词Advisor，从默认文件读取违禁词列表
     */
    public ProhibitedWordAdvisor() {
        this.prohibitedWords = loadProhibitedWordsFromFile(DEFAULT_PROHIBITED_WORDS_FILE);
        log.info("初始化违禁词Advisor，违禁词数量: {}", prohibitedWords.size());
    }

    /**
     * 创建违禁词Advisor，从指定文件读取违禁词列表
     */
    public ProhibitedWordAdvisor(String prohibitedWordsFile) {
        this.prohibitedWords = loadProhibitedWordsFromFile(prohibitedWordsFile);
        log.info("初始化违禁词Advisor，违禁词数量: {}", prohibitedWords.size());
    }

    /**
     * 从文件加载违禁词列表
     */
    private List<String> loadProhibitedWordsFromFile(String filePath) {
        try {
            var resource = new ClassPathResource(filePath);
            var reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)
            );

            List<String> words = reader.lines()
                    .filter(StringUtils::hasText)
                    .map(String::trim)
                    .collect(Collectors.toList());

            log.info("从文件 {} 加载违禁词 {} 个", filePath, words.size());
            return words;
        } catch (IOException e) {
            log.error("加载违禁词文件 {} 失败", filePath, e);
            return new ArrayList<>();
        }
    }

    /**
     * 检查请求中是否包含违禁词
     */
    public ChatClientRequest checkRequest(ChatClientRequest request) {
        String userText = request.prompt().getUserMessage().getText();
        if (containsProhibitedWord(userText)) {
            log.warn("检测到违禁词在用户输入中: {}", userText);
            throw new ProhibitedWordException("用户输入包含违禁词");
        }
        return request;
    }

    /**
     * 检查文本中是否包含违禁词
     */
    public boolean containsProhibitedWord(String text) {
        if (!StringUtils.hasText(text)) {
            return false;
        }

        for (String word : prohibitedWords) {
            if (text.toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        ChatClientRequest request = checkRequest(chatClientRequest);
        return callAdvisorChain.nextCall(request);
    }

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest, StreamAdvisorChain streamAdvisorChain) {
        ChatClientRequest request = checkRequest(chatClientRequest);
        return streamAdvisorChain.nextStream(request);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 确保在其他Advisor之前执行
     *
     * @return
     */
    @Override
    public int getOrder() {
        return -100;
    }

    /**
     * 违禁词异常
     */
    public static class ProhibitedWordException extends RuntimeException {
        public ProhibitedWordException(String message) {
            super(message);
        }
    }
}
