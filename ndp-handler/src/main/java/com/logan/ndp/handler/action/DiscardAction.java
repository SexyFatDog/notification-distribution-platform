package com.logan.ndp.handler.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.logan.ndp.common.constant.NDPConstant;
import com.logan.ndp.common.task.domain.TaskInfo;
import com.logan.ndp.common.task.pipeline.BusinessProcess;
import com.logan.ndp.common.task.pipeline.ProcessContext;
import com.ndp.config.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 丢弃消息
 * 一般将需要丢弃的模板id写在分布式配置中心
 */
@Service
public class DiscardAction implements BusinessProcess<TaskInfo> {
    private static final String DISCARD_MESSAGE_KEY = "discardMsgIds";

    @Autowired
    private ConfigService config;
//    @Autowired
//    private LogUtils logUtils;

    @Override
    public void process(ProcessContext<TaskInfo> context) {
        TaskInfo taskInfo = context.getProcessModel();
        // 配置示例:	["1","2"]
        JSONArray array = JSON.parseArray(config.getProperty(DISCARD_MESSAGE_KEY, NDPConstant.EMPTY_VALUE_JSON_ARRAY));
        if (array.contains(String.valueOf(taskInfo.getMessageTemplateId()))) {
            //logUtils.print(AnchorInfo.builder().bizId(taskInfo.getBizId()).messageId(taskInfo.getMessageId()).businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver()).state(AnchorState.DISCARD.getCode()).build());
            context.setNeedBreak(true);
        }

    }
}
