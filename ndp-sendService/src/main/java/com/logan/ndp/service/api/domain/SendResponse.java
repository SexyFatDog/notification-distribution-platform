package com.logan.ndp.service.api.domain;

import com.logan.ndp.common.task.domain.SimpleTaskInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 发送接口返回值
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SendResponse {
    /**
     * 响应状态
     */
    private String code;
    /**
     * 响应编码
     */
    private String msg;

    /**
     * 实际发送任务列表
     */
    private List<SimpleTaskInfo> data;

}
