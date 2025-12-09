package portal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portal.pojo.Leader;
import portal.service.UserService;
import portal.client.LeaderServiceClient;

import java.util.Map;

@RestController
@RequestMapping("/")
public class PortalController {

    private final UserService userService;
    private final LeaderServiceClient leaderServiceClient;

    public PortalController(UserService userService, LeaderServiceClient leaderServiceClient) {
        this.userService = userService;
        this.leaderServiceClient = leaderServiceClient;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role) {
        return userService.login(username, password, role);
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> requestData) {
        String username = requestData.get("username");
        String password = requestData.get("password");
        String role = requestData.get("role");

        if (role.equals("leader")) {
            Leader leader = new Leader();
            leader.setName(username);
            leader.setPassword(password);
            return ResponseEntity.ok(leaderServiceClient.insertLeader(leader));
        } else if ("member".equals(role)) {
            // 团员注册逻辑
            return ResponseEntity.ok("Member registration not implemented yet");
        } else {
            return ResponseEntity.badRequest().body("Invalid role");
        }
    }
}
