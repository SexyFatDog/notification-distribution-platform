package com.logan.ndp.web.controller;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import com.logan.ndp.common.constant.NDPConstant;
import com.logan.ndp.repository.domain.ChannelAccountDo;
import com.logan.ndp.web.service.ChannelAccountService;
import com.logan.ndp.web.util.Convert4Amis;
import com.logan.ndp.web.vo.amis.CommonAmisVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account")
@Tag(name = "channel account management")
public class ChannelAccountController {

    @Autowired
    private ChannelAccountService channelAccountService;

    /**
     * 如果Id存在，则修改
     * 如果Id不存在，则保存
     */
    @PostMapping("/save")
    @Operation(method = "post", summary = "save channel account info")
    public ChannelAccountDo saveOrUpdate(@RequestBody ChannelAccountDo channelAccount) {
        channelAccount.setCreator(CharSequenceUtil.isBlank(channelAccount.getCreator()) ? NDPConstant.DEFAULT_CREATOR : channelAccount.getCreator());

        return channelAccountService.save(channelAccount);
    }

    /**
     * 根据渠道标识查询渠道账号相关的信息
     */
    @GetMapping("/queryByChannelType")
    @Operation(method = "get", summary = "query related records by channel account Type")
    public List<CommonAmisVo> query(Integer channelType, String creator) {
        creator = CharSequenceUtil.isBlank(creator) ? NDPConstant.DEFAULT_CREATOR : creator;

        List<ChannelAccountDo> channelAccounts = channelAccountService.queryByChannelType(channelType, creator);
        return Convert4Amis.getChannelAccountVo(channelAccounts, channelType);
    }

    /**
     * 所有的渠道账号信息
     */
    @GetMapping("/list")
    @Operation(method = "get", summary = "query all channel accounts")
    public List<ChannelAccountDo> list(String creator) {
        creator = CharSequenceUtil.isBlank(creator) ? NDPConstant.DEFAULT_CREATOR : creator;

        return channelAccountService.list(creator);
    }

    /**
     * 根据Id删除
     * id多个用逗号分隔开
     */
    @DeleteMapping("delete/{id}")
    @Operation(method = "delete", summary = "delete by id")
    public void deleteByIds(@PathVariable("id") String id) {
        if (CharSequenceUtil.isNotBlank(id)) {
            List<Long> idList = Arrays.stream(id.split(StrPool.COMMA)).map(Long::valueOf).collect(Collectors.toList());
            channelAccountService.deleteByIds(idList);
        }
    }
}
