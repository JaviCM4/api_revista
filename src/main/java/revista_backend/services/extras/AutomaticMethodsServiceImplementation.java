package revista_backend.services.extras;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

@Service
@Transactional(rollbackFor = Exception.class)
public class AutomaticMethodsServiceImplementation implements AutomaticMethodsService {

    private final AdvertisementRepository advertisementRepository;
    private final AdStatusRepository adStatusRepository;
    private final AdLockStatusRepository adLockStatusRepository;
    private final AdBlockRepository adBlockRepository;
    private final MagazineRepository magazineRepository;
    private final MagazinePaymentRepository magazinePaymentRepository;

    @Autowired
    public AutomaticMethodsServiceImplementation(AdvertisementRepository advertisementRepository, AdStatusRepository adStatusRepository, AdLockStatusRepository adLockStatusRepository, AdBlockRepository adBlockRepository, MagazineRepository magazineRepository, MagazinePaymentRepository magazinePaymentRepository) {
        this.advertisementRepository = advertisementRepository;
        this.adStatusRepository = adStatusRepository;
        this.adLockStatusRepository = adLockStatusRepository;
        this.adBlockRepository = adBlockRepository;
        this.magazineRepository = magazineRepository;
        this.magazinePaymentRepository = magazinePaymentRepository;
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void verifyDateAds() throws ResourceNotFoundException {
        List<Advertisement> advertisements = advertisementRepository.findAllByAdStatus_Id(1);
        AdStatus adStatus = adStatusRepository.findById(2)
                .orElseThrow(() -> new ResourceNotFoundException("Ad status not found"));

        for (Advertisement ad : advertisements) {
            if (!ad.getExpirationDate().isAfter(LocalDate.now())) {
                ad.setAdStatus(adStatus);
            }
        }
        advertisementRepository.saveAll(advertisements);
    }

    @Scheduled(cron = "0 30 2 * * ?")
    public void verifyDateBlocksAds() throws ResourceNotFoundException {
        List<AdBlock> adBlocks = adBlockRepository.findAllByAdLockStatus_Id(1);
        AdLockStatus adLockStatus = adLockStatusRepository.findById(2)
                .orElseThrow(() -> new ResourceNotFoundException("Ad Lock Status not found"));

        for (AdBlock adBlock : adBlocks) {
            if (!adBlock.getEndDate().isAfter(LocalDate.now())) {
                adBlock.setAdLockStatus(adLockStatus);
            }
        }
        adBlockRepository.saveAll(adBlocks);
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void makePaymentForMagazine() {
        List<Magazine> magazines = magazineRepository.findByActiveMagazine(true);

        for (Magazine mag : magazines) {
            MagazinePayment payment = new MagazinePayment();
            payment.setMagazine(mag);
            payment.setPayment(mag.getDailyCost());
            payment.setPaymentDate(LocalDate.now());
            magazinePaymentRepository.save(payment);
        }
    }
}