package portal.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portal.pojo.Leader;

import java.util.List;

@FeignClient(name = "leader-service", url = "${leader.service.url:http://localhost:8081}")
public interface LeaderServiceClient {

    @PostMapping("/leader/login")
    ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password);

    @GetMapping("/leader/findAllLeaders")
    List<Leader> findAllLeaders();

    @GetMapping("/leader/findLeaderById")
    Leader findLeaderById(@RequestParam("id") Integer id);

    @PostMapping("/leader/insertLeader")
    int insertLeader(@RequestBody Leader leader);

    @PostMapping("/leader/register")
    int registerLeader(@RequestBody Leader leader);

}
