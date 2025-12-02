package leader.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import leader.pojo.Leader;

import java.util.List;

@Mapper
public interface LeaderMapper {
//    Leader findLeaderById(Integer id);
//    int insertLeader(Leader leader);
//    List<Leader> findAllLeaders();

    @Select("select * from leader_info where id=#{id}")
    Leader findLeaderById(Integer id);

    @Select("select * from leader_info")
    List<Leader> findAllLeaders();

    @Select("insert into leader_info(name,phone,community) values(#{name},#{phone},#{community})")
    int insertLeader(Leader leader);
}
