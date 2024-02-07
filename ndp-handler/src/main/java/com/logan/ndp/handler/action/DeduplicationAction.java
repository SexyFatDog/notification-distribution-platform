package com.logan.ndp.handler.action;

import cn.hutool.core.collection.CollUtil;
import com.logan.ndp.common.constant.NDPConstant;
import com.logan.ndp.common.enums.DeduplicationType;
import com.logan.ndp.common.task.domain.TaskInfo;
import com.logan.ndp.common.task.pipeline.BusinessProcess;
import com.logan.ndp.common.task.pipeline.ProcessContext;
import com.logan.ndp.common.utils.EnumUtil;
import com.logan.ndp.handler.deduplication.DeduplicationHolder;
import com.logan.ndp.handler.deduplication.DeduplicationParam;
import com.ndp.config.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;


/**
 * 去重服务
 * 1. 根据相同内容N分钟去重（SlideWindowLimitService）
 * 2. 相同的渠道一天内频次去重（SimpleLimitService）
 */
@Service
public class DeduplicationAction implements BusinessProcess<TaskInfo> {

    public static final String DEDUPLICATION_RULE_KEY = "deduplicationRule";

    @Autowired
    private ConfigService config;

    @Autowired
    private DeduplicationHolder deduplicationHolder;

    @Override
    public void process(ProcessContext<TaskInfo> context) {
        TaskInfo taskInfo = context.getProcessModel();

        // 配置样例{"deduplication_10":{"num":1,"time":300},"deduplication_20":{"num":5}}
        String deduplicationConfig = config.getProperty(DEDUPLICATION_RULE_KEY, NDPConstant.EMPTY_JSON_OBJECT);

        // 去重
        List<Integer> deduplicationList = EnumUtil.getCodeList(DeduplicationType.class);
        for (Integer deduplicationType : deduplicationList) {
            DeduplicationParam deduplicationParam = deduplicationHolder.selectBuilder(deduplicationType).build(deduplicationConfig, taskInfo);
            if (Objects.nonNull(deduplicationParam)) {
                deduplicationHolder.selectService(deduplicationType).deduplication(deduplicationParam);
            }
        }

        if (CollUtil.isEmpty(taskInfo.getReceiver())) {
            context.setNeedBreak(true);
        }
    }
}
