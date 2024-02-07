package com.logan.ndp.service.impl.config;

import com.logan.ndp.common.task.pipeline.ProcessController;
import com.logan.ndp.common.task.pipeline.ProcessTemplate;
import com.logan.ndp.service.api.enums.BusinessCode;
import com.logan.ndp.service.impl.action.send.SendAfterCheckAction;
import com.logan.ndp.service.impl.action.send.SendAssembleAction;
import com.logan.ndp.service.impl.action.send.SendMqAction;
import com.logan.ndp.service.impl.action.send.SendPreCheckAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * API层的pipeline配置类
 */
@Configuration
public class PipelineConfig {
    @Autowired
    private SendPreCheckAction sendPreCheckAction;
    @Autowired
    private SendAssembleAction sendAssembleAction;
    @Autowired
    private SendAfterCheckAction sendAfterCheckAction;
    @Autowired
    private SendMqAction sendMqAction;

//    @Autowired
//    private RecallAssembleAction recallAssembleAction;
//    @Autowired
//    private RecallMqAction recallMqAction;


    /**
     * 普通发送执行流程
     * 1. 前置参数校验
     * 2. 组装参数
     * 3. 后置参数校验
     * 4. 发送消息至MQ
     *
     * @return
     */
    @Bean("commonSendTemplate")
    public ProcessTemplate commonSendTemplate() {
        ProcessTemplate processTemplate = new ProcessTemplate();
        processTemplate.setProcessList(Arrays.asList(sendPreCheckAction, sendAssembleAction,
                sendAfterCheckAction, sendMqAction));
        return processTemplate;
    }

    /**
     * 消息撤回执行流程
     * 1.组装参数
     * 2.发送MQ
     *
     * @return
     */
//    @Bean("recallMessageTemplate")
//    public ProcessTemplate recallMessageTemplate() {
//        ProcessTemplate processTemplate = new ProcessTemplate();
//        processTemplate.setProcessList(Arrays.asList(recallAssembleAction, recallMqAction));
//        return processTemplate;
//    }

    /**
     * pipeline流程控制器
     * 后续扩展则加BusinessCode和ProcessTemplate
     *
     * @return
     */
    @Bean("apiProcessController")
    public ProcessController apiProcessController() {
        ProcessController processController = new ProcessController();
        Map<String, ProcessTemplate> templateConfig = new HashMap<>(4);
        templateConfig.put(BusinessCode.COMMON_SEND.getCode(), commonSendTemplate());
        //templateConfig.put(BusinessCode.RECALL.getCode(), recallMessageTemplate());
        processController.setTemplateConfig(templateConfig);
        return processController;
    }

}
