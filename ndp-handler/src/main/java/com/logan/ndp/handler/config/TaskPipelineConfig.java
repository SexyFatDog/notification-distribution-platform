package com.logan.ndp.handler.config;



import com.logan.ndp.common.task.pipeline.ProcessController;
import com.logan.ndp.common.task.pipeline.ProcessTemplate;
import com.logan.ndp.handler.action.DeduplicationAction;
import com.logan.ndp.handler.action.DiscardAction;
import com.logan.ndp.handler.action.SendMessageAction;
import com.logan.ndp.handler.action.ShieldAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * handler层的pipeline配置类
 */
@Configuration
public class TaskPipelineConfig {
    public static final String PIPELINE_HANDLER_CODE = "handler";
    @Autowired
    private DiscardAction discardAction;
    @Autowired
    private ShieldAction shieldAction;
    @Autowired
    private DeduplicationAction deduplicationAction;
    @Autowired
    private SendMessageAction sendMessageAction;


    /**
     * 消息从MQ消费的流程
     * 0.丢弃消息
     * 1.屏蔽消息
     * 2.通用去重功能
     * 3.发送消息
     *
     * @return
     */
    @Bean("taskTemplate")
    public ProcessTemplate taskTemplate() {
        ProcessTemplate processTemplate = new ProcessTemplate();
        processTemplate.setProcessList(Arrays.asList(discardAction, shieldAction, deduplicationAction, sendMessageAction));
        return processTemplate;
    }

    /**
     * pipeline流程控制器
     * 后续扩展则加BusinessCode和ProcessTemplate
     *
     * @return
     */
    @Bean("handlerProcessController")
    public ProcessController processController() {
        ProcessController processController = new ProcessController();
        Map<String, ProcessTemplate> templateConfig = new HashMap<>(4);
        templateConfig.put(PIPELINE_HANDLER_CODE, taskTemplate());
        processController.setTemplateConfig(templateConfig);
        return processController;
    }
}
