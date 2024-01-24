package com.logan.ndp.common.dto.account.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Logan 黄嘉林
 * @Date 2023/7/16 12:24
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsAccount {
    /**
     * 标识渠道商Id
     */
    protected Integer supplierId;

    /**
     * 标识渠道商名字
     */
    protected String supplierName;

    /**
     * 【重要】类名，定位到具体的处理"下发"/"回执"逻辑
     * 依据ScriptName对应具体的某一个短信账号
     */
    protected String scriptName;


}
