package com.logan.ndp.common.enums;

import com.logan.ndp.common.dto.model.ContentModel;
import com.logan.ndp.common.dto.model.EmailContentModel;
import com.logan.ndp.common.dto.model.SmsContentModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Objects;

@Getter
@ToString
@AllArgsConstructor
public enum ChannelType implements PowerfulEnum {

    //IM(10, "IM(站内信)", ImContentModel.class, "im"),

    //PUSH(20, "push(通知栏)", PushContentModel.class, "push"),

    SMS(30, "sms(短信)",  SmsContentModel.class, "sms"),

    EMAIL(40, "email(邮件)", EmailContentModel.class, "email"),

    // OFFICIAL_ACCOUNT(50, "officialAccounts(服务号)", OfficialAccountsContentModel.class, "official_accounts"),

    // MINI_PROGRAM(60, "miniProgram(小程序)", MiniProgramContentModel.class, "mini_program"),

    // ENTERPRISE_WE_CHAT(70, "enterpriseWeChat(企业微信)", EnterpriseWeChatContentModel.class, "enterprise_we_chat"),

    // DING_DING_ROBOT(80, "dingDingRobot(钉钉机器人)", DingDingRobotContentModel.class, "ding_ding_robot"),

    // DING_DING_WORK_NOTICE(90, "dingDingWorkNotice(钉钉工作通知)", DingDingWorkContentModel.class, "ding_ding_work_notice"),

    // ENTERPRISE_WE_CHAT_ROBOT(100, "enterpriseWeChat(企业微信机器人)", EnterpriseWeChatRobotContentModel.class, "enterprise_we_chat_robot"),

    // FEI_SHU_ROBOT(110, "feiShuRoot(飞书机器人)", FeiShuRobotContentModel.class, "fei_shu_robot"),

    // ALIPAY_MINI_PROGRAM(120, "alipayMiniProgram(支付宝小程序)", AlipayMiniProgramContentModel.class, "alipay_mini_program"),
    ;

    /**
     * 编码值
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String description;

    /**
     * 内容模型Class
     */
    private final Class<? extends ContentModel> contentModelClass;

    /**
     * 英文标识
     */
    private final String codeEn;

    /**
     * 通过code获取class
     * @Param code
     */
    public static Class<? extends ContentModel> getChanelModelClassByCode(Integer code) {
        return Arrays.stream(ChannelType.values()).filter(channelType -> Objects.equals(code, channelType.getCode()))
                .map(ChannelType::getContentModelClass)
                .findFirst().orElse(null);
    }
}
