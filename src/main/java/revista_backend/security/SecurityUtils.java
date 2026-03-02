package revista_backend.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Extracts user id from the Authorization header of the request.
     * Throws RuntimeException if header is missing or token invalid.
     */
    public int getUserIdFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            return jwtUtil.extractUserId(token);
        }
        throw new RuntimeException("Authorization header not found");
    }
}