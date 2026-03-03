package revista_backend.dto.user;

import java.time.LocalDate;
import lombok.Value;
import revista_backend.models.user.User;

@Value
public class UserFindResponse {

    Integer id;
    String names;
    String lastNames;
    LocalDate dateOfBirth;
    String photography;
    String description;
    Integer availableMoney;
    String userTypeName;
    String userStatusName;
    String sexTypeName;
    String municipalityName;

    public UserFindResponse(User user) {
        this.id = user.getId();
        this.names = user.getNames();
        this.lastNames = user.getLastNames();
        this.dateOfBirth = user.getDateOfBirth();
        this.photography = user.getPhotography();
        this.description = user.getDescription();
        this.availableMoney = user.getAvailableMoney();
        this.userTypeName = user.getUserType().getName();
        this.userStatusName = user.getUserStatus().getName();
        this.sexTypeName = user.getSexType().getName();
        this.municipalityName = user.getMunicipality().getName();
    }
    
}
