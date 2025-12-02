package mapper;

import org.apache.ibatis.annotations.Mapper;
import pojo.Leader;

import java.util.List;

@Mapper
public interface LeaderMapper {
    Leader findLeaderById(Integer id);
    int insertLeader(Leader leader);
    List<Leader> findAllLeaders();
}
