package com.yuan.yuanaiagent.chatmemory;

import cn.hutool.json.JSONConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * MySQL实现的对话记忆
 * 将对话内容持久化到MySQL数据库
 */
//todo 按需打开
//@Component
@Slf4j
public class MySQLChatMemory implements ChatMemory {

    private final JdbcTemplate jdbcTemplate;
    private final JSONConfig jsonConfig;

    public MySQLChatMemory(@Qualifier("mysqlJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jsonConfig = new JSONConfig().setIgnoreNullValue(true);
        log.info("初始化MySQL对话记忆，使用限定的MySQL数据源");
    }

    @Override
    public void add(String conversationId, Message message) {
        ChatMemory.super.add(conversationId, message);
    }

    @Override
    public void add(String conversationId, List<Message> messages) {

    }

    @Override
    public List<Message> get(String conversationId) {
        return null;
    }

    @Override
    public void clear(String conversationId) {

    }
}
