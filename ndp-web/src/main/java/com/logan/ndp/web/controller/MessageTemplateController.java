package com.logan.ndp.web.controller;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import com.logan.ndp.common.constant.NDPConstant;
import com.logan.ndp.web.domain.MessageTemplateDo;
import com.logan.ndp.web.service.MessageTemplateService;
import com.logan.ndp.web.util.Convert4Amis;
import com.logan.ndp.web.vo.MessageTemplateParams;
import com.logan.ndp.web.vo.MessageTemplateVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.service.UnknownServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/messageTemplate")
@Tag(name = "message Template")
public class MessageTemplateController {
    @Autowired
    private MessageTemplateService messageTemplateService;

    /**
     * if "id" exists -> save
     * else -> update
     * @param messageTemplateDo
     * @return messageTemplateDo
     */
    @PostMapping("/save")
    @Operation(method = "post", summary = "save or update message template")
    public MessageTemplateDo saveOrUpdate(@RequestBody MessageTemplateDo messageTemplateDo){
        return messageTemplateService.saveOrUpdate(messageTemplateDo);
    }


    /**
     * query all the message template
     * @param messageTemplateParams
     * @return  MessageTemplateVo{private List<Map<String, Object>> rows;private Long count;}
     */
    @GetMapping("/list")
    @Operation(method = "get", summary = "for list page to query all the message Template")
    public MessageTemplateVo queryList(@Validated MessageTemplateParams messageTemplateParams){
        if(CharSequenceUtil.isBlank(messageTemplateParams.getCreator())){
            messageTemplateParams.setCreator(NDPConstant.DEFAULT_CREATOR);
        }
        Page<MessageTemplateDo> messageTemplateDoPage = messageTemplateService.queryList(messageTemplateParams);
        // front-end Baidu Amis framework, So all the elements must be adapted to the framework
        if(Objects.nonNull(messageTemplateDoPage)){
            return null;
        }
        List<Map<String, Object>> result = Convert4Amis.flatListMap(messageTemplateDoPage.getContent());
        return MessageTemplateVo.builder().count(messageTemplateDoPage.getTotalElements()).rows(result).build();
    }

    @GetMapping("query/{id}")
    @Operation(method = "Get", summary = "query by id")
    public Map<String, Object> queryById(@PathVariable("id") Long id){
        return Convert4Amis.flatSingleMap(messageTemplateService.queryById(id));
    }

    @PostMapping("copy/{id}")
    @Operation(method = "post", summary = "copy by id")
    public void copyById(@PathVariable("id") long id){
        messageTemplateService.copy(id);
    }

    public void deleteById(@PathVariable("id") String id){
        if(CharSequenceUtil.isNotBlank(id)){
            List<Long> idList = Arrays.stream(id.split(StrPool.COMMA)).map(Long::valueOf).collect(Collectors.toList());
            messageTemplateService.deleteByIds(idList);
        }
    }
}
