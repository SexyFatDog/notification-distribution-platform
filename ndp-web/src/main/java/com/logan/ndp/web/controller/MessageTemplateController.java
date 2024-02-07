package com.logan.ndp.web.controller;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import com.alibaba.fastjson.JSON;
import com.logan.ndp.common.constant.NDPConstant;
import com.logan.ndp.common.enums.RespStatusEnum;
import com.logan.ndp.repository.domain.MessageTemplateDo;
import com.logan.ndp.service.api.SendService;
import com.logan.ndp.service.api.domain.SendRequest;
import com.logan.ndp.service.api.domain.SendResponse;
import com.logan.ndp.service.api.enums.BusinessCode;
import com.logan.ndp.common.task.domain.MessageParam;
import com.logan.ndp.web.exception.CommonException;
import com.logan.ndp.web.service.MessageTemplateService;
import com.logan.ndp.web.util.Convert4Amis;
import com.logan.ndp.web.vo.MessageTemplateParams;
import com.logan.ndp.web.vo.MessageTemplateVo;
import com.logan.ndp.web.vo.amis.CommonAmisVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private SendService sendService;

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

    @DeleteMapping("delete/{id}")
    public void deleteById(@PathVariable("id") String id){
        if(CharSequenceUtil.isNotBlank(id)){
            List<Long> idList = Arrays.stream(id.split(StrPool.COMMA)).map(Long::valueOf).collect(Collectors.toList());
            messageTemplateService.deleteByIds(idList);
        }
    }

    /**
     *  测试发送接口
     * @param messageTemplateParam
     * @return SendResponse
     */
    @PostMapping("test")
    @Operation(method = "post", summary = "测试发送接口")
    public SendResponse test(@RequestBody MessageTemplateParams messageTemplateParam) {

        Map<String, String> variables = JSON.parseObject(messageTemplateParam.getMsgContent(), Map.class);
        MessageParam messageParam = MessageParam.builder().receiver(messageTemplateParam.getReceiver()).variables(variables).build();
        SendRequest sendRequest = SendRequest.builder().code(BusinessCode.COMMON_SEND.getCode()).messageTemplateId(messageTemplateParam.getId()).messageParam(messageParam).build();
        SendResponse response = sendService.send(sendRequest);
        if (!Objects.equals(response.getCode(), RespStatusEnum.SUCCESS.getCode())) {
            throw new CommonException(response.getMsg());
        }
        return response;
    }

    /**
     * 获取需要测试的模板占位符，透出给Amis
     */
    @PostMapping("test/content")
    @Operation(method = "post", summary = "获取需要测试的模板占位符，透出给Amis")
    public CommonAmisVo test(Long id) {
        MessageTemplateDo messageTemplate = messageTemplateService.queryById(id);
        return Convert4Amis.getTestContent(messageTemplate.getMsgContent());
    }
}
