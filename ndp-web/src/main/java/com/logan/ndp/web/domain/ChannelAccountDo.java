package com.logan.ndp.web.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author 3y
 * 渠道账号信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "channel_account") // since class name doesn't match table name, this annotation must be added
@ToString
public class ChannelAccountDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 账号名称
     */
    private String name;

    /**
     * 发送渠道
     *
     * @see com.logan.ndp.common.enums.ChannelType
     */
    private Integer sendChannel;

    /**
     * 账号配置
     */
    private String accountConfig;

    /**
     * 是否删除
     * 0：未删除
     * 1：已删除
     */
    private Integer isDeleted;

    /**
     * 账号拥有者
     */
    private String creator;

    /**
     * 创建时间 单位 s
     */
    private Integer created;

    /**
     * 更新时间 单位s
     */
    private Integer updated;

}
