package com.logan.ndp.service.impl;

import com.logan.ndp.common.enums.RespStatusEnum;
import com.logan.ndp.common.vo.BasicResultVO;
import com.logan.ndp.service.api.SendService;
import com.logan.ndp.service.api.domain.BatchSendRequest;
import com.logan.ndp.service.api.domain.SendRequest;
import com.logan.ndp.service.api.domain.SendResponse;
import com.logan.ndp.common.task.domain.SimpleTaskInfo;
import com.logan.ndp.common.task.pipeline.ProcessContext;
import com.logan.ndp.common.task.pipeline.ProcessController;
import com.logan.ndp.service.impl.domain.SendTaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import java.util.Collections;
import java.util.List;

@Service
public class SendServiceImpl implements SendService {
    @Qualifier("apiProcessController")
    @Autowired
    private ProcessController processController;

    @Override
    public SendResponse send(SendRequest sendRequest) {
        if (ObjectUtils.isEmpty(sendRequest)) {
            return new SendResponse(RespStatusEnum.CLIENT_BAD_PARAMETERS.getCode(), RespStatusEnum.CLIENT_BAD_PARAMETERS.getMsg(), null);
        }

        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(sendRequest.getMessageTemplateId())
                .messageParamList(Collections.singletonList(sendRequest.getMessageParam()))
                .build();

        ProcessContext context = ProcessContext.builder()
                .code(sendRequest.getCode())
                .processModel(sendTaskModel)
                .needBreak(false)
                .response(BasicResultVO.success()).build();

        ProcessContext process = processController.process(context);

        return new SendResponse(process.getResponse().getStatus(), process.getResponse().getMsg(), (List<SimpleTaskInfo>) process.getResponse().getData());
    }

    @Override
    public SendResponse batchSend(BatchSendRequest batchSendRequest) {
        if (ObjectUtils.isEmpty(batchSendRequest)) {
            return new SendResponse(RespStatusEnum.CLIENT_BAD_PARAMETERS.getCode(), RespStatusEnum.CLIENT_BAD_PARAMETERS.getMsg(), null);
        }

        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(batchSendRequest.getMessageTemplateId())
                .messageParamList(batchSendRequest.getMessageParamList())
                .build();

        ProcessContext context = ProcessContext.builder()
                .code(batchSendRequest.getCode())
                .processModel(sendTaskModel)
                .needBreak(false)
                .response(BasicResultVO.success()).build();

        ProcessContext process = processController.process(context);

        return new SendResponse(process.getResponse().getStatus(), process.getResponse().getMsg(), (List<SimpleTaskInfo>) process.getResponse().getData());
    }


}
