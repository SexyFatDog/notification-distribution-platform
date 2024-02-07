package com.logan.ndp.service.api.domain;



import com.logan.ndp.common.task.domain.MessageParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.util.List;

/**
 * 发送/撤回操作的 参数
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendRequest {

    /**
     * 执行业务类型
     *
     * @see com.logan.ndp.service.api.enums.BusinessCode
     * send:发送消息
     * recall:撤回消息
     */
    private String code;

    /**
     * 消息模板Id -> 确定消息的模板
     * 【必填】
     */
    private Long messageTemplateId;


    /**
     * 消息相关的参数
     * 当业务类型为"send"，必传
     */
    private MessageParam messageParam;

    /**
     * 需要撤回的消息messageIds (可根据发送接口返回的消息messageId进行撤回)
     * 【可选】
     */
    private List<String> recallMessageIds;

}
