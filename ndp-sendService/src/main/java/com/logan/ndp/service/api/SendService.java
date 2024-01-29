package com.logan.ndp.service.api;

import com.logan.ndp.service.api.domain.BatchSendRequest;
import com.logan.ndp.service.api.domain.SendRequest;
import com.logan.ndp.service.api.domain.SendResponse;


/**
 * 发送消息 接口
 */
public interface SendService {
    /**
     * 单文案发送接口
     *
     * @param sendRequest eg:    {"code":"send","messageParam":{"bizId":null,"extra":null,"receiver":"123@qq.com","variables":null},"messageTemplateId":17,"recallMessageId":null}
     * @return SendResponse eg:    {"code":"0","data":[{"bizId":"ecZim2-FzdejNSY-sqgCM","businessId":2000001720230815,"messageId":"ecZim2-FzdejNSY-sqgCM"}],"msg":"操作成功"}
     */
    SendResponse send(SendRequest sendRequest);


    /**
     * 多文案发送接口
     *
     * @param batchSendRequest
     * @return
     */
    SendResponse batchSend(BatchSendRequest batchSendRequest);
}
