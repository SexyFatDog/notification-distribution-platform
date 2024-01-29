package com.logan.ndp.web.service;

import com.logan.ndp.repository.domain.ChannelAccountDo;

import java.util.List;

public interface ChannelAccountService {


    /**
     * 保存/修改渠道账号信息
     *
     * @param ChannelAccountDo
     * @return
     */
    ChannelAccountDo save(ChannelAccountDo channelAccount);

    /**
     * 根据渠道标识查询账号信息
     *
     * @param channelType 渠道标识
     * @param creator     创建者
     * @return
     */
    List<ChannelAccountDo> queryByChannelType(Integer channelType, String creator);


    /**
     * 列表信息
     *
     * @param creator
     * @return
     */
    List<ChannelAccountDo> list(String creator);

    /**
     * 软删除(deleted=1)
     *
     * @param ids
     */
    void deleteByIds(List<Long> ids);

}