package group.controller;

import group.pojo.GroupActivity;
import group.pojo.GroupOrder;
import group.pojo.GroupOrderMember;
import group.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping("/activities")
    public List<GroupActivity> findAllGroupActivities() {
        return groupService.findAllGroupActivities();
    }

    @GetMapping("/activity/{id}")
    public GroupActivity findGroupActivityById(@PathVariable Long id) {
        return groupService.findGroupActivityById(id);
    }

    @GetMapping("/{activityId}/orders")
    public List<GroupOrder> findGroupOrdersByActivityId(@PathVariable Long activityId) {
        return groupService.findGroupOrdersByActivityId(activityId);
    }

    @PostMapping("/order/create")
    public ResponseEntity<String> createGroupOrder(@RequestParam Long activityId, @RequestParam Long leaderId) {
        boolean result = groupService.createGroupOrder(activityId, leaderId);
        if (result) {
            return ResponseEntity.ok("团购订单创建成功");
        } else {
            return ResponseEntity.badRequest().body("团购订单创建失败");
        }
    }

    @PostMapping("/order/{groupOrderId}/join")
    public ResponseEntity<String> joinGroupOrder(
            @PathVariable Long groupOrderId,
            @RequestParam Long userId,
            @RequestParam Long orderId) {
        boolean result = groupService.joinGroupOrder(groupOrderId, userId, orderId);
        if (result) {
            return ResponseEntity.ok("加入团购成功");
        } else {
            return ResponseEntity.badRequest().body("加入团购失败");
        }
    }

    @GetMapping("/order/{groupOrderId}/members")
    public List<GroupOrderMember> findGroupOrderMembersByGroupOrderId(@PathVariable Long groupOrderId) {
        return groupService.findGroupOrderMembersByGroupOrderId(groupOrderId);
    }
}
