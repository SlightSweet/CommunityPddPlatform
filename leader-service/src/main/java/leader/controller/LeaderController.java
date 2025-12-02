package leader.controller;

import leader.service.LeaderService;
import leader.pojo.Leader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leader")
public class LeaderController {
    private final LeaderService leaderService;

    // 构造器注入（推荐）
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
}
