package com.logan.ndp.common.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 短信消息模型
 * @Author Logan 黄嘉林
 * @Date 2023/7/15 15:26
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsContentModel extends ContentModel{

    /**
     * 短信内容
     */
    private String content;

    /**
     * 短信链接
     */
    private String url;
}
