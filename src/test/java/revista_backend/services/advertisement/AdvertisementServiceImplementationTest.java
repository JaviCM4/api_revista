package revista_backend.services.advertisement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revista_backend.dto.advertisement.AdBlockCreateRequest;
import revista_backend.dto.advertisement.AdCreateRequest;
import revista_backend.dto.advertisement.AdFindResponse;
import revista_backend.exceptions.AccessDeniedException;
import revista_backend.exceptions.MoneyException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.ValidationException;
import revista_backend.models.advertisement.AdBlock;
import revista_backend.models.advertisement.Advertisement;
import revista_backend.models.advertisement.ExtraContent;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.status.AdLockStatus;
import revista_backend.models.status.AdStatus;
import revista_backend.models.types.AdType;
import revista_backend.models.user.User;
import revista_backend.repositories.advertisement.AdBlockRepository;
import revista_backend.repositories.advertisement.AdvertisementRepository;
import revista_backend.repositories.advertisement.ExtraContentRepository;
import revista_backend.repositories.magazine.MagazineRepository;
import revista_backend.repositories.status.AdLockStatusRepository;
import revista_backend.repositories.status.AdStatusRepository;
import revista_backend.repositories.types.AdTypeRepository;
import revista_backend.repositories.user.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdvertisementServiceImplementationTest {

    private static final int USER_ID = 1;
    private static final int AD_ID = 10;
    private static final int MAGAZINE_ID = 100;

    @Mock
    private AdvertisementRepository advertisementRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdTypeRepository adTypeRepository;

    @Mock
    private AdStatusRepository adStatusRepository;

    @Mock
    private ExtraContentRepository extraContentRepository;

    @Mock
    private MagazineRepository magazineRepository;

    @Mock
    private AdLockStatusRepository adLockStatusRepository;

    @Mock
    private AdBlockRepository adBlockRepository;

    @InjectMocks
    private AdvertisementServiceImplementation service;

    @Test
    void testCreateSuccess() throws ResourceNotFoundException, ValidationException, MoneyException {
        AdvertisementServiceImplementation spyService = spy(service);

        User user = createUser(USER_ID, 1000);
        AdType adType = createAdType(1, "Banner");
        AdStatus adStatus = createAdStatus(1, "Activo");
        Advertisement savedAd = createAdvertisement(AD_ID, user, adType, adStatus, 200, LocalDate.now().plusDays(10));

        AdCreateRequest dto = new AdCreateRequest(
                1,
                List.of("img-1.png", "img-2.png"),
                200,
                LocalDate.now().plusDays(10)
        );

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(adTypeRepository.findById(1)).thenReturn(Optional.of(adType));
        when(adStatusRepository.findById(1)).thenReturn(Optional.of(adStatus));
        when(advertisementRepository.save(any(Advertisement.class))).thenReturn(savedAd);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<ExtraContent> extraCaptor = ArgumentCaptor.forClass(ExtraContent.class);

        spyService.create(dto, USER_ID);

        verify(userRepository).save(userCaptor.capture());
        verify(advertisementRepository).save(any(Advertisement.class));
        verify(extraContentRepository, times(2)).save(extraCaptor.capture());

        User updatedUser = userCaptor.getValue();
        List<ExtraContent> extras = extraCaptor.getAllValues();

        assertAll(
                () -> assertEquals(800, updatedUser.getAvailableMoney()),
                () -> assertEquals("img-1.png", extras.get(0).getResource()),
                () -> assertEquals("img-2.png", extras.get(1).getResource()),
                () -> assertEquals(AD_ID, extras.get(0).getAdvertisement().getId())
        );
    }

    @Test
    void testCreateWhenMoneyIsInsufficient() {
        AdvertisementServiceImplementation spyService = spy(service);

        User user = createUser(USER_ID, 50);
        AdType adType = createAdType(1, "Banner");
        AdStatus adStatus = createAdStatus(1, "Activo");

        AdCreateRequest dto = new AdCreateRequest(
                1,
                List.of("img-1.png"),
                200,
                LocalDate.now().plusDays(10)
        );

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(adTypeRepository.findById(1)).thenReturn(Optional.of(adType));
        when(adStatusRepository.findById(1)).thenReturn(Optional.of(adStatus));

        MoneyException exception = assertThrows(MoneyException.class, () -> spyService.create(dto, USER_ID));

        assertEquals("Total Cost Exceeded", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
        verify(advertisementRepository, never()).save(any(Advertisement.class));
        verify(extraContentRepository, never()).save(any(ExtraContent.class));
    }

    @Test
    void testCreateWhenUserNotFound() {
        AdvertisementServiceImplementation spyService = spy(service);
        AdCreateRequest dto = new AdCreateRequest(1, List.of("img-1.png"), 200, LocalDate.now().plusDays(10));

        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> spyService.create(dto, USER_ID));

        assertEquals("User Not Found", exception.getMessage());
        verify(advertisementRepository, never()).save(any(Advertisement.class));
    }

    @Test
    void testCreateWhenAdTypeNotFound() {
        AdvertisementServiceImplementation spyService = spy(service);
        User user = createUser(USER_ID, 1000);
        AdCreateRequest dto = new AdCreateRequest(1, List.of("img-1.png"), 200, LocalDate.now().plusDays(10));

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(adTypeRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> spyService.create(dto, USER_ID));

        assertEquals("Type Advertisement Not Found", exception.getMessage());
        verify(advertisementRepository, never()).save(any(Advertisement.class));
    }

    @Test
    void testCreateWhenAdStatusNotFound() {
        AdvertisementServiceImplementation spyService = spy(service);
        User user = createUser(USER_ID, 1000);
        AdType adType = createAdType(1, "Banner");
        AdCreateRequest dto = new AdCreateRequest(1, List.of("img-1.png"), 200, LocalDate.now().plusDays(10));

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(adTypeRepository.findById(1)).thenReturn(Optional.of(adType));
        when(adStatusRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> spyService.create(dto, USER_ID));

        assertEquals("Status Advertisement Not Found", exception.getMessage());
        verify(advertisementRepository, never()).save(any(Advertisement.class));
    }

    @Test
    void testCreateWhenExpirationDateIsNotFuture() {
        AdvertisementServiceImplementation spyService = spy(service);

        User user = createUser(USER_ID, 1000);
        AdType adType = createAdType(1, "Banner");
        AdStatus adStatus = createAdStatus(1, "Activo");

        AdCreateRequest dto = new AdCreateRequest(
                1,
                List.of("img-1.png"),
                200,
                LocalDate.now()
        );

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(adTypeRepository.findById(1)).thenReturn(Optional.of(adType));
        when(adStatusRepository.findById(1)).thenReturn(Optional.of(adStatus));

        ValidationException exception = assertThrows(ValidationException.class, () -> spyService.create(dto, USER_ID));

        assertEquals("Expiration Date must be future", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
        verify(advertisementRepository, never()).save(any(Advertisement.class));
    }

    @Test
    void testCreateSuccessWithoutDetails() throws ResourceNotFoundException, ValidationException, MoneyException {
        AdvertisementServiceImplementation spyService = spy(service);

        User user = createUser(USER_ID, 1000);
        AdType adType = createAdType(1, "Banner");
        AdStatus adStatus = createAdStatus(1, "Activo");
        Advertisement savedAd = createAdvertisement(AD_ID, user, adType, adStatus, 200, LocalDate.now().plusDays(10));

        AdCreateRequest dto = new AdCreateRequest(
                1,
                List.of(),
                200,
                LocalDate.now().plusDays(10)
        );

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(adTypeRepository.findById(1)).thenReturn(Optional.of(adType));
        when(adStatusRepository.findById(1)).thenReturn(Optional.of(adStatus));
        when(advertisementRepository.save(any(Advertisement.class))).thenReturn(savedAd);

        spyService.create(dto, USER_ID);

        verify(userRepository).save(any(User.class));
        verify(advertisementRepository).save(any(Advertisement.class));
        verify(extraContentRepository, never()).save(any(ExtraContent.class));
    }

    @Test
    void testCreateSuccessWithNullDetails() throws ResourceNotFoundException, ValidationException, MoneyException {
        AdvertisementServiceImplementation spyService = spy(service);

        User user = createUser(USER_ID, 1000);
        AdType adType = createAdType(1, "Banner");
        AdStatus adStatus = createAdStatus(1, "Activo");
        Advertisement savedAd = createAdvertisement(AD_ID, user, adType, adStatus, 200, LocalDate.now().plusDays(10));

        AdCreateRequest dto = new AdCreateRequest(
                1,
                null,
                200,
                LocalDate.now().plusDays(10)
        );

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(adTypeRepository.findById(1)).thenReturn(Optional.of(adType));
        when(adStatusRepository.findById(1)).thenReturn(Optional.of(adStatus));
        when(advertisementRepository.save(any(Advertisement.class))).thenReturn(savedAd);

        spyService.create(dto, USER_ID);

        verify(userRepository).save(any(User.class));
        verify(advertisementRepository).save(any(Advertisement.class));
        verify(extraContentRepository, never()).save(any(ExtraContent.class));
    }

    @Test
    void testBlockAdSuccess() throws ResourceNotFoundException, AccessDeniedException, MoneyException, ValidationException {
        AdvertisementServiceImplementation spyService = spy(service);

        User owner = createUser(USER_ID, 500);
        Magazine magazine = createMagazine(MAGAZINE_ID, owner, 120);
        Advertisement advertisement = createAdvertisement(AD_ID, owner, createAdType(1, "Banner"), createAdStatus(1, "Activo"), 100, LocalDate.now().plusDays(5));
        AdLockStatus lockStatus = createAdLockStatus(1, "Activo");
        AdStatus blockedStatus = createAdStatus(2, "Bloqueado");

        AdBlockCreateRequest dto = new AdBlockCreateRequest(
                MAGAZINE_ID,
                AD_ID,
                120,
                LocalDate.now().plusDays(7)
        );

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(advertisementRepository.findById(AD_ID)).thenReturn(Optional.of(advertisement));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(owner));
        when(adLockStatusRepository.findById(1)).thenReturn(Optional.of(lockStatus));
        when(adStatusRepository.findById(2)).thenReturn(Optional.of(blockedStatus));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<Advertisement> adCaptor = ArgumentCaptor.forClass(Advertisement.class);
        ArgumentCaptor<AdBlock> adBlockCaptor = ArgumentCaptor.forClass(AdBlock.class);

        spyService.blockAd(dto, USER_ID);

        verify(userRepository).save(userCaptor.capture());
        verify(advertisementRepository).save(adCaptor.capture());
        verify(adBlockRepository).save(adBlockCaptor.capture());

        User updatedUser = userCaptor.getValue();
        Advertisement blockedAd = adCaptor.getValue();
        AdBlock savedBlock = adBlockCaptor.getValue();

        assertAll(
                () -> assertEquals(380, updatedUser.getAvailableMoney()),
                () -> assertEquals(blockedStatus, blockedAd.getAdStatus()),
                () -> assertEquals(120, savedBlock.getPayment()),
                () -> assertNotNull(savedBlock.getStartDate()),
                () -> assertEquals(dto.getExpirationDate(), savedBlock.getEndDate())
        );
    }

    @Test
    void testBlockAdWhenUserIsNotOwner() throws ResourceNotFoundException {
        AdvertisementServiceImplementation spyService = spy(service);

        User owner = createUser(999, 500);
        Magazine magazine = createMagazine(MAGAZINE_ID, owner, 120);
        Advertisement advertisement = createAdvertisement(AD_ID, owner, createAdType(1, "Banner"), createAdStatus(1, "Activo"), 100, LocalDate.now().plusDays(5));

        AdBlockCreateRequest dto = new AdBlockCreateRequest(
                MAGAZINE_ID,
                AD_ID,
                120,
                LocalDate.now().plusDays(7)
        );

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(advertisementRepository.findById(AD_ID)).thenReturn(Optional.of(advertisement));
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));

        AccessDeniedException exception = assertThrows(
                AccessDeniedException.class,
                () -> spyService.blockAd(dto, USER_ID)
        );

        assertEquals("User Not Found", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
        verify(advertisementRepository, never()).save(any(Advertisement.class));
        verify(adBlockRepository, never()).save(any(AdBlock.class));
    }

    @Test
    void testBlockAdWhenMoneyIsInsufficient() throws ResourceNotFoundException {
        AdvertisementServiceImplementation spyService = spy(service);

        User owner = createUser(USER_ID, 100);
        Magazine magazine = createMagazine(MAGAZINE_ID, owner, 120);
        Advertisement advertisement = createAdvertisement(AD_ID, owner, createAdType(1, "Banner"), createAdStatus(1, "Activo"), 100, LocalDate.now().plusDays(5));
        AdLockStatus lockStatus = createAdLockStatus(1, "Activo");

        AdBlockCreateRequest dto = new AdBlockCreateRequest(
                MAGAZINE_ID,
                AD_ID,
                120,
                LocalDate.now().plusDays(7)
        );

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(advertisementRepository.findById(AD_ID)).thenReturn(Optional.of(advertisement));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(owner));
        when(adLockStatusRepository.findById(1)).thenReturn(Optional.of(lockStatus));

        MoneyException exception = assertThrows(MoneyException.class, () -> spyService.blockAd(dto, USER_ID));

        assertEquals("Total Cost Exceeded", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
        verify(advertisementRepository, never()).save(any(Advertisement.class));
        verify(adBlockRepository, never()).save(any(AdBlock.class));
    }

    @Test
    void testBlockAdWhenMagazineNotFound() {
        AdvertisementServiceImplementation spyService = spy(service);
        AdBlockCreateRequest dto = new AdBlockCreateRequest(MAGAZINE_ID, AD_ID, 120, LocalDate.now().plusDays(7));

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> spyService.blockAd(dto, USER_ID));

        assertEquals("Magazine not found", exception.getMessage());
        verify(advertisementRepository, never()).findById(any(Integer.class));
    }

    @Test
    void testBlockAdWhenAdvertisementNotFound() {
        AdvertisementServiceImplementation spyService = spy(service);
        User owner = createUser(USER_ID, 500);
        Magazine magazine = createMagazine(MAGAZINE_ID, owner, 120);
        AdBlockCreateRequest dto = new AdBlockCreateRequest(MAGAZINE_ID, AD_ID, 120, LocalDate.now().plusDays(7));

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(advertisementRepository.findById(AD_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> spyService.blockAd(dto, USER_ID));

        assertEquals("Advertisement not found", exception.getMessage());
        verify(userRepository, never()).findById(any(Integer.class));
    }

    @Test
    void testBlockAdWhenOwnerUserNotFound() {
        AdvertisementServiceImplementation spyService = spy(service);
        User owner = createUser(USER_ID, 500);
        Magazine magazine = createMagazine(MAGAZINE_ID, owner, 120);
        Advertisement advertisement = createAdvertisement(AD_ID, owner, createAdType(1, "Banner"), createAdStatus(1, "Activo"), 100, LocalDate.now().plusDays(5));
        AdBlockCreateRequest dto = new AdBlockCreateRequest(MAGAZINE_ID, AD_ID, 120, LocalDate.now().plusDays(7));

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(advertisementRepository.findById(AD_ID)).thenReturn(Optional.of(advertisement));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> spyService.blockAd(dto, USER_ID));

        assertEquals("Owner user not found", exception.getMessage());
        verify(adLockStatusRepository, never()).findById(any(Integer.class));
    }

    @Test
    void testBlockAdWhenAdLockStatusNotFound() {
        AdvertisementServiceImplementation spyService = spy(service);
        User owner = createUser(USER_ID, 500);
        Magazine magazine = createMagazine(MAGAZINE_ID, owner, 120);
        Advertisement advertisement = createAdvertisement(AD_ID, owner, createAdType(1, "Banner"), createAdStatus(1, "Activo"), 100, LocalDate.now().plusDays(5));
        AdBlockCreateRequest dto = new AdBlockCreateRequest(MAGAZINE_ID, AD_ID, 120, LocalDate.now().plusDays(7));

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(advertisementRepository.findById(AD_ID)).thenReturn(Optional.of(advertisement));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(owner));
        when(adLockStatusRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> spyService.blockAd(dto, USER_ID));

        assertEquals("Ad lock status not found", exception.getMessage());
        verify(adStatusRepository, never()).findById(any(Integer.class));
    }

    @Test
    void testBlockAdWhenExpirationDateIsNotFuture() {
        AdvertisementServiceImplementation spyService = spy(service);
        User owner = createUser(USER_ID, 500);
        Magazine magazine = createMagazine(MAGAZINE_ID, owner, 120);
        Advertisement advertisement = createAdvertisement(AD_ID, owner, createAdType(1, "Banner"), createAdStatus(1, "Activo"), 100, LocalDate.now().plusDays(5));
        AdLockStatus lockStatus = createAdLockStatus(1, "Activo");
        AdBlockCreateRequest dto = new AdBlockCreateRequest(MAGAZINE_ID, AD_ID, 120, LocalDate.now());

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(advertisementRepository.findById(AD_ID)).thenReturn(Optional.of(advertisement));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(owner));
        when(adLockStatusRepository.findById(1)).thenReturn(Optional.of(lockStatus));

        ValidationException exception = assertThrows(ValidationException.class,
                () -> spyService.blockAd(dto, USER_ID));

        assertEquals("Expiration Date must be future", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
        verify(advertisementRepository, never()).save(any(Advertisement.class));
    }

    @Test
    void testBlockAdWhenAdStatusNotFound() {
        AdvertisementServiceImplementation spyService = spy(service);
        User owner = createUser(USER_ID, 500);
        Magazine magazine = createMagazine(MAGAZINE_ID, owner, 120);
        Advertisement advertisement = createAdvertisement(AD_ID, owner, createAdType(1, "Banner"), createAdStatus(1, "Activo"), 100, LocalDate.now().plusDays(5));
        AdLockStatus lockStatus = createAdLockStatus(1, "Activo");
        AdBlockCreateRequest dto = new AdBlockCreateRequest(MAGAZINE_ID, AD_ID, 120, LocalDate.now().plusDays(7));

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(advertisementRepository.findById(AD_ID)).thenReturn(Optional.of(advertisement));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(owner));
        when(adLockStatusRepository.findById(1)).thenReturn(Optional.of(lockStatus));
        when(adStatusRepository.findById(2)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> spyService.blockAd(dto, USER_ID));

        assertEquals("Ad status not found", exception.getMessage());
        verify(adBlockRepository, never()).save(any(AdBlock.class));
    }

    @Test
    void testDisableAdSuccess() throws ResourceNotFoundException {
        AdvertisementServiceImplementation spyService = spy(service);

        User user = createUser(USER_ID, 500);
        Advertisement advertisement = createAdvertisement(AD_ID, user, createAdType(1, "Banner"), createAdStatus(1, "Activo"), 100, LocalDate.now().plusDays(5));
        AdStatus blockedStatus = createAdStatus(2, "Bloqueado");

        when(advertisementRepository.findById(AD_ID)).thenReturn(Optional.of(advertisement));
        when(adStatusRepository.findById(2)).thenReturn(Optional.of(blockedStatus));

        spyService.disableAd(AD_ID);

        assertEquals(blockedStatus, advertisement.getAdStatus());
        verify(advertisementRepository).save(advertisement);
    }

    @Test
    void testDisableAdWhenAdvertisementNotFound() {
        AdvertisementServiceImplementation spyService = spy(service);
        when(advertisementRepository.findById(AD_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> spyService.disableAd(AD_ID));

        assertEquals("Advertisement not found", exception.getMessage());
        verify(adStatusRepository, never()).findById(any(Integer.class));
    }

    @Test
    void testDisableAdWhenStatusNotFound() {
        AdvertisementServiceImplementation spyService = spy(service);
        User user = createUser(USER_ID, 500);
        Advertisement advertisement = createAdvertisement(AD_ID, user, createAdType(1, "Banner"), createAdStatus(1, "Activo"), 100, LocalDate.now().plusDays(5));

        when(advertisementRepository.findById(AD_ID)).thenReturn(Optional.of(advertisement));
        when(adStatusRepository.findById(2)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> spyService.disableAd(AD_ID));

        assertEquals("Ad status not found", exception.getMessage());
        verify(advertisementRepository, never()).save(any(Advertisement.class));
    }

    @Test
    void testFindAllAdByMagazineSuccess() throws ResourceNotFoundException {
        AdvertisementServiceImplementation spyService = spy(service);

        User user = createUser(USER_ID, 200);
        Advertisement ad = createAdvertisement(AD_ID, user, createAdType(1, "Banner"), createAdStatus(1, "Activo"), 200, LocalDate.now().plusDays(12));
        ExtraContent extra = createExtraContent(ad, "https://a.com/one.png");

        when(advertisementRepository.findAllowedAdsByMagazine_Id(MAGAZINE_ID)).thenReturn(List.of(ad));
        when(extraContentRepository.findByAdvertisement_Id(AD_ID)).thenReturn(List.of(extra));

        List<AdFindResponse> result = spyService.findAllAdByMagazine(MAGAZINE_ID);

        assertEquals(1, result.size());
        assertEquals(AD_ID, result.get(0).getIdAdvertisement());
        assertEquals(1, result.get(0).getLinks().size());
    }

    @Test
    void testFindAllAdvertisementSuccess() {
        AdvertisementServiceImplementation spyService = spy(service);

        User user = createUser(USER_ID, 200);
        Advertisement ad = createAdvertisement(AD_ID, user, createAdType(1, "Banner"), createAdStatus(1, "Activo"), 200, LocalDate.now().plusDays(12));

        when(advertisementRepository.findAllByAdStatus_Id(1)).thenReturn(List.of(ad));
        when(extraContentRepository.findByAdvertisement_Id(AD_ID)).thenReturn(List.of());

        List<AdFindResponse> result = spyService.findAllAdvertisement();

        assertEquals(1, result.size());
        assertEquals("Activo", result.get(0).getAdStatusName());
        assertTrue(result.get(0).getLinks().isEmpty());
    }

    @Test
    void testGetBlockCostByMagazineSuccess() throws ResourceNotFoundException {
        AdvertisementServiceImplementation spyService = spy(service);

        User owner = createUser(USER_ID, 500);
        Magazine magazine = createMagazine(MAGAZINE_ID, owner, 150);

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));

        Integer result = spyService.getBlockCostByMagazine(MAGAZINE_ID);

        assertEquals(150, result);
        verify(magazineRepository).findById(MAGAZINE_ID);
    }

    @Test
    void testGetBlockCostByMagazineWhenMagazineNotFound() {
        AdvertisementServiceImplementation spyService = spy(service);
        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> spyService.getBlockCostByMagazine(MAGAZINE_ID));

        assertEquals("Magazine not found", exception.getMessage());
    }

    @Test
    void testFindAllByAdvertiserSuccess() {
        AdvertisementServiceImplementation spyService = spy(service);

        User user = createUser(USER_ID, 200);
        AdType adType = createAdType(1, "Banner");
        AdStatus adStatus = createAdStatus(1, "Activo");
        Advertisement advertisement = createAdvertisement(AD_ID, user, adType, adStatus, 200, LocalDate.now().plusDays(12));

        ExtraContent extra1 = createExtraContent(advertisement, "https://a.com/1.png");
        ExtraContent extra2 = createExtraContent(advertisement, "https://a.com/2.png");

        when(advertisementRepository.findAllByUser_Id(USER_ID)).thenReturn(List.of(advertisement));
        when(extraContentRepository.findByAdvertisement_Id(AD_ID)).thenReturn(List.of(extra1, extra2));

        List<AdFindResponse> result = spyService.findAllByAdvertiser(USER_ID);

        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(AD_ID, result.get(0).getIdAdvertisement()),
                () -> assertEquals(1, result.get(0).getIdAdType()),
                () -> assertEquals("Activo", result.get(0).getAdStatusName()),
                () -> assertEquals(2, result.get(0).getLinks().size())
        );
    }

    private User createUser(Integer id, Integer availableMoney) {
        User user = new User();
        user.setId(id);
        user.setAvailableMoney(availableMoney);
        return user;
    }

    private Magazine createMagazine(Integer id, User owner, Integer adBlockCost) {
        Magazine magazine = new Magazine();
        magazine.setId(id);
        magazine.setUser(owner);
        magazine.setAdBlockCost(adBlockCost);
        return magazine;
    }

    private AdType createAdType(Integer id, String name) {
        AdType adType = new AdType();
        adType.setId(id);
        adType.setName(name);
        return adType;
    }

    private AdStatus createAdStatus(Integer id, String name) {
        AdStatus adStatus = new AdStatus();
        adStatus.setId(id);
        adStatus.setName(name);
        return adStatus;
    }

    private AdLockStatus createAdLockStatus(Integer id, String name) {
        AdLockStatus adLockStatus = new AdLockStatus();
        adLockStatus.setId(id);
        adLockStatus.setName(name);
        return adLockStatus;
    }

    private Advertisement createAdvertisement(
            Integer id,
            User user,
            AdType adType,
            AdStatus adStatus,
            Integer totalCost,
            LocalDate expirationDate
    ) {
        Advertisement advertisement = new Advertisement();
        advertisement.setId(id);
        advertisement.setUser(user);
        advertisement.setAdType(adType);
        advertisement.setAdStatus(adStatus);
        advertisement.setTotalCost(totalCost);
        advertisement.setCreationDate(LocalDate.now());
        advertisement.setExpirationDate(expirationDate);
        return advertisement;
    }

    private ExtraContent createExtraContent(Advertisement advertisement, String resource) {
        ExtraContent extraContent = new ExtraContent();
        extraContent.setAdvertisement(advertisement);
        extraContent.setResource(resource);
        return extraContent;
    }
}
