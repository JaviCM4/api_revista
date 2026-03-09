package revista_backend.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import revista_backend.dto.user.*;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.ValidationException;
import revista_backend.models.location.Municipality;
import revista_backend.models.status.UserStatus;
import revista_backend.models.types.SexType;
import revista_backend.models.types.UserType;
import revista_backend.models.user.User;
import revista_backend.repositories.location.MunicipalityRepository;
import revista_backend.repositories.status.UserStatusRepository;
import revista_backend.repositories.types.SexTypeRepository;
import revista_backend.repositories.types.UserTypeRepository;
import revista_backend.repositories.user.UserRepository;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final SexTypeRepository sexTypeRepository;
    private final UserTypeRepository userTypeRepository;
    private final UserStatusRepository userStatusRepository;
    private final MunicipalityRepository municipalityRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, SexTypeRepository sexTypeRepository, UserTypeRepository userTypeRepository, UserStatusRepository userStatusRepository, MunicipalityRepository municipalityRepository) {
        this.userRepository = userRepository;
        this.sexTypeRepository = sexTypeRepository;
        this.userTypeRepository = userTypeRepository;
        this.userStatusRepository = userStatusRepository;
        this.municipalityRepository = municipalityRepository;
    }

    @Override
    public User create(UserCreateRequest dto) throws ResourceNotFoundException {
        User newUser = buildUser(dto, 2, dto.getUserStatus(), dto.getSexType(), dto.getMunicipioId());
        return userRepository.save(newUser);
    }

    @Override
    public User createAdmin(UserCreateAdminRequest dto) throws ResourceNotFoundException {
        User newUser = buildUser(dto, dto.getIdUserType(), dto.getUserStatus(), dto.getSexType(), dto.getMunicipioId());
        return userRepository.save(newUser);
    }

    @Override
    public List<UserFindResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserFindResponse::new)
                .toList();
    }

    @Override
    public UserFindResponse findById(Integer idUser) throws ResourceNotFoundException {
        User user = userRepository.findById(idUser)
                                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return new UserFindResponse(user);
    }

    @Override
    public UserUpdateResponse update(UserUpdateRequest dto, Integer idUser)
            throws ResourceNotFoundException {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        user.updateEntity(dto);
        userRepository.save(user);
        return new UserUpdateResponse(user);
    }

    @Override
    public void suspendUser(Integer idUser) throws ResourceNotFoundException {
        User user = userRepository.findById(idUser)
                                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        UserStatus userStatus = userStatusRepository.findById(2)
                .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado"));

        user.setUserStatus(userStatus);
        userRepository.save(user);
    }

    @Override
    public void updateMoney(Integer money, Integer idUser)
            throws ResourceNotFoundException, ValidationException {
        if (money == null || money < 0) {
            throw new ValidationException("The amount must be a positive integer.");
        }

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        user.setAvailableMoney(user.getAvailableMoney() + money);
        userRepository.save(user);
    }

    @Override
    public void deleteById(Integer idUser) throws ResourceNotFoundException {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        UserStatus deleteStatus = userStatusRepository.findById(4)
                .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado"));

        user.setUserStatus(deleteStatus);
        userRepository.save(user);
    }

    private User buildUser(Object dto, Integer userTypeId, Integer userStatusId, Integer sexTypeId, Integer municipioId) throws ResourceNotFoundException {
        UserType userType        = userTypeRepository.findById(userTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuario no encontrado"));
        UserStatus userStatus    = userStatusRepository.findById(userStatusId)
                .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado"));
        SexType sexType          = sexTypeRepository.findById(sexTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Sexo no encontrado"));
        Municipality municipality = municipalityRepository.findById(municipioId)
                .orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado"));

        return dto instanceof UserCreateRequest r
                ? r.createEntity(userType, userStatus, sexType, municipality)
                : ((UserCreateAdminRequest) dto).createEntity(userType, userStatus, sexType, municipality);
    }
}
