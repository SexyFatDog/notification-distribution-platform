package com.logan.ndp.common.task.pipeline;

/**
 * @Description
 * @Author Logan 黄嘉林
 * @Date 2023/7/16 12:52
 **/
public interface BusinessProcess<T extends ProcessModel> {
    /**
     * 真正处理逻辑
     * @Param context
     */
    void process(ProcessContext<T> context);
}
