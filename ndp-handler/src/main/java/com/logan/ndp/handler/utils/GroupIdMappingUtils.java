package com.logan.ndp.handler.utils;



import com.logan.ndp.common.enums.ChannelType;
import com.logan.ndp.common.enums.MessageType;
import com.logan.ndp.common.task.domain.TaskInfo;
import com.logan.ndp.common.utils.EnumUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * groupId 标识着每一个消费者组
 */
public class GroupIdMappingUtils {
    private GroupIdMappingUtils() {
    }

    /**
     * 获取所有的groupIds
     * (不同的渠道不同的消息类型拥有自己的groupId)
     */
    public static List<String> getAllGroupIds() {
        List<String> groupIds = new ArrayList<>();
        for (ChannelType channelType : ChannelType.values()) {
            for (MessageType messageType : MessageType.values()) {
                groupIds.add(channelType.getCodeEn() + "." + messageType.getCodeEn());
            }
        }
        return groupIds;
    }


    /**
     * 根据TaskInfo获取当前消息的groupId
     *
     * @param taskInfo
     * @return
     */
    public static String getGroupIdByTaskInfo(TaskInfo taskInfo) {
        String channelCodeEn = EnumUtil.getEnumByCode(taskInfo.getSendChannel(), ChannelType.class).getCodeEn();
        String msgCodeEn = EnumUtil.getEnumByCode(taskInfo.getMsgType(), MessageType.class).getCodeEn();
        return channelCodeEn + "." + msgCodeEn;
    }
}
