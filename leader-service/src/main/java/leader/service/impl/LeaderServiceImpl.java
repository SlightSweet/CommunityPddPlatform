package leader.service.impl;

import leader.service.LeaderService;
import leader.pojo.Leader;
import leader.mapper.LeaderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LeaderServiceImpl implements LeaderService {

    @Autowired
    private LeaderMapper leaderMapper;

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

}
