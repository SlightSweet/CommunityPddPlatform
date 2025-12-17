package group.service.impl;

import group.mapper.GroupMapper;
import group.pojo.GroupActivity;
import group.pojo.GroupOrder;
import group.pojo.GroupOrderMember;
import group.service.GroupService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupMapper groupMapper;

    public GroupServiceImpl(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    @Override
    public List<GroupActivity> findAllGroupActivities() {
        return groupMapper.findAllGroupActivities();
    }

    @Override
    public GroupActivity findGroupActivityById(Long id) {
        return groupMapper.findGroupActivityById(id);
    }

    @Override
    public List<GroupOrder> findGroupOrdersByActivityId(Long activityId) {
        return groupMapper.findGroupOrdersByActivityId(activityId);
    }

    @Override
    public GroupOrder findGroupOrderById(Long id) {
        return groupMapper.findGroupOrderById(id);
    }

    @Override
    public boolean createGroupOrder(Long activityId, Long leaderId) {
        GroupActivity activity = groupMapper.findGroupActivityById(activityId);
        if (activity == null) {
            return false;
        }

        GroupOrder groupOrder = new GroupOrder();
        groupOrder.setActivityId(activityId);
        groupOrder.setLeaderId(leaderId);
        groupOrder.setCurrentMembers(1);
        // 删除调用不存在的setStatus方法
        groupOrder.setCreateTime(new Date());
        
        // 删除调用不存在的setExpireTime方法

        groupMapper.insertGroupOrder(groupOrder);
        return true;
    }

    @Override
    public boolean joinGroupOrder(Long groupOrderId, Long userId, Long orderId) {
        GroupOrder groupOrder = groupMapper.findGroupOrderById(groupOrderId);
        if (groupOrder == null) {
            return false;
        }

        // 检查是否已满员或已结束
        GroupActivity activity = groupMapper.findGroupActivityById(groupOrder.getActivityId());
        // 修改此处，使用正确的字段名
        if (groupOrder.getCurrentMembers() >= activity.getMinMembers() || groupOrder.getGroupStatus() != 0) {
            return false;
        }

        GroupOrderMember member = new GroupOrderMember();
//        member.setGroupOrderId(groupOrderId);
        member.setUserId(userId);
        member.setOrderId(orderId);
        member.setJoinTime(new Date());

        groupMapper.insertGroupOrderMember(member);

        // 更新当前人数
        groupOrder.setCurrentMembers(groupOrder.getCurrentMembers() + 1);
        // 如果达到最低人数要求，则更新状态为已完成
        if (groupOrder.getCurrentMembers() >= activity.getMinMembers()) {
            // 修改此处，使用正确的字段名
            groupOrder.setGroupStatus((byte) 1); // 设置为已完成状态
        }
        groupMapper.updateGroupOrder(groupOrder);

        return true;
    }

    @Override
    public List<GroupOrderMember> findGroupOrderMembersByGroupOrderId(Long groupOrderId) {
        return groupMapper.findGroupOrderMembersByGroupOrderId(groupOrderId);
    }
}
