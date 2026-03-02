package revista_backend.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import revista_backend.services.user.UserServiceImplementation;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplementationTest {

    private static final int USER_TYPE_ID = 2;
    private static final int USER_STATUS_ID = 1;
    private static final int SEX_TYPE_ID = 1;
    private static final int MUNICIPALITY_ID = 1;
    private static final int USER_ID = 1;
    private static final int DELETE_STATUS_ID = 4;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTypeRepository userTypeRepository;

    @Mock
    private UserStatusRepository userStatusRepository;

    @Mock
    private SexTypeRepository sexTypeRepository;

    @Mock
    private MunicipalityRepository municipalityRepository;

    @InjectMocks
    private UserServiceImplementation userService;

    @Test
    void testCreateUser() throws ResourceNotFoundException {
        // Arrange
        UserCreateRequest userCreateRequest = createUserRequest();

        UserType userType = new UserType();
        userType.setId(USER_TYPE_ID);

        UserStatus userStatus = new UserStatus();
        userStatus.setId(USER_STATUS_ID);

        SexType sexType = new SexType();
        sexType.setId(SEX_TYPE_ID);

        Municipality municipality = new Municipality();
        municipality.setId(MUNICIPALITY_ID);

        User expectedUser = new User();
        expectedUser.setId(USER_ID);

        when(userTypeRepository.findById(USER_TYPE_ID))
                .thenReturn(Optional.of(userType));

        when(userStatusRepository.findById(USER_STATUS_ID))
                .thenReturn(Optional.of(userStatus));

        when(sexTypeRepository.findById(SEX_TYPE_ID))
                .thenReturn(Optional.of(sexType));

        when(municipalityRepository.findById(MUNICIPALITY_ID))
                .thenReturn(Optional.of(municipality));

        when(userRepository.save(any(User.class)))
                .thenReturn(expectedUser);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        // Act
        User result = userService.create(userCreateRequest);

        // Assert
        verify(userTypeRepository).findById(USER_TYPE_ID);
        verify(userStatusRepository).findById(USER_STATUS_ID);
        verify(sexTypeRepository).findById(SEX_TYPE_ID);
        verify(municipalityRepository).findById(MUNICIPALITY_ID);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(USER_ID, result.getId()),
                () -> assertEquals(userCreateRequest.getNames(), savedUser.getNames()),
                () -> assertEquals(userCreateRequest.getLastNames(), savedUser.getLastNames()),
                () -> assertEquals(userCreateRequest.getDateOfBirth(), savedUser.getDateOfBirth()),
                () -> assertEquals(userCreateRequest.getPhotography(), savedUser.getPhotography()),
                () -> assertEquals(userCreateRequest.getDescription(), savedUser.getDescription()),
                () -> assertEquals(userType, savedUser.getUserType()),
                () -> assertEquals(userStatus, savedUser.getUserStatus()),
                () -> assertEquals(sexType, savedUser.getSexType()),
                () -> assertEquals(municipality, savedUser.getMunicipality())
        );
    }

    @Test
    void testCreateUserWhenUserTypeNotFound() {
        // Arrange
        UserCreateRequest userCreateRequest = createUserRequest();
        when(userTypeRepository.findById(USER_TYPE_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> userService.create(userCreateRequest));
        
        assertEquals("Tipo de usuario no encontrado", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
        verify(userStatusRepository, never()).findById(anyInt());
        verify(sexTypeRepository, never()).findById(anyInt());
        verify(municipalityRepository, never()).findById(anyInt());
    }

    @Test
    void testCreateUserWhenUserStatusNotFound() {
        // Arrange
        UserCreateRequest userCreateRequest = createUserRequest();
        UserType userType = new UserType();
        
        when(userTypeRepository.findById(USER_TYPE_ID)).thenReturn(Optional.of(userType));
        when(userStatusRepository.findById(USER_STATUS_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> userService.create(userCreateRequest));
        
        assertEquals("Estado no encontrado", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
        verify(sexTypeRepository, never()).findById(anyInt());
        verify(municipalityRepository, never()).findById(anyInt());
    }

    @Test
    void testCreateUserWhenSexTypeNotFound() {
        // Arrange
        UserCreateRequest userCreateRequest = createUserRequest();
        UserType userType = new UserType();
        UserStatus userStatus = new UserStatus();
        
        when(userTypeRepository.findById(USER_TYPE_ID)).thenReturn(Optional.of(userType));
        when(userStatusRepository.findById(USER_STATUS_ID)).thenReturn(Optional.of(userStatus));
        when(sexTypeRepository.findById(SEX_TYPE_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> userService.create(userCreateRequest));
        
        assertEquals("Sexo no encontrado", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
        verify(municipalityRepository, never()).findById(anyInt());
    }

    @Test
    void testCreateUserWhenMunicipalityNotFound() {
        // Arrange
        UserCreateRequest userCreateRequest = createUserRequest();
        UserType userType = new UserType();
        UserStatus userStatus = new UserStatus();
        SexType sexType = new SexType();
        
        when(userTypeRepository.findById(USER_TYPE_ID)).thenReturn(Optional.of(userType));
        when(userStatusRepository.findById(USER_STATUS_ID)).thenReturn(Optional.of(userStatus));
        when(sexTypeRepository.findById(SEX_TYPE_ID)).thenReturn(Optional.of(sexType));
        when(municipalityRepository.findById(MUNICIPALITY_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> userService.create(userCreateRequest));
        
        assertEquals("Municipio no encontrado", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    void testFindAll() {
        // Arrange
        User user1 = createUser(1);
        User user2 = createUser(2);
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<UserFindResponse> result = userService.findAll();

        // Assert
        verify(userRepository).findAll();
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertEquals("prueba", result.get(0).getNames()),
                () -> assertEquals("prueba", result.get(1).getNames())
        );
    }

    @Test
    void testFindAllWhenEmpty() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<UserFindResponse> result = userService.findAll();

        // Assert
        verify(userRepository).findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    void testFindById() throws ResourceNotFoundException {
        // Arrange
        User user = createUser(USER_ID);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        // Act
        UserFindResponse result = userService.findById(USER_ID);

        // Assert
        verify(userRepository).findById(USER_ID);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("prueba", result.getNames()),
                () -> assertEquals("prueba", result.getLastNames())
        );
    }

    @Test
    void testFindByIdWhenNotFound() {
        // Arrange
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> userService.findById(USER_ID));
        
        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(userRepository).findById(USER_ID);
    }

    @Test
    void testUpdate() throws ResourceNotFoundException {
        // Arrange
        User existingUser = createUser(USER_ID);
        UserUpdateRequest updateRequest = new UserUpdateRequest("Nombre", "Apellido", LocalDate.of(1990, 1, 1), "foto.png", "-----");
        
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        UserUpdateResponse result = userService.update(USER_ID, updateRequest);

        // Assert
        verify(userRepository).findById(USER_ID);
        verify(userRepository).save(existingUser);
        assertNotNull(result);
    }

    @Test
    void testUpdateWhenUserNotFound() {
        // Arrange
        UserUpdateRequest updateRequest = new UserUpdateRequest("Nombre", "Apellido", LocalDate.of(1990, 1, 1), "foto.png", "-----");
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> userService.update(USER_ID, updateRequest));
        
        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(userRepository).findById(USER_ID);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteById() throws ResourceNotFoundException {
        // Arrange
        User user = createUser(USER_ID);
        UserStatus deleteStatus = new UserStatus();
        deleteStatus.setId(DELETE_STATUS_ID);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userStatusRepository.findById(DELETE_STATUS_ID)).thenReturn(Optional.of(deleteStatus));
        when(userRepository.save(any(User.class))).thenReturn(user);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        // Act
        userService.deleteById(USER_ID);

        // Assert
        verify(userRepository).findById(USER_ID);
        verify(userStatusRepository).findById(DELETE_STATUS_ID);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals(deleteStatus, savedUser.getUserStatus());
    }

    @Test
    void testDeleteByIdWhenUserNotFound() {
        // Arrange
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> userService.deleteById(USER_ID));
        
        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(userRepository).findById(USER_ID);
        verify(userStatusRepository, never()).findById(anyInt());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteByIdWhenStatusNotFound() {
        // Arrange
        User user = createUser(USER_ID);
        
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userStatusRepository.findById(DELETE_STATUS_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> userService.deleteById(USER_ID));
        
        assertEquals("Estado no encontrado", exception.getMessage());
        verify(userRepository).findById(USER_ID);
        verify(userStatusRepository).findById(DELETE_STATUS_ID);
        verify(userRepository, never()).save(any(User.class));
    }

    private UserCreateRequest createUserRequest() {
        return new UserCreateRequest(
                USER_STATUS_ID,
                SEX_TYPE_ID,
                MUNICIPALITY_ID,
                "prueba",
                "prueba",
                "prueba",
                LocalDate.of(2002, 12, 15),
                "------",
                "------",
                "------"
        );
    }

    private User createUser(Integer id) {
        User user = new User();
        user.setId(id);
        user.setNames("prueba");
        user.setLastNames("prueba");
        user.setDateOfBirth(LocalDate.of(2002, 12, 15));
        user.setPhotography("photo.jpg");
        user.setDescription("Description");
        
        UserType userType = new UserType();
        userType.setId(USER_TYPE_ID);
        user.setUserType(userType);
        
        UserStatus userStatus = new UserStatus();
        userStatus.setId(USER_STATUS_ID);
        user.setUserStatus(userStatus);
        
        SexType sexType = new SexType();
        sexType.setId(SEX_TYPE_ID);
        user.setSexType(sexType);
        
        Municipality municipality = new Municipality();
        municipality.setId(MUNICIPALITY_ID);
        user.setMunicipality(municipality);
       
        return user;
    }
}