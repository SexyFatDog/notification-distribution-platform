package com.logan.ndp.handler.receiver;

import com.logan.ndp.common.task.domain.TaskInfo;
import org.springframework.util.StopWatch;

import java.util.List;

/**
 * 消息消息队列里的发送指令
 */
public interface ConsumeService {
    /**
     * 从MQ拉到消息进行消费，发送消息
     *
     * @param taskInfoLists
     */
    void consume2Send(List<TaskInfo> taskInfoLists);


    /**
     * 从MQ拉到消息进行消费，撤回消息
     * 如果有 recallMessageId ，则优先撤回 recallMessageId
     * 如果没有 recallMessageId ，则撤回整个模板的消息
     *
     * @param recallTaskInfo
     */
//    void consume2recall(RecallTaskInfo recallTaskInfo);
}
