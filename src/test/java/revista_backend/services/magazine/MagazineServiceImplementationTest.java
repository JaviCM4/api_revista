package revista_backend.services.magazine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revista_backend.dto.magazine.request.MagazineCreateRequest;
import revista_backend.dto.magazine.request.MagazineUpdateCostRequest;
import revista_backend.dto.magazine.request.MagazineUpdatePermissionsRequest;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.ValidationException;
import revista_backend.exceptions.AccessDeniedException;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.magazine.MagazineSubscription;
import revista_backend.models.magazine.MagazineTag;
import revista_backend.models.user.User;
import revista_backend.repositories.categories.MagazineCategoryRepository;
import revista_backend.repositories.magazine.MagazineRepository;
import revista_backend.repositories.magazine.MagazineSubscriptionRepository;
import revista_backend.repositories.magazine.MagazineTagRepository;
import revista_backend.repositories.user.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MagazineServiceImplementationTest {

    @Mock
    private MagazineRepository magazineRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MagazineSubscriptionRepository magazineSubscriptionRepository;

    @Mock
    private MagazineTagRepository magazineTagRepository;

    @Mock
    private MagazineCategoryRepository magazineCategoryRepository;

    @InjectMocks
    private MagazineServiceImplementation service;

    @Test
    void testCreateSuccess() throws ResourceNotFoundException, ValidationException {
        // Arrange
        MagazineCreateRequest dto = new MagazineCreateRequest("T", "D", true, true, true, LocalDate.now());
        int userId = 2;
        User user = new User(); 
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(magazineRepository.save(any(Magazine.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Magazine created = service.create(dto, userId);

        // Assert
        verify(magazineRepository).save(any(Magazine.class));
        assertAll(
                () -> assertNotNull(created),
                () -> assertEquals(userId, created.getUser().getId())
        );
    }

    @Test
    void testCreateUserNotFound() {
        // Arrange
        MagazineCreateRequest dto = new MagazineCreateRequest("T", "D", true, true, true, LocalDate.now());
        int userId = 2;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> service.create(dto, userId));
        assertEquals("Tipo de usuario no encontrado", ex.getMessage());
    }

    @Test
    void testCreateValidationException() {
        // Arrange
        MagazineCreateRequest dto = new MagazineCreateRequest("T", "D", true, true, true, LocalDate.now().plusDays(1));
        int userId = 2;
        User user = new User(); 
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act & Assert
        ValidationException ex = assertThrows(ValidationException.class, () -> service.create(dto, userId));
        assertEquals("La fecha de creación debe ser menor o igual a hoy", ex.getMessage());
    }

    @Test
    void testUpdateCostMagazineSuccess() throws ResourceNotFoundException {
        // Arrange
        Magazine magazine = new Magazine(); 
        magazine.setId(1);
        when(magazineRepository.findById(1)).thenReturn(Optional.of(magazine));

        MagazineUpdateCostRequest dto = new MagazineUpdateCostRequest(1, 10, 5);

        // Act
        service.updateCostMagazine(dto);

        // Assert
        verify(magazineRepository).save(magazine);
        assertAll(
                () -> assertEquals(10, magazine.getDailyCost()),
                () -> assertEquals(5, magazine.getDailyCost())
        );
    }

    @Test
    void testUpdateCostMagazineNotFound() {
        // Arrange
        when(magazineRepository.findById(1)).thenReturn(Optional.empty());
        MagazineUpdateCostRequest dto = new MagazineUpdateCostRequest(1, 10, 5);

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> service.updateCostMagazine(dto));
        assertEquals("Revista no encontrada", ex.getMessage());
    }

    @Test
    void testUpdatePermissionsMagazineSuccess() throws ResourceNotFoundException, AccessDeniedException {
        // Arrange
        Magazine magazine = new Magazine(); 
        magazine.setId(1);
        User user = new User();
        user.setId(1);
        magazine.setUser(user);
        when(magazineRepository.findById(1)).thenReturn(Optional.of(magazine));

        MagazineUpdatePermissionsRequest dto = new MagazineUpdatePermissionsRequest(1, false, false, false);
        int userId = 1;

        // Act
        service.updatePermissionsMagazine(dto, userId);

        // Assert
        verify(magazineRepository).save(magazine);
        assertAll(
                () -> assertFalse(magazine.isAllowComments()),
                () -> assertFalse(magazine.isAllowReactions()),
                () -> assertFalse(magazine.isAllowSubscription())
        );
    }

    @Test
    void testFindAllNormal() {
        // Arrange
        Magazine magazine = new Magazine(); 
        magazine.setId(1);
        User user = new User(); 
        user.setNames("A"); 
        user.setLastNames("B");
        magazine.setUser(user);
        when(magazineRepository.findAll()).thenReturn(List.of(magazine));
        when(magazineTagRepository.findByMagazine_Id(1)).thenReturn(List.of());
        when(magazineCategoryRepository.findByMagazine_Id(1)).thenReturn(List.of());

        // Act
        var res = service.findAllNormal();

        // Assert
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(1, res.size()),
                () -> assertEquals("A B", res.get(0).getAuthor())
        );
    }

    @Test
    void testFindAllSubscriber() {
        // Arrange
        Magazine magazine = new Magazine(); 
        magazine.setId(1);
        User user = new User(); 
        user.setNames("A"); 
        user.setLastNames("B");
        magazine.setUser(user);
        MagazineSubscription sub = new MagazineSubscription(); 
        sub.setMagazine(magazine);
        when(magazineSubscriptionRepository.findByUser_Id(2)).thenReturn(List.of(sub));
        when(magazineTagRepository.findByMagazine_Id(1)).thenReturn(List.of());
        when(magazineCategoryRepository.findByMagazine_Id(1)).thenReturn(List.of());

        // Act
        var res = service.findAllSubscriber(2);

        // Assert
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(1, res.size()),
                () -> assertEquals("A B", res.get(0).getAuthor())
        );
    }

    @Test
    void testFindAllTag() {
        // Arrange
        MagazineTag tag = new MagazineTag();
        Magazine mag = new Magazine(); 
        mag.setId(1);
        User user = new User(); 
        user.setNames("A"); 
        user.setLastNames("B");
        mag.setUser(user);
        tag.setMagazine(mag);
        when(magazineTagRepository.findByDetailIgnoreCase("tag")).thenReturn(List.of(tag));
        when(magazineTagRepository.findByMagazine_Id(1)).thenReturn(List.of(tag));
        when(magazineCategoryRepository.findByMagazine_Id(1)).thenReturn(List.of());

        // Act
        var res = service.findAllTag("tag");

        // Assert
        assertAll(
                () -> assertNotNull(res),
                () -> assertTrue(res.size() > 0),
                () -> assertEquals("A B", res.get(0).getAuthor())
        );
    }
}
