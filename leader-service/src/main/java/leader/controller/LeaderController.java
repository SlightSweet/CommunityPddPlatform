package leader.controller;

import leader.pojo.Leader;
import leader.service.LeaderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leader")
public class LeaderController {
    private final LeaderService leaderService;

    public LeaderController(LeaderService leaderService) {
        this.leaderService = leaderService;
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
