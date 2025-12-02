package leader.service;

import leader.pojo.Leader;

import java.util.List;

public interface LeaderService {
    List<Leader> findAllLeaders();
    Leader findLeaderById(Integer id);
    int insertLeader(Leader leader);
}