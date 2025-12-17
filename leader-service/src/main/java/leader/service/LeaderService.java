package leader.service;

import leader.pojo.Leader;

import java.util.List;

public interface LeaderService {
    List<Leader> findAllLeaders();
    Leader findLeaderById(Integer id);
    int insertLeader(Leader leader);
    Leader authenticate(String username, String password);
    
    // 添加统计信息服务接口
    int countOrdersByLeaderId(Long leaderId);
    int countGroupsByLeaderId(Long leaderId);
    int countAllProducts();
}