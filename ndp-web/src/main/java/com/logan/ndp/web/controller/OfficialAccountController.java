package com.logan.ndp.web.controller;


import com.logan.ndp.common.constant.NDPConstant;
import com.logan.ndp.common.enums.RespStatusEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;

public class OfficialAccountController {
//    @RequestMapping("/check/login")
//    @Operation(summary = "check whether have login")
//    public WxMpUser checkLogin(String sceneId) {
//        String userInfo = redisTemplate.opsForValue().get(sceneId);
//        if (CharSequenceUtil.isBlank(userInfo)) {
//            throw new CommonException(RespStatusEnum.SUCCESS.getCode(), RespStatusEnum.SUCCESS.getMsg(), RespStatusEnum.NO_LOGIN);
//        }
//        return JSON.parseObject(userInfo, WxMpUser.class);
//    }

    @RequestMapping(value = "/receipt", produces = {NDPConstant.CONTENT_TYPE_XML})
    public String receiptMessage(HttpServletRequest request) {
        return RespStatusEnum.DO_NOT_NEED_LOGIN.getMsg();

    }
}
