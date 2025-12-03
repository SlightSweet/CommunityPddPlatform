package portal.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import portal.client.LeaderServiceClient;
import portal.client.MemberServiceClient;

@Service
public class UserService {

    private final LeaderServiceClient leaderServiceClient;
    private final MemberServiceClient memberServiceClient;

    public UserService(LeaderServiceClient leaderServiceClient, MemberServiceClient memberServiceClient) {
        this.leaderServiceClient = leaderServiceClient;
        this.memberServiceClient = memberServiceClient;
    }

    public ResponseEntity<?> login(String username, String password, String role) {
        if ("leader".equals(role)) {
            return leaderServiceClient.login(username, password);
        } else if ("member".equals(role)) {
            return memberServiceClient.login(username, password);
        } else {
            return ResponseEntity.badRequest().body("Invalid role");
        }
    }
}
