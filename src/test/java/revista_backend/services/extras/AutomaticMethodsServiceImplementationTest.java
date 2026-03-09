package revista_backend.services.extras;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.advertisement.AdBlock;
import revista_backend.models.advertisement.Advertisement;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.magazine.MagazinePayment;
import revista_backend.models.status.AdLockStatus;
import revista_backend.models.status.AdStatus;
import revista_backend.repositories.advertisement.AdBlockRepository;
import revista_backend.repositories.advertisement.AdvertisementRepository;
import revista_backend.repositories.magazine.MagazinePaymentRepository;
import revista_backend.repositories.magazine.MagazineRepository;
import revista_backend.repositories.status.AdLockStatusRepository;
import revista_backend.repositories.status.AdStatusRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AutomaticMethodsServiceImplementationTest {

    @Mock
    private AdvertisementRepository advertisementRepository;

    @Mock
    private AdStatusRepository adStatusRepository;

    @Mock
    private AdLockStatusRepository adLockStatusRepository;

    @Mock
    private AdBlockRepository adBlockRepository;

    @Mock
    private MagazineRepository magazineRepository;

    @Mock
    private MagazinePaymentRepository magazinePaymentRepository;

    @InjectMocks
    private AutomaticMethodsServiceImplementation service;

    @Test
    void testVerifyDateAdsSuccess() throws ResourceNotFoundException {
        Advertisement expiredAd = createAdvertisement(1, LocalDate.now().minusDays(1));
        Advertisement activeAd = createAdvertisement(2, LocalDate.now().plusDays(5));
        AdStatus inactiveStatus = createAdStatus(2, "Inactivo");

        when(advertisementRepository.findAllByAdStatus_Id(1)).thenReturn(List.of(expiredAd, activeAd));
        when(adStatusRepository.findById(2)).thenReturn(Optional.of(inactiveStatus));

        ArgumentCaptor<List<Advertisement>> captor = ArgumentCaptor.forClass(List.class);

        service.verifyDateAds();

        verify(advertisementRepository).findAllByAdStatus_Id(1);
        verify(adStatusRepository).findById(2);
        verify(advertisementRepository).saveAll(captor.capture());

        List<Advertisement> savedAds = captor.getValue();

        assertAll(
                () -> assertEquals(inactiveStatus, savedAds.get(0).getAdStatus()),
                () -> assertEquals(null, savedAds.get(1).getAdStatus())
        );
    }

    @Test
    void testVerifyDateAdsWhenStatusNotFound() {
        when(advertisementRepository.findAllByAdStatus_Id(1)).thenReturn(List.of());
        when(adStatusRepository.findById(2)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.verifyDateAds()
        );

        assertEquals("Ad status not found", exception.getMessage());
    }

    @Test
    void testVerifyDateBlocksAdsSuccess() throws ResourceNotFoundException {
        AdBlock expiredBlock = createAdBlock(1, LocalDate.now().minusDays(1));
        AdBlock activeBlock = createAdBlock(2, LocalDate.now().plusDays(3));
        AdLockStatus inactiveLockStatus = createAdLockStatus(2, "Inactivo");

        when(adBlockRepository.findAllByAdLockStatus_Id(1)).thenReturn(List.of(expiredBlock, activeBlock));
        when(adLockStatusRepository.findById(2)).thenReturn(Optional.of(inactiveLockStatus));

        ArgumentCaptor<List<AdBlock>> captor = ArgumentCaptor.forClass(List.class);

        service.verifyDateBlocksAds();

        verify(adBlockRepository).findAllByAdLockStatus_Id(1);
        verify(adLockStatusRepository).findById(2);
        verify(adBlockRepository).saveAll(captor.capture());

        List<AdBlock> savedBlocks = captor.getValue();

        assertAll(
                () -> assertEquals(inactiveLockStatus, savedBlocks.get(0).getAdLockStatus()),
                () -> assertEquals(null, savedBlocks.get(1).getAdLockStatus())
        );
    }

    @Test
    void testMakePaymentForMagazineSuccess() {
        Magazine m1 = createMagazine(10, 50);
        Magazine m2 = createMagazine(20, 70);

        when(magazineRepository.findByActiveMagazine(true)).thenReturn(List.of(m1, m2));

        ArgumentCaptor<MagazinePayment> paymentCaptor = ArgumentCaptor.forClass(MagazinePayment.class);

        service.makePaymentForMagazine();

        verify(magazineRepository).findByActiveMagazine(true);
        verify(magazinePaymentRepository, times(2)).save(paymentCaptor.capture());

        List<MagazinePayment> savedPayments = paymentCaptor.getAllValues();

        assertAll(
                () -> assertEquals(m1, savedPayments.get(0).getMagazine()),
                () -> assertEquals(50, savedPayments.get(0).getPayment()),
                () -> assertEquals(LocalDate.now(), savedPayments.get(0).getPaymentDate()),
                () -> assertEquals(m2, savedPayments.get(1).getMagazine()),
                () -> assertEquals(70, savedPayments.get(1).getPayment())
        );
    }

    private Advertisement createAdvertisement(Integer id, LocalDate expirationDate) {
        Advertisement advertisement = new Advertisement();
        advertisement.setId(id);
        advertisement.setExpirationDate(expirationDate);
        return advertisement;
    }

    private AdStatus createAdStatus(Integer id, String name) {
        AdStatus adStatus = new AdStatus();
        adStatus.setId(id);
        adStatus.setName(name);
        return adStatus;
    }

    private AdBlock createAdBlock(Integer id, LocalDate endDate) {
        AdBlock adBlock = new AdBlock();
        adBlock.setId(id);
        adBlock.setEndDate(endDate);
        return adBlock;
    }

    private AdLockStatus createAdLockStatus(Integer id, String name) {
        AdLockStatus adLockStatus = new AdLockStatus();
        adLockStatus.setId(id);
        adLockStatus.setName(name);
        return adLockStatus;
    }

    private Magazine createMagazine(Integer id, Integer dailyCost) {
        Magazine magazine = new Magazine();
        magazine.setId(id);
        magazine.setDailyCost(dailyCost);
        return magazine;
    }
}
