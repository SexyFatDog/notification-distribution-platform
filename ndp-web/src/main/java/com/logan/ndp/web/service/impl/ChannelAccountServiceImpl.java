package com.logan.ndp.web.service.impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.logan.ndp.common.constant.NDPConstant;
import com.logan.ndp.repository.dao.ChannelAccountDao;
import com.logan.ndp.repository.domain.ChannelAccountDo;
import com.logan.ndp.web.service.ChannelAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ChannelAccountServiceImpl implements ChannelAccountService {

    @Autowired
    private ChannelAccountDao channelAccountDao;


    @Override
    public ChannelAccountDo save(ChannelAccountDo channelAccountDo) {
        if(Objects.isNull(channelAccountDo.getId())){
            channelAccountDo.setCreated(Math.toIntExact(DateUtil.currentSeconds()));
            channelAccountDo.setIsDeleted(NDPConstant.FALSE);
        }
        channelAccountDo.setCreator(CharSequenceUtil.isNotBlank(channelAccountDo.getCreator()) ? channelAccountDo.getCreator() : NDPConstant.DEFAULT_CREATOR);
        channelAccountDo.setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
        return channelAccountDao.save(channelAccountDo);
    }

    @Override
    public List<ChannelAccountDo> queryByChannelType(Integer channelType, String creator) {
        return channelAccountDao.findAllByIsDeletedEqualsAndCreatorEqualsAndSendChannelEquals(NDPConstant.FALSE, creator, channelType);
    }

    @Override
    public List<ChannelAccountDo> list(String creator) {
        return channelAccountDao.findAllByCreatorEquals(creator);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        for(Long id:ids){
            channelAccountDao.delete(id);
        }
    }
}
