package com.yuan.yuanaiagent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuan.yuanaiagent.domain.ChatMemory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author 86159
* @description 针对表【chat_memory】的数据库操作Mapper
* @createDate 2026-01-11 08:09:51
* @Entity generator.domain.ChatMemory
*/
@Mapper
public interface ChatMemoryMapper extends BaseMapper<ChatMemory> {

    /**
     * 获取最大消息序号
     */
    @Select("SELECT MAX(message_order) FROM chat_memory WHERE conversation_id = #{conversationId} AND is_delete = 0")
    Integer getMaxOrder(@Param("conversationId") String conversationId);

    /**
     * 获取会话消息数量
     */
    @Select("SELECT COUNT(*) FROM chat_memory WHERE conversation_id = #{conversationId} AND is_delete = 0")
    int getMessageCount(@Param("conversationId") String conversationId);

    /**
     * 逻辑删除会话消息
     */
    @Update("UPDATE chat_memory SET is_delete = 1, update_time = NOW() WHERE conversation_id = #{conversationId} AND is_delete = 0")
    int logicalDeleteByConversationId(@Param("conversationId") String conversationId);

    /**
     * 获取最近消息，按消息顺序降序
     */
    @Select("SELECT * from chat_memory WHERE conversation_id = #{conversationId} AND is_delete = 0 ORDER BY message_order DESC LIMIT #{limit}")
    List<ChatMemory> getLatestMessages(@Param("conversationId") String conversationId, @Param("limit") int limit);

    /**
     * 分页获取消息
     */
    @Select("SELECT * FROM chat_memory WHERE conversation_id = #{conversationId} AND is_delete = 0 ORDER BY message_order DESC " +
            "LIMIT #{pageSize} OFFSET #{offset}")
    List<ChatMemory> getMessagesPaginated(@Param("conversationId") String conversationId,
                                          @Param("pageSize") int pageSize, @Param("offset") int offset);

    @Select("SELECT * FROM chat_memory WHERE conversation_id = #{conversationId} AND is_delete = 0 ORDER BY message_order DESC " +
            "LIMIT #{limit} OFFSET #{offset}")
    List<ChatMemory> getMessagesWithOffset(@Param("conversationId") String conversationId, @Param("limit") int limit,
                                           @Param("offset") int offset);
}




