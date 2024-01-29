package com.logan.ndp.service.common.pipeline;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * @Description 业务执行模板（把责任链的逻辑串起来）
 * @Author Logan 黄嘉林
 * @Date 2023/7/16 12:51
 **/

@Getter
@Setter
public class ProcessTemplate {
    private List<BusinessProcess> processList;
}
