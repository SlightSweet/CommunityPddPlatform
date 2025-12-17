package leader.mapper;

import leader.pojo.Leader;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LeaderMapper {
    @Select("select * from leader_info where id=#{id}")
    Leader findLeaderById(Integer id);

    @Select("select * from leader_info")
    List<Leader> findAllLeaders();

    @Insert("insert into leader_info(name,password) values(#{name},#{password})")
    int insertLeader(Leader leader);

    @Select("SELECT * FROM leader_info WHERE name = #{username} AND password = #{password}")
    Leader findByUsernameAndPassword(String username, String password);

    @Select("SELECT * FROM leader_info WHERE name = #{username}")
    Leader findByUsername(String username);
    
    // 添加统计信息查询方法
    @Select("SELECT COUNT(*) FROM group_order WHERE leader_id = #{leaderId}")
    int countOrdersByLeaderId(Long leaderId);
    
    @Select("SELECT COUNT(*) FROM group_activity WHERE leader_id = #{leaderId}")
    int countGroupsByLeaderId(Long leaderId);
    
    @Select("SELECT COUNT(*) FROM product")
    int countAllProducts();
}
