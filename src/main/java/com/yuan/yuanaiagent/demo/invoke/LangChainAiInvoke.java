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
