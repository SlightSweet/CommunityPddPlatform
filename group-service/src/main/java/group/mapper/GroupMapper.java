package group.mapper;


import group.pojo.GroupActivity;
import group.pojo.GroupOrder;
import group.pojo.GroupOrderMember;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface GroupMapper {
    List<GroupActivity> findAllGroupActivities();
    GroupActivity findGroupActivityById(Long id);

    List<GroupOrder> findGroupOrdersByActivityId(Long activityId);
    GroupOrder findGroupOrderById(Long id);
    void insertGroupOrder(GroupOrder groupOrder);
    void updateGroupOrder(GroupOrder groupOrder);

    List<GroupOrderMember> findGroupOrderMembersByGroupOrderId(Long groupOrderId);
    void insertGroupOrderMember(GroupOrderMember groupOrderMember);
}