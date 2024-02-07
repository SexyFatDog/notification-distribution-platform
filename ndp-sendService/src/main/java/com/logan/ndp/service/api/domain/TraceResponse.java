package com.logan.ndp.service.api.domain;

import com.logan.ndp.common.task.domain.SimpleAnchorInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TraceResponse {
    /**
     * 响应状态
     */
    private String code;
    /**
     * 响应编码
     */
    private String msg;

    /**
     * 埋点信息
     */
    private List<SimpleAnchorInfo> data;
}
