package com.logan.ndp.common.task.pipeline;

import com.logan.ndp.common.vo.BasicResultVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description ProcessContext代表责任链中每一个步骤的上下文。包含责任链的编号，中断标志，和ProcessModel（储存上下文信息），响应结果
 * @Author Logan 黄嘉林
 * @Date 2023/7/16 12:54
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class ProcessContext<T extends ProcessModel> {

    /**
     * 标识责任链的code
     */
    private String code;

    /**
     * 存储责任链上下文数据的模型
     */
    private T processModel;

    /**
     * 责任链中断的标识
     */
    private Boolean needBreak;

    /**
     * 流程处理的结果
     */
    BasicResultVO response;

}
