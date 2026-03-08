package revista_backend.services.magazine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revista_backend.dto.magazine.request.SubscriptionCreateRequest;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.RestrictedException;
import revista_backend.exceptions.ValidationException;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.magazine.MagazineSubscription;
import revista_backend.models.user.User;
import revista_backend.repositories.magazine.MagazineRepository;
import revista_backend.repositories.magazine.MagazineSubscriptionRepository;
import revista_backend.repositories.user.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MagazineSubscriptionServiceImplementationTest {

    private static final int MAGAZINE_ID = 1;
    private static final int USER_ID = 2;
    private static final int SUBSCRIPTION_ID = 1;

    @Mock
    private MagazineSubscriptionRepository magazineSubscriptionRepository;

    @Mock
    private MagazineRepository magazineRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MagazineSubscriptionServiceImplementation service;

    @Test
    void testCreateSuccess() throws Exception {
        // Arrange
        SubscriptionCreateRequest dto = createSubscriptionRequest(LocalDate.now());
        Magazine magazine = createMagazine(MAGAZINE_ID, true);
        User user = createUser(USER_ID);

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(magazineSubscriptionRepository.existsByMagazine_IdAndUser_Id(MAGAZINE_ID, USER_ID)).thenReturn(false);

        ArgumentCaptor<MagazineSubscription> subscriptionCaptor = ArgumentCaptor.forClass(MagazineSubscription.class);

        // Act
        service.create(dto, USER_ID);

        // Assert
        verify(magazineRepository).findById(MAGAZINE_ID);
        verify(userRepository).findById(USER_ID);
        verify(magazineSubscriptionRepository).existsByMagazine_IdAndUser_Id(MAGAZINE_ID, USER_ID);
        verify(magazineSubscriptionRepository).save(subscriptionCaptor.capture());

        MagazineSubscription savedSubscription = subscriptionCaptor.getValue();

        assertAll(
                () -> assertNotNull(savedSubscription),
                () -> assertEquals(magazine, savedSubscription.getMagazine()),
                () -> assertEquals(user, savedSubscription.getUser())
        );
    }

    @Test
    void testCreateMagazineNotFound() {
        // Arrange
        SubscriptionCreateRequest dto = createSubscriptionRequest(LocalDate.now());
        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, 
                () -> service.create(dto, USER_ID));
        assertEquals("Magazine not found", ex.getMessage());
        verify(magazineSubscriptionRepository, never()).save(any());
    }

    @Test
    void testCreateRestricted() {
        // Arrange
        SubscriptionCreateRequest dto = createSubscriptionRequest(LocalDate.now());
        Magazine magazine = createMagazine(MAGAZINE_ID, false);
        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));

        // Act & Assert
        RestrictedException ex = assertThrows(RestrictedException.class, 
                () -> service.create(dto, USER_ID));
        assertEquals("You are not eligible for subscriptions to this magazine", ex.getMessage());
        verify(magazineSubscriptionRepository, never()).save(any());
    }

    @Test
    void testCreateUserNotFound() {
        // Arrange
        SubscriptionCreateRequest dto = createSubscriptionRequest(LocalDate.now());
        Magazine magazine = createMagazine(MAGAZINE_ID, true);
        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, 
                () -> service.create(dto, USER_ID));
        assertEquals("User not found", ex.getMessage());
        verify(magazineSubscriptionRepository, never()).save(any());
    }

    @Test
    void testCreateValidationException() {
        // Arrange
        SubscriptionCreateRequest dto = createSubscriptionRequest(LocalDate.now().minusDays(1));
        Magazine magazine = createMagazineWithDate(MAGAZINE_ID, true, LocalDate.now().plusDays(1));
        User user = createUser(USER_ID);
        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        // Act & Assert
        ValidationException ex = assertThrows(ValidationException.class, 
                () -> service.create(dto, USER_ID));
        assertEquals("You cannot subscribe before the magazine exists.", ex.getMessage());
        verify(magazineSubscriptionRepository, never()).save(any());
    }

    @Test
    void testCreateConflict() {
        // Arrange
        SubscriptionCreateRequest dto = createSubscriptionRequest(LocalDate.now());
        Magazine magazine = createMagazine(MAGAZINE_ID, true);
        User user = createUser(USER_ID);
        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(magazineSubscriptionRepository.existsByMagazine_IdAndUser_Id(MAGAZINE_ID, USER_ID)).thenReturn(true);

        // Act & Assert
        assertThrows(ConflictException.class, () -> service.create(dto, USER_ID));
        verify(magazineSubscriptionRepository, never()).save(any());
    }

    @Test
    void testDeleteSuccess() throws ResourceNotFoundException {
        // Arrange
        MagazineSubscription subscription = new MagazineSubscription();
        when(magazineSubscriptionRepository.findByMagazine_IdAndUser_Id(MAGAZINE_ID, USER_ID))
                .thenReturn(Optional.of(subscription));

        // Act
        service.delete(MAGAZINE_ID, USER_ID);

        // Assert
        verify(magazineSubscriptionRepository).findByMagazine_IdAndUser_Id(MAGAZINE_ID, USER_ID);
        verify(magazineSubscriptionRepository).delete(subscription);
    }

    @Test
    void testDeleteNotFound() {
        // Arrange
        when(magazineSubscriptionRepository.findByMagazine_IdAndUser_Id(MAGAZINE_ID, USER_ID))
                .thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, 
                () -> service.delete(MAGAZINE_ID, USER_ID));
        assertEquals("Subscription not found", ex.getMessage());
        verify(magazineSubscriptionRepository, never()).delete(any());
    }

    private SubscriptionCreateRequest createSubscriptionRequest(LocalDate date) {
        return new SubscriptionCreateRequest(MAGAZINE_ID, date);
    }

    private Magazine createMagazine(Integer id, boolean allowSubscription) {
        Magazine magazine = new Magazine();
        magazine.setId(id);
        magazine.setAllowSubscription(allowSubscription);
        magazine.setCreationDate(LocalDate.now());
        return magazine;
    }

    private Magazine createMagazineWithDate(Integer id, boolean allowSubscription, LocalDate creationDate) {
        Magazine magazine = new Magazine();
        magazine.setId(id);
        magazine.setAllowSubscription(allowSubscription);
        magazine.setCreationDate(creationDate);
        return magazine;
    }

    private User createUser(Integer id) {
        User user = new User();
        user.setId(id);
        return user;
    }
}
