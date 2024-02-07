package com.logan.ndp.handler.handler.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.RateLimiter;
import com.logan.ndp.common.dto.model.EmailContentModel;
import com.logan.ndp.common.enums.ChannelType;
import com.logan.ndp.common.task.domain.TaskInfo;
import com.logan.ndp.handler.enums.RateLimitStrategy;
import com.logan.ndp.handler.flowcontrol.FlowControlParam;
import com.logan.ndp.handler.handler.BaseHandler;
import com.logan.ndp.handler.handler.Handler;
import com.logan.ndp.repository.utils.AccountUtils;
import com.logan.ndp.repository.utils.FileUtils;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * 邮件发送处理
 */
@Component
@Slf4j
public class EmailHandler extends BaseHandler implements Handler {

    @Autowired
    private AccountUtils accountUtils;

    @Value("${ndp.business.upload.crowd.path}")
    private String dataPath;

    public EmailHandler() {
        channelCode = ChannelType.EMAIL.getCode();

        // 按照请求限流，默认单机 3 qps （具体数值配置在apollo动态调整)
        Double rateInitValue = Double.valueOf(3);
        flowControlParam = FlowControlParam.builder().rateInitValue(rateInitValue)
                .rateLimitStrategy(RateLimitStrategy.REQUEST_RATE_LIMIT)
                .rateLimiter(RateLimiter.create(rateInitValue)).build();

    }

    @Override
    public boolean handler(TaskInfo taskInfo) {
        EmailContentModel emailContentModel = (EmailContentModel) taskInfo.getContentModel();
        MailAccount account = getAccountConfig(taskInfo.getSendAccount());
        try {
            List<File> files = CharSequenceUtil.isNotBlank(emailContentModel.getFileUrl()) ? FileUtils.getRemoteUrl2File(dataPath, CharSequenceUtil.split(emailContentModel.getFileUrl(), StrPool.COMMA)) : null;
            if (CollUtil.isEmpty(files)) {
                MailUtil.send(account, taskInfo.getReceiver(), emailContentModel.getTitle(), emailContentModel.getContent(), true);
            } else {
                MailUtil.send(account, taskInfo.getReceiver(), emailContentModel.getTitle(), emailContentModel.getContent(), true, files.toArray(new File[files.size()]));
            }


        } catch (Exception e) {
            log.error("EmailHandler#handler fail!{},params:{}", Throwables.getStackTraceAsString(e), taskInfo);
            return false;
        }
        return true;
    }

    /**
     * 获取账号信息和配置
     *
     * @return
     */
    private MailAccount getAccountConfig(Integer sendAccount) {
        MailAccount account = accountUtils.getAccountById(sendAccount, MailAccount.class);
        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            account.setAuth(account.isAuth()).setStarttlsEnable(account.isStarttlsEnable()).setSslEnable(account.isSslEnable()).setCustomProperty("mail.smtp.ssl.socketFactory", sf);
            account.setTimeout(25000).setConnectionTimeout(25000);
        } catch (Exception e) {
            log.error("EmailHandler#getAccount fail!{}", Throwables.getStackTraceAsString(e));
        }
        return account;
    }

    /**
     * 邮箱 api 不支持撤回消息
     * @param recallTaskInfo
     */
//    public void recall(RecallTaskInfo recallTaskInfo) {
//
//    }
}
