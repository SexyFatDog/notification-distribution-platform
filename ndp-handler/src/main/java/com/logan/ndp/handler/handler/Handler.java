package com.logan.ndp.handler.handler;


import com.logan.ndp.common.task.domain.TaskInfo;

/**
 * 消息处理器
 */
public interface Handler {

    /**
     * 处理器
     *
     * @param taskInfo
     */
    void doHandler(TaskInfo taskInfo);

    /**
     * 撤回消息
     *
     * @param recallTaskInfo
     * @return
     */
//    void recall(RecallTaskInfo recallTaskInfo);

}
