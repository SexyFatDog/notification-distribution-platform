package com.logan.ndp.web.service.impl;



import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.logan.ndp.common.constant.CommonConstant;
import com.logan.ndp.common.constant.NDPConstant;
import com.logan.ndp.common.enums.AuditStatus;
import com.logan.ndp.common.enums.MessageStatus;
import com.logan.ndp.repository.dao.MessageTemplateDao;
import com.logan.ndp.repository.domain.MessageTemplateDo;
import com.logan.ndp.web.service.MessageTemplateService;
import com.logan.ndp.common.vo.BasicResultVO;
import com.logan.ndp.web.vo.MessageTemplateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MessageTemplateServiceImpl implements MessageTemplateService {

    @Autowired
    private MessageTemplateDao messageTemplateDao;

    @Override
    public Page<MessageTemplateDo> queryList(MessageTemplateParams messageTemplateParam) {
        PageRequest pageRequest = PageRequest.of(messageTemplateParam.getPage() - 1, messageTemplateParam.getPerPage());
        String creator = CharSequenceUtil.isBlank(messageTemplateParam.getCreator()) ? NDPConstant.DEFAULT_CREATOR : messageTemplateParam.getCreator();
        return messageTemplateDao.findAll(new Specification<MessageTemplateDo>() {
            @Override
            public Predicate toPredicate(Root<MessageTemplateDo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // store query conditions
                List<Predicate> predicateList = new ArrayList<>();

                // add query conditions
                if(CharSequenceUtil.isNotBlank(messageTemplateParam.getKeywords())){
                    // add a condition: if TEMPLATE NAME is not blank, add it as a predicate
                    predicateList.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + messageTemplateParam.getKeywords() + "%"));
                }
                // add a condition: isDeleted must equal to CommonConstant.FALSE
                predicateList.add(criteriaBuilder.equal(root.get("isDeleted").as(Integer.class), CommonConstant.FALSE));
                // add a condition: creator equal to create
                predicateList.add(criteriaBuilder.equal(root.get("creator").as(String.class), creator));

                Predicate[] p = new Predicate[predicateList.size()];
                // connect all conditions with AND
                criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(p)));
                // Sort: Sort by "updated" field in descending order
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("updated")));

                return criteriaQuery.getRestriction();
            }
        }, pageRequest);

    }

    @Override
    public Long count() {
        return messageTemplateDao.countByIsDeletedEquals(CommonConstant.FALSE);
    }

    @Override
    public MessageTemplateDo saveOrUpdate(MessageTemplateDo messageTemplate) {
        if (Objects.isNull(messageTemplate.getId())){
            initStatus(messageTemplate);
        }
        messageTemplate.setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
        return messageTemplateDao.save(messageTemplate);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        Iterable<MessageTemplateDo> messageTemplateDos = messageTemplateDao.findAllById(ids);
        messageTemplateDos.forEach(messageTemplateDo -> {
            messageTemplateDo.setIsDeleted(CommonConstant.TRUE);
        });
        messageTemplateDao.saveAll(messageTemplateDos);
    }

    @Override
    public MessageTemplateDo queryById(Long id) {
        return messageTemplateDao.findById(id).orElse(null);
    }

    @Override
    public void copy(Long id) {
        MessageTemplateDo messageTemplateDo = messageTemplateDao.findById(id).orElse(null);
        if (Objects.nonNull(messageTemplateDo)){
            MessageTemplateDo clone = messageTemplateDo.setId(null).setCronTaskId(null);
            messageTemplateDao.save(messageTemplateDo);
        }
    }

    @Override
    public BasicResultVO startCronTask(Long id) {
        return null;
    }

    @Override
    public BasicResultVO stopCronTask(Long id) {
        return null;
    }

    /**
     * 初始化状态信息
     *
     * @param messageTemplate
     */
    private void initStatus(MessageTemplateDo messageTemplate) {
        messageTemplate.setFlowId(CharSequenceUtil.EMPTY)
                .setMsgStatus(MessageStatus.INIT.getCode()).setAuditStatus(AuditStatus.WAIT_AUDIT.getCode())
                .setCreator(CharSequenceUtil.isBlank(messageTemplate.getCreator()) ? NDPConstant.DEFAULT_CREATOR : messageTemplate.getCreator())
                .setUpdator(CharSequenceUtil.isBlank(messageTemplate.getUpdator()) ? NDPConstant.DEFAULT_UPDATER : messageTemplate.getUpdator())
                .setTeam(CharSequenceUtil.isBlank(messageTemplate.getTeam()) ? NDPConstant.DEFAULT_TEAM : messageTemplate.getTeam())
                .setAuditor(CharSequenceUtil.isBlank(messageTemplate.getAuditor()) ? NDPConstant.DEFAULT_AUDITOR : messageTemplate.getAuditor())
                .setCreated(Math.toIntExact(DateUtil.currentSeconds()))
                .setIsDeleted(CommonConstant.FALSE);

    }

//    /**
//     * 1. 重置模板的状态
//     * 2. 修改定时任务信息(如果存在)
//     *
//     * @param messageTemplate
//     */
//    private void resetStatus(MessageTemplate messageTemplate) {
//        messageTemplate.setUpdator(messageTemplate.getUpdator())
//                .setMsgStatus(MessageStatus.INIT.getCode()).setAuditStatus(AuditStatus.WAIT_AUDIT.getCode());
//
//        // 从数据库查询并注入 定时任务 ID
//        MessageTemplate dbMsg = queryById(messageTemplate.getId());
//        if (Objects.nonNull(dbMsg) && Objects.nonNull(dbMsg.getCronTaskId())) {
//            messageTemplate.setCronTaskId(dbMsg.getCronTaskId());
//        }
//
//        if (Objects.nonNull(messageTemplate.getCronTaskId()) && TemplateType.CLOCKING.getCode().equals(messageTemplate.getTemplateType())) {
//            XxlJobInfo xxlJobInfo = xxlJobUtils.buildXxlJobInfo(messageTemplate);
//            cronTaskService.saveCronTask(xxlJobInfo);
//            cronTaskService.stopCronTask(messageTemplate.getCronTaskId());
//        }
//    }

}
