package com.logan.ndp.service.api.domain;

import com.logan.ndp.common.task.domain.MessageParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 批量发送操作 参数
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatchSendRequest {


    /**
     * 执行业务类型
     * 必传,参考 BusinessCode枚举
     */
    private String code;


    /**
     * 消息模板Id
     * 必传
     */
    private Long messageTemplateId;


    /**
     * 消息相关的参数
     * 必传
     */
    private List<MessageParam> messageParamList;


}
