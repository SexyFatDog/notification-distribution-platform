package com.logan.ndp.service.impl.action.send;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Throwables;
import com.logan.ndp.common.enums.RespStatusEnum;
import com.logan.ndp.common.vo.BasicResultVO;
import com.logan.ndp.messageque.SendMqService;
import com.logan.ndp.common.task.domain.SimpleTaskInfo;
import com.logan.ndp.common.task.domain.TaskInfo;
import com.logan.ndp.common.task.pipeline.BusinessProcess;
import com.logan.ndp.common.task.pipeline.ProcessContext;
import com.logan.ndp.service.impl.domain.SendTaskModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 1. 将消息发送到MQ
 * 2. 返回拼装好的messageId给到接口调用方
 */
@Slf4j
@Service
public class SendMqAction implements BusinessProcess<SendTaskModel> {


    @Autowired
    private SendMqService sendMqService;

    @Value("${ndp.business.topic.name}")
    private String sendMessageTopic;

    @Value("${ndp.business.tagId.value}")
    private String tagId;

    @Value("${ndp.mq.pipeline}")
    private String mqPipeline;

    @Override
    public void process(ProcessContext<SendTaskModel> context) {
        SendTaskModel sendTaskModel = context.getProcessModel();
        List<TaskInfo> taskInfo = sendTaskModel.getTaskInfo();
        try {
            String message = JSON.toJSONString(sendTaskModel.getTaskInfo(), new SerializerFeature[]{SerializerFeature.WriteClassName});
            sendMqService.send(sendMessageTopic, message, tagId);

            context.setResponse(BasicResultVO.success(taskInfo.stream().map(v -> SimpleTaskInfo.builder().businessId(v.getBusinessId()).messageId(v.getMessageId()).bizId(v.getBizId()).build()).collect(Collectors.toList())));
        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR));
            log.error("send {} fail! e:{},params:{}", mqPipeline, Throwables.getStackTraceAsString(e)
                    , JSON.toJSONString(CollUtil.getFirst(taskInfo.listIterator())));
        }
    }

}
