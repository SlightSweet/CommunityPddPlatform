package leader.service.impl;

import leader.mapper.LeaderMapper;
import leader.pojo.Leader;
import leader.service.LeaderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderServiceImpl implements LeaderService {

    private final LeaderMapper leaderMapper;

    public LeaderServiceImpl(LeaderMapper leaderMapper) {
        this.leaderMapper = leaderMapper;
    }

    @Override
    public List<Leader> findAllLeaders() {
        return leaderMapper.findAllLeaders();
    }

    @Override
    public Leader findLeaderById(Integer id) {
        return leaderMapper.findLeaderById(id);
    }

    @Override
    public int insertLeader(Leader leader) {
        return leaderMapper.insertLeader(leader);
    }

    @Override
    public Leader authenticate(String username, String password) {
        // 简单示例：明文验证。生产请用哈希（BCrypt）并且不要直接比对明文
        return leaderMapper.findByUsernameAndPassword(username, password);
    }
}
