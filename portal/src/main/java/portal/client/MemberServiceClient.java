package portal.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import portal.pojo.Leader;  // 假设你有 Leader 类

@FeignClient(name = "member-service")  // 调用 member-service 服务，Feign 会自动创建实现
public interface MemberServiceClient {
    @GetMapping("/member/login")
    ResponseEntity<Leader> login(@RequestParam("username") String username, @RequestParam("password") String password);
}
