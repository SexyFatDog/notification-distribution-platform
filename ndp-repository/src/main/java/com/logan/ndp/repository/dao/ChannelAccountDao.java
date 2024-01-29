package com.logan.ndp.repository.dao;

import com.logan.ndp.repository.domain.ChannelAccountDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 渠道账号类型 Dao
 */
public interface ChannelAccountDao extends JpaRepository<ChannelAccountDo, Long> {
    /**
     * 查询 列表
     *
     * @param deleted     0：未删除 1：删除
     * @param channelType 渠道值
     * @param creator     创建者
     * @return List<ChannelAccountDo>
     */
    List<ChannelAccountDo> findAllByIsDeletedEqualsAndCreatorEqualsAndSendChannelEquals(Integer deleted, String creator, Integer channelType);

    /**
     * 查询 列表
     *
     * @param deleted     0：未删除 1：删除
     * @param channelType 渠道值
     * @return List<ChannelAccountDo>
     */
    List<ChannelAccountDo> findAllByIsDeletedEqualsAndSendChannelEquals(Integer deleted, Integer channelType);

    /**
     * 根据创建者检索相关的记录
     *
     * @param creator
     * @return List<ChannelAccountDo>
     */
    List<ChannelAccountDo> findAllByCreatorEquals(String creator);

    /**
     * 统计未删除的条数
     *
     * @param deleted
     * @return
     */
    Long countByIsDeletedEquals(Integer deleted);
}
