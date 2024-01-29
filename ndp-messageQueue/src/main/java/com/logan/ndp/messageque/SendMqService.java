package com.logan.ndp.messageque;

/**
 * 发送消息去消息队列
 */
public interface SendMqService {

    /**
     * 发送消息
     * @param topic ${ndp.business.tagId.key}
     * @param jsonValue
     * @param tagId
     */
    void send(String topic, String jsonValue, String tagId);

    /**
     * 发送消息
     *
     * @param topic ${ndp.business.tagId.key}
     * @param jsonValue
     */
    void send(String topic, String jsonValue);

}
