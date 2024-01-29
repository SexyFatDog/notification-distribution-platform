package com.logan.ndp.service.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 操作类型
 * 1.COMMON_SEND 普通发送
 * 2.RECALL 撤回消息
 */
@Getter
@ToString
@AllArgsConstructor
public enum BusinessCode {

    /**
     * 普通发送流程
     */
    COMMON_SEND("send", "普通发送"),

    /**
     * 撤回流程
     */
    RECALL("recall", "撤回消息");


    /**
     * code 关联着责任链的模板
     */
    private final String code;

    /**
     * 类型说明
     */
    private final String description;


}
