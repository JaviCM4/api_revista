package revista_backend.services.magazine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MagazineServiceImplementationTest {

    private static final int MAGAZINE_ID = 1;
    private static final int USER_ID = 2;
    private static final int DAILY_COST = 10;
    private static final int WEEKLY_COST = 5;
    private static final String TITLE = "T";
    private static final String DESCRIPTION = "D";
    private static final String AUTHOR = "A B";
    private static final String TAG_DETAIL = "tag";
    private static final String USER_NAME = "A";
    private static final String USER_LAST_NAME = "B";

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
        MagazineCreateRequest dto = createMagazineRequest();
        User user = createUser(USER_ID, USER_NAME, USER_LAST_NAME);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(magazineRepository.save(any(Magazine.class))).thenAnswer(i -> i.getArgument(0));

        ArgumentCaptor<Magazine> magazineCaptor = ArgumentCaptor.forClass(Magazine.class);

        // Act
        Magazine created = service.create(dto, USER_ID);

        // Assert
        verify(userRepository).findById(USER_ID);
        verify(magazineRepository).save(magazineCaptor.capture());

        Magazine savedMagazine = magazineCaptor.getValue();

        assertAll(
                () -> assertNotNull(created),
                () -> assertEquals(USER_ID, savedMagazine.getUser().getId())
        );
    }

    @Test
    void testCreateUserNotFound() {
        // Arrange
        MagazineCreateRequest dto = createMagazineRequest();
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, 
                () -> service.create(dto, USER_ID));
        assertEquals("Type User not found", ex.getMessage());
        verify(magazineRepository, never()).save(any());
    }

    @Test
    void testCreateWithValidDate() throws ResourceNotFoundException, ValidationException {
        // Arrange
        MagazineCreateRequest dto = new MagazineCreateRequest(TITLE, DESCRIPTION, true, true, true, LocalDate.now());
        User user = createUser(USER_ID, USER_NAME, USER_LAST_NAME);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(magazineRepository.save(any(Magazine.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Magazine result = service.create(dto, USER_ID);

        // Assert
        verify(userRepository).findById(USER_ID);
        verify(magazineRepository).save(any(Magazine.class));
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(TITLE, result.getTitle()),
                () -> assertEquals(user, result.getUser())
        );
    }

    @Test
    void testUpdateCostMagazineSuccess() throws ResourceNotFoundException {
        // Arrange
        Magazine magazine = createMagazine(MAGAZINE_ID);
        MagazineUpdateCostRequest dto = createMagazineUpdateCostRequest();

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(magazineRepository.save(any(Magazine.class))).thenReturn(magazine);

        ArgumentCaptor<Magazine> magazineCaptor = ArgumentCaptor.forClass(Magazine.class);

        // Act
        service.updateCostMagazine(dto);

        // Assert
        verify(magazineRepository).findById(MAGAZINE_ID);
        verify(magazineRepository).save(magazineCaptor.capture());

        Magazine savedMagazine = magazineCaptor.getValue();

        assertAll(
                () -> assertEquals(DAILY_COST, savedMagazine.getDailyCost())
        );
    }

    @Test
    void testUpdateCostMagazineNotFound() {
        // Arrange
        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.empty());
        MagazineUpdateCostRequest dto = createMagazineUpdateCostRequest();

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, 
                () -> service.updateCostMagazine(dto));
        assertEquals("Magazine not found", ex.getMessage());
        verify(magazineRepository, never()).save(any());
    }

    @Test
    void testUpdatePermissionsMagazineSuccess() throws ResourceNotFoundException, AccessDeniedException {
        // Arrange
        Magazine magazine = createMagazine(MAGAZINE_ID);
        magazine.setUser(createUser(USER_ID, USER_NAME, USER_LAST_NAME));
        MagazineUpdatePermissionsRequest dto = createMagazineUpdatePermissionsRequest();

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(magazineRepository.save(any(Magazine.class))).thenReturn(magazine);

        ArgumentCaptor<Magazine> magazineCaptor = ArgumentCaptor.forClass(Magazine.class);

        // Act
        service.updatePermissionsMagazine(dto, USER_ID);

        // Assert
        verify(magazineRepository).findById(MAGAZINE_ID);
        verify(magazineRepository).save(magazineCaptor.capture());

        Magazine savedMagazine = magazineCaptor.getValue();

        assertAll(
                () -> assertFalse(savedMagazine.isAllowComments()),
                () -> assertFalse(savedMagazine.isAllowReactions()),
                () -> assertFalse(savedMagazine.isAllowSubscription())
        );
    }

    @Test
    void testFindAllNormal() {
        // Arrange
        Magazine magazine = createMagazine(MAGAZINE_ID);
        magazine.setUser(createUser(USER_ID, USER_NAME, USER_LAST_NAME));
        List<Magazine> magazines = Arrays.asList(magazine);

        when(magazineRepository.findByActiveMagazine(true)).thenReturn(magazines);
        when(magazineTagRepository.findByMagazine_Id(MAGAZINE_ID)).thenReturn(Arrays.asList());
        when(magazineCategoryRepository.findByMagazine_Id(MAGAZINE_ID)).thenReturn(Arrays.asList());

        // Act
        var res = service.findAllNormal();

        // Assert
        verify(magazineRepository).findByActiveMagazine(true);
        verify(magazineTagRepository).findByMagazine_Id(MAGAZINE_ID);
        verify(magazineCategoryRepository).findByMagazine_Id(MAGAZINE_ID);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(1, res.size()),
                () -> assertEquals(AUTHOR, res.get(0).getAuthor())
        );
    }

    @Test
    void testFindAllSubscriber() {
        // Arrange
        Magazine magazine = createMagazine(MAGAZINE_ID);
        magazine.setUser(createUser(USER_ID, USER_NAME, USER_LAST_NAME));
        MagazineSubscription subscription = new MagazineSubscription();
        subscription.setMagazine(magazine);
        List<MagazineSubscription> subscriptions = Arrays.asList(subscription);

        when(magazineSubscriptionRepository.findByUser_Id(USER_ID)).thenReturn(subscriptions);
        when(magazineTagRepository.findByMagazine_Id(MAGAZINE_ID)).thenReturn(Arrays.asList());
        when(magazineCategoryRepository.findByMagazine_Id(MAGAZINE_ID)).thenReturn(Arrays.asList());

        // Act
        var res = service.findAllSubscriber(USER_ID);

        // Assert
        verify(magazineSubscriptionRepository).findByUser_Id(USER_ID);
        verify(magazineTagRepository).findByMagazine_Id(MAGAZINE_ID);
        verify(magazineCategoryRepository).findByMagazine_Id(MAGAZINE_ID);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(1, res.size()),
                () -> assertEquals(AUTHOR, res.get(0).getAuthor())
        );
    }

    @Test
    void testFindAllTag() {
        // Arrange
        Magazine magazine = createMagazine(MAGAZINE_ID);
        magazine.setUser(createUser(USER_ID, USER_NAME, USER_LAST_NAME));
        MagazineTag tag = new MagazineTag();
        tag.setMagazine(magazine);
        List<MagazineTag> tags = Arrays.asList(tag);

        when(magazineTagRepository.findByDetailIgnoreCase(TAG_DETAIL)).thenReturn(tags);
        when(magazineTagRepository.findByMagazine_Id(MAGAZINE_ID)).thenReturn(tags);
        when(magazineCategoryRepository.findByMagazine_Id(MAGAZINE_ID)).thenReturn(Arrays.asList());

        // Act
        var res = service.findAllTag(TAG_DETAIL);

        // Assert
        verify(magazineTagRepository).findByDetailIgnoreCase(TAG_DETAIL);
        verify(magazineTagRepository).findByMagazine_Id(MAGAZINE_ID);
        verify(magazineCategoryRepository).findByMagazine_Id(MAGAZINE_ID);
        assertAll(
                () -> assertNotNull(res),
                () -> assertTrue(res.size() > 0),
                () -> assertEquals(AUTHOR, res.get(0).getAuthor())
        );
    }

    private MagazineCreateRequest createMagazineRequest() {
        return new MagazineCreateRequest(TITLE, DESCRIPTION, true, true, true, LocalDate.now());
    }

    private MagazineUpdateCostRequest createMagazineUpdateCostRequest() {
        return new MagazineUpdateCostRequest(MAGAZINE_ID, DAILY_COST, WEEKLY_COST);
    }

    private MagazineUpdatePermissionsRequest createMagazineUpdatePermissionsRequest() {
        return new MagazineUpdatePermissionsRequest(MAGAZINE_ID, false, false, false);
    }

    private Magazine createMagazine(Integer id) {
        Magazine magazine = new Magazine();
        magazine.setId(id);
        return magazine;
    }

    private User createUser(Integer id, String names, String lastNames) {
        User user = new User();
        user.setId(id);
        user.setNames(names);
        user.setLastNames(lastNames);
        return user;
    }
}
