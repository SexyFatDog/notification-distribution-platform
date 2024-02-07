package com.logan.ndp.handler.receiver.impl;

import cn.hutool.core.collection.CollUtil;
import com.logan.ndp.common.task.domain.TaskInfo;
import com.logan.ndp.handler.handler.HandlerHolder;
import com.logan.ndp.handler.pending.Task;
import com.logan.ndp.handler.pending.TaskPendingHolder;
import com.logan.ndp.handler.receiver.ConsumeService;
import com.logan.ndp.handler.utils.GroupIdMappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StopWatch;

import java.util.List;

public class ConsumeServiceImpl implements ConsumeService {
    private static final String LOG_BIZ_TYPE = "Receiver#consumer";

    @Autowired
    private HandlerHolder handlerHolder;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TaskPendingHolder taskPendingHolder;

    @Override
    public void consume2Send(List<TaskInfo> taskInfoLists) {
        String topicGroupId = GroupIdMappingUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfoLists.iterator()));
        for (TaskInfo taskInfo : taskInfoLists) {
            //logUtils.print(LogParam.builder().bizType(LOG_BIZ_TYPE).object(taskInfo).build(), AnchorInfo.builder().bizId(taskInfo.getBizId()).messageId(taskInfo.getMessageId()).ids(taskInfo.getReceiver()).businessId(taskInfo.getBusinessId()).state(AnchorState.RECEIVE.getCode()).build());
            Task task = context.getBean(Task.class).setTaskInfo(taskInfo);
            taskPendingHolder.route(topicGroupId).execute(task);
        }
    }
}
