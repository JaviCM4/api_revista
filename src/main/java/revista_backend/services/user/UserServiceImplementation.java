package revista_backend.services.user;

import java.lang.module.ResolutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import revista_backend.dto.user.UserCreateRequest;
import revista_backend.dto.user.UserFindResponse;
import revista_backend.dto.user.UserUpdateRequest;
import revista_backend.dto.user.UserUpdateResponse;
import revista_backend.exceptions.ResourceNotFoundException;
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
        UserType userType = userTypeRepository.findById(2)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuario no encontrado"));

        UserStatus userStatus = userStatusRepository.findById(dto.getUserStatus())
                .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado"));

        SexType sexType = sexTypeRepository.findById(dto.getSexType())
                .orElseThrow(() -> new ResourceNotFoundException("Sexo no encontrado"));

        Municipality municipality = municipalityRepository.findById(dto.getMunicipioId())
                .orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado"));

        User newUser = dto.createEntity(userType, userStatus, sexType, municipality);

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
    public UserFindResponse findById(Integer id) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return new UserFindResponse(user);
    }

    @Override
    public UserUpdateResponse update(Integer id, UserUpdateRequest dto) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        user.updateFrom(dto);
        userRepository.save(user);
        return new UserUpdateResponse(user);
    }

    @Override
    public void deleteById(Integer id) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        UserStatus userStatus = userStatusRepository.findById(4)
                .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado"));
        
        user.setUserStatus(userStatus);
        userRepository.save(user);
    }
}

