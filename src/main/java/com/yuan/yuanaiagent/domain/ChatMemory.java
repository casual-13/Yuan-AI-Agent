package com.yuan.yuanaiagent.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 聊天记忆实体类
 *
 * @TableName chatmemory
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "chat_memory")
public class ChatMemory implements Serializable {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 会话ID
     */
    @TableField(value = "conversation_id")
    private String conversationId;

    /**
     * 消息顺序
     */
    @TableField(value = "message_order")
    private Integer messageOrder;

    /**
     * 消息类型
     */
    @TableField(value = "message_type")
    private String messageType;

    /**
     * 消息内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 消息JSON
     */
    @TableField(value = "message_json")
    private String messageJson;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    @TableLogic
    private Boolean isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}