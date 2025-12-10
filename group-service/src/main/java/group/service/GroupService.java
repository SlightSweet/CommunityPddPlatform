package group.service;

import group.pojo.GroupActivity;
import group.pojo.GroupOrder;
import group.pojo.GroupOrderMember;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupService {
    List<GroupActivity> findAllGroupActivities();
    GroupActivity findGroupActivityById(Long id);

    List<GroupOrder> findGroupOrdersByActivityId(Long activityId);
    GroupOrder findGroupOrderById(Long id);
    boolean createGroupOrder(Long activityId, Long leaderId);
    boolean joinGroupOrder(Long groupOrderId, Long userId, Long orderId);

    List<GroupOrderMember> findGroupOrderMembersByGroupOrderId(Long groupOrderId);
}