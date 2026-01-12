package com.yuan.yuanaiagent.chatmemory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.extern.slf4j.Slf4j;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 基于文件持久化的对话记忆
 */
@Slf4j
public class FileBasedChatMemory implements ChatMemory {

    private final String BASE_DIR;

    private static final Kryo kryo = new Kryo();

    static {
        /**
         * 关闭“类必须显式注册”的限制。
         * 🔸 背景：
         * Kryo 默认要求：所有要序列化的类必须通过 kryo.register(SomeClass.class) 显式注册。
         * 这是为了提升性能（避免运行时反射查类名）和安全性（防止反序列化任意类）。
         * 🔸 设置为 false 后：
         * Kryo 可以自动处理未注册的类（通过类名写入字节流）。
         * 使用更方便（无需提前知道所有类型），但：
         * 性能略低（需写入完整类名，如 com.example.User）；
         * 体积略大（类名占空间）；
         * 安全性降低（可能反序列化恶意类，需确保数据可信）。
         */
        kryo.setRegistrationRequired(false);
        /**
         * 🔹 作用：
         * 指定对象反序列化时的实例化策略 —— 使用不调用构造函数的方式创建对象。
         * 🔸 背景：
         * Java 对象通常通过 new 调用构造函数创建。
         * 但某些类没有无参构造函数，或构造函数有副作用（如初始化网络连接），导致 Kryo 无法反序列化。
         * 🔸 StdInstantiatorStrategy 是什么？
         * 来自 objenesis 库（Kryo 依赖它）；
         * 使用底层 JVM 技术（如 sun.misc.Unsafe 或 ObjectInputStream）绕过构造函数直接分配内存并创建对象；
         * 类似于 Java 原生序列化的行为。
         */
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
    }

    // 构造对象时，指定文件保存目录
    public FileBasedChatMemory(String dir) {
        this.BASE_DIR = dir;
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        List<Message> conversationMessages = getOrCreateConversation(conversationId);
        conversationMessages.addAll(messages);
        saveConversation(conversationId, conversationMessages);
    }

    @Override
    public List<Message> get(String conversationId) {
        return getOrCreateConversation(conversationId);
    }

    @Override
    public void clear(String conversationId) {
        File file = getConversationFile(conversationId);
        if (file.exists()) {
            file.delete();
        }
    }

    private List<Message> getOrCreateConversation(String conversationId) {
        File file = getConversationFile(conversationId);
        List<Message> messages = new ArrayList<>();
        if (file.exists()) {
            try (Input input = new Input(new FileInputStream(file))) {
                messages = kryo.readObject(input, ArrayList.class);
            } catch (IOException e) {
                log.error("读取对话记录失败: {}", conversationId, e);
                return new ArrayList<>();
            }
        }
        return messages;
    }

    private void saveConversation(String conversationId, List<Message> messages) {
        File file = getConversationFile(conversationId);
        try (Output output = new Output(new FileOutputStream(file))) {
            kryo.writeObject(output, messages);
        } catch (IOException e) {
            log.error("保存对话记录失败: {}", conversationId, e);
        }
    }

    private File getConversationFile(String conversationId) {
        return new File(BASE_DIR, conversationId + ".kryo");
    }
}
