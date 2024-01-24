package com.logan.ndp.common.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 电子邮件消息体
 * @Author Logan 黄嘉林
 * @Date 2023/7/15 15:23
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailContentModel extends ContentModel{

    /**
     * 邮件标题
     */
    private String title;

    /**
     * 邮件内容，可写入html格式的内容
     */
    private String content;

    /**
     * 邮件附件的连接
     */
    private String fileUrl;
}
