package com.logan.ndp.service.api;


import com.logan.ndp.service.api.domain.SendRequest;
import com.logan.ndp.service.api.domain.SendResponse;

/**
 * 撤回操作 接口
 * 不是每一个服务类型都支持
 * 优先级低
 */
public interface RecallService {
    /**
     * 根据 模板ID 或消息id 撤回消息
     * 如果只传入 messageTemplateId，则会撤回整个模板下发的消息
     * 如果还传入 recallMessageId，则优先撤回该 ids 的消息
     *
     * @param sendRequest
     * @return
     */
    SendResponse recall(SendRequest sendRequest);
}
