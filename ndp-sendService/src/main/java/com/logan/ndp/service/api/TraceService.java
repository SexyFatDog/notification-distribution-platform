package com.logan.ndp.service.api;

import com.logan.ndp.service.api.domain.TraceResponse;

public interface TraceService {
    /**
     * 基于消息 ID 查询 链路结果
     *
     * @param messageId
     * @return
     */
    TraceResponse traceByMessageId(String messageId);
}
