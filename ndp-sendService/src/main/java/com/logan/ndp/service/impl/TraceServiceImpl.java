package com.logan.ndp.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.logan.ndp.common.constant.NDPConstant;
import com.logan.ndp.common.enums.RespStatusEnum;
import com.logan.ndp.repository.utils.RedisUtils;
import com.logan.ndp.service.api.TraceService;
import com.logan.ndp.service.api.domain.TraceResponse;
import com.logan.ndp.service.common.domain.SimpleAnchorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TraceServiceImpl implements TraceService {
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public TraceResponse traceByMessageId(String messageId) {
        if (CharSequenceUtil.isBlank(messageId)) {
            return new TraceResponse(RespStatusEnum.CLIENT_BAD_PARAMETERS.getCode(), RespStatusEnum.CLIENT_BAD_PARAMETERS.getMsg(), null);
        }
        String redisMessageKey = CharSequenceUtil.join(StrUtil.COLON, NDPConstant.CACHE_KEY_PREFIX, NDPConstant.MESSAGE_ID, messageId);
        List<String> messageList = redisUtils.lRange(redisMessageKey, 0, -1);
        if (CollUtil.isEmpty(messageList)) {
            return new TraceResponse(RespStatusEnum.FAIL.getCode(), RespStatusEnum.FAIL.getMsg(), null);
        }

        // 0. 按时间排序
        List<SimpleAnchorInfo> sortAnchorList = messageList.stream().map(s -> JSON.parseObject(s, SimpleAnchorInfo.class)).sorted((o1, o2) -> Math.toIntExact(o1.getTimestamp() - o2.getTimestamp())).collect(Collectors.toList());

        return new TraceResponse(RespStatusEnum.SUCCESS.getCode(), RespStatusEnum.SUCCESS.getMsg(), sortAnchorList);
    }
}
