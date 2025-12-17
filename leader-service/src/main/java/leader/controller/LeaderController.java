package leader.controller;

import leader.pojo.Leader;
import leader.service.LeaderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leader")
public class LeaderController {
    private final LeaderService leaderService;

    public LeaderController(LeaderService leaderService) {
        this.leaderService = leaderService;
    }

    @GetMapping("/statistics/{leaderId}")
    public ResponseEntity<?> getLeaderStatistics(@PathVariable Long leaderId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalOrders", leaderService.countOrdersByLeaderId(leaderId));
        stats.put("totalGroups", leaderService.countGroupsByLeaderId(leaderId));
        stats.put("totalProducts", leaderService.countAllProducts());
        // 这里简单设置一个默认值，实际应用中应该从订单表中计算总收益
        stats.put("totalRevenue", "¥0");
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/recent-orders/{leaderId}")
    public ResponseEntity<?> getRecentOrders(@PathVariable Long leaderId) {
        // 模拟最近订单数据
        List<Map<String, Object>> orders = new ArrayList<>();
        Map<String, Object> order1 = new HashMap<>();
        order1.put("orderId", "ORD202512001");
        order1.put("productName", "优质大米 5kg");
        order1.put("quantity", 2);
        order1.put("amount", "¥89.90");
        order1.put("status", "已完成");
        order1.put("orderTime", "2025-12-15 14:30");
        orders.add(order1);

        Map<String, Object> order2 = new HashMap<>();
        order2.put("orderId", "ORD202512002");
        order2.put("productName", "有机蔬菜礼盒");
        order2.put("quantity", 1);
        order2.put("amount", "¥128.00");
        order2.put("status", "配送中");
        order2.put("orderTime", "2025-12-16 09:15");
        orders.add(order2);

        Map<String, Object> order3 = new HashMap<>();
        order3.put("orderId", "ORD202512003");
        order3.put("productName", "新鲜鸡蛋 30枚装");
        order3.put("quantity", 3);
        order3.put("amount", "¥45.60");
        order3.put("status", "待发货");
        order3.put("orderTime", "2025-12-16 16:45");
        orders.add(order3);

        return ResponseEntity.ok(orders);
    }

    @RequestMapping("/findAllLeaders")
    public List<Leader> findAllLeaders() {
        return leaderService.findAllLeaders();
    }

    @RequestMapping("/findLeaderById")
    public Leader findLeaderById(Integer id) {
        return leaderService.findLeaderById(id);
    }

    @PostMapping("/insertLeader")
    public int insertLeader(@RequestBody Leader leader) {
        return leaderService.insertLeader(leader);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam(value = "username", required = false) String username,
                                   @RequestParam(value = "name", required = false) String name,
                                   @RequestParam("password") String password) {
        String userToCheck = username != null ? username : name;
        Leader leader = leaderService.authenticate(userToCheck, password);
        if (leader == null) {
            return ResponseEntity.status(401).body("用户名或密码错误");
        }
        // 可在此生成 JWT/Session，当前返回用户信息（测试用）
        return ResponseEntity.ok(leader);
    }
}
