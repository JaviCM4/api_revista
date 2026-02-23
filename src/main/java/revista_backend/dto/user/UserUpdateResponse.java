package revista_backend.dto.user;

import lombok.Value;
import revista_backend.models.user.User;

import java.time.LocalDate;

@Value
public class UserUpdateResponse {

    String names;
    String lastNames;

    public UserUpdateResponse(User user) {
        this.names = user.getNames();
        this.lastNames = user.getLastNames();
    }

}
