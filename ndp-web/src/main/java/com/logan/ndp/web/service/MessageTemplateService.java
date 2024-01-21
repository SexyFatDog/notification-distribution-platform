package com.logan.ndp.web.service;

import com.logan.ndp.web.domain.MessageTemplateDo;
import com.logan.ndp.web.vo.BasicResultVO;
import com.logan.ndp.web.vo.MessageTemplateParams;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MessageTemplateService {

    /**
     * 查询未删除的模板列表（分页)
     *
     * @param messageTemplateParam
     * @return
     */
    Page<MessageTemplateDo> queryList(MessageTemplateParams messageTemplateParam);

    /**
     * 统计未删除的条数
     *
     * @return
     */
    Long count();

    /**
     * 单个 保存或者更新
     * 存在ID 更新
     * 不存在ID保存
     *
     * @param messageTemplate
     * @return
     */
    MessageTemplateDo saveOrUpdate(MessageTemplateDo messageTemplate);


    /**
     * 软删除(deleted=1)
     *
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 根据ID查询模板信息
     *
     * @param id
     * @return
     */
    MessageTemplateDo    queryById(Long id);

    /**
     * 复制配置
     *
     * @param id
     */
    void copy(Long id);

    /**
     * 启动模板的定时任务
     *
     * @param id
     * @return
     */
    BasicResultVO startCronTask(Long id);

    /**
     * 暂停模板的定时任务
     *
     * @param id
     * @return
     */
    BasicResultVO stopCronTask(Long id);
}
