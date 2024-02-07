package com.logan.ndp.handler.pending;


import com.logan.ndp.common.task.domain.TaskInfo;
import com.logan.ndp.common.task.pipeline.ProcessContext;
import com.logan.ndp.common.task.pipeline.ProcessController;
import com.logan.ndp.common.task.pipeline.ProcessModel;
import com.logan.ndp.common.vo.BasicResultVO;
import com.logan.ndp.handler.config.TaskPipelineConfig;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Task 执行器
 */
@Data
@Accessors(chain = true)
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Task implements Runnable {
    private TaskInfo taskInfo;
    @Autowired
    @Qualifier("handlerProcessController")
    private ProcessController processController;

    @Override
    public void run() {
        ProcessContext<ProcessModel> context = ProcessContext.builder()
                .processModel(taskInfo).code(TaskPipelineConfig.PIPELINE_HANDLER_CODE)
                .needBreak(false).response(BasicResultVO.success())
                .build();
        processController.process(context);
    }
}
