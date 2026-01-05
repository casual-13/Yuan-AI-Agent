package com.yuan.yuanaiagent;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.utils.JsonUtils;
import dev.langchain4j.community.model.dashscope.QwenChatModel;

import java.util.Arrays;

public class AICalling {
    public static void main(String[] args) {
//        LangChainAiInvoke();
        sdkAiInvoke();
    }

    private static void sdkAiInvoke() {
        try {
            Generation gen = new Generation();
            GenerationParam param = GenerationParam.builder()
                    .apiKey(System.getenv("API-KEY"))
                    .model("qwen-plus")
                    .messages(Arrays.asList(
                            Message.builder()
                                    .role(Role.SYSTEM.getValue())
                                    .content("You are a helpful assistant.")
                                    .build(),
                            Message.builder()
                                    .role(Role.USER.getValue())
                                    .content("你是谁？")
                                    .build()
                    ))
                    .build();
            GenerationResult result = gen.call(param);
            System.out.println("SDK调用：" + JsonUtils.toJson(result));
        } catch (Exception e) {
            System.err.println("调用服务错误: " + e.getMessage());
        }
    }


    private static void LangChainAiInvoke() {
        QwenChatModel model = QwenChatModel.builder()
                .apiKey(System.getenv("API-KEY"))
                .modelName("qwen-plus")
                .build();
        System.out.println("LangChainAi调用：" + model.chat("我是程序员yuan"));
    }
}
