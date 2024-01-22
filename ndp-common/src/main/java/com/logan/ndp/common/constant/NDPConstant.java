package com.logan.ndp.common.constant;

/**
 *  定义项目的常量信息
 */
public class NDPConstant {
    /**
     * businessId默认的长度
     * 生成的逻辑：com.logan.ndp.support.utils.TaskInfoUtils#generateBusinessId(java.lang.Long, java.lang.Integer)
     */
    public final static Integer BUSINESS_ID_LENGTH = 16;

    /**
     * 接口限制 最多的人数
     */
    public static final Integer BATCH_RECEIVER_SIZE = 100000;

    /**
     * 消息发送给全部人的标识
     * (企业微信 应用消息)
     * (钉钉自定义机器人)
     * (钉钉工作消息)
     */
    public static final String SEND_ALL = "@all";

    /**
     * 链路追踪缓存的key标识
     */
    public static final String CACHE_KEY_PREFIX = "Austin";
    public static final String MESSAGE_ID = "MessageId";

    /**
     * 默认的常量，如果新建模板/账号时，没传入则用该常量
     */
    public static final String DEFAULT_CREATOR = "Logan";
    public static final String DEFAULT_UPDATOR = "Logan";
    public static final String DEFAULT_TEAM = "Logan_SCNU";
    public static final String DEFAULT_AUDITOR = "Logan";

}
