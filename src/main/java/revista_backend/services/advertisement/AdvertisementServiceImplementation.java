package revista_backend.services.advertisement;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
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

@Service
public class AdvertisementServiceImplementation implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;
    private final AdTypeRepository adTypeRepository;
    private final AdStatusRepository adStatusRepository;
    private final ExtraContentRepository extraContentRepository;
    private final MagazineRepository magazineRepository;
    private final AdLockStatusRepository adLockStatusRepository;
    private final AdBlockRepository adBlockRepository;

    public AdvertisementServiceImplementation(revista_backend.repositories.advertisement.AdvertisementRepository advertisementRepository, AdvertisementRepository advertisementRepository1, UserRepository userRepository, AdTypeRepository adTypeRepository, AdStatusRepository adStatusRepository, ExtraContentRepository extraContentRepository, MagazineRepository magazineRepository, AdLockStatusRepository adLockStatusRepository, AdBlockRepository adBlockRepository) {
        this.advertisementRepository = advertisementRepository1;
        this.extraContentRepository = extraContentRepository;
        this.userRepository = userRepository;
        this.adTypeRepository = adTypeRepository;
        this.adStatusRepository = adStatusRepository;
        this.magazineRepository = magazineRepository;
        this.adLockStatusRepository = adLockStatusRepository;
        this.adBlockRepository = adBlockRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(AdCreateRequest dto, Integer idUser)
            throws ResourceNotFoundException, ValidationException, MoneyException {

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        AdType adType = adTypeRepository.findById(dto.getIdTypeAd())
                .orElseThrow(() -> new ResourceNotFoundException("Type Advertisement Not Found"));

        AdStatus adStatus = adStatusRepository.findById(1)
                .orElseThrow(() -> new ResourceNotFoundException("Status Advertisement Not Found"));

        // Validar dinero
        if (user.getAvailableMoney() < dto.getTotalCost()) {
            throw new MoneyException("Total Cost Exceeded");
        }

        // Validar fecha
        if (!dto.getExpirationDate().isAfter(LocalDate.now())) {
            throw new ValidationException("Expiration Date must be future");
        }

        // Descontar dinero
        user.setAvailableMoney(user.getAvailableMoney() - dto.getTotalCost());
        userRepository.save(user);

        // Crear anuncio
        Advertisement newAdvertisement = dto.createEntity(user, adType, adStatus);
        Advertisement ad = advertisementRepository.save(newAdvertisement);

        // Guardar recursos dinámicamente
        if (dto.getDetail() != null && !dto.getDetail().isEmpty()) {

            for (String resource : dto.getDetail()) {
                ExtraContent extra = new ExtraContent();
                extra.setAdvertisement(ad);
                extra.setResource(resource);
                extraContentRepository.save(extra);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void blockAd(AdBlockCreateRequest dto, Integer idUser)
            throws ResourceNotFoundException, MoneyException, ValidationException, AccessDeniedException {

        Magazine magazine = magazineRepository.findById(dto.getIdMagazine())
                .orElseThrow(() -> new ResourceNotFoundException("Magazine not found"));

        Advertisement advertisement = advertisementRepository.findById(dto.getInAdvertisement())
                .orElseThrow(() -> new ResourceNotFoundException("Advertisement not found"));

        User user = userRepository.findById(magazine.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner user not found"));

        if (!user.getId().equals(idUser)) {
            throw new AccessDeniedException("User Not Found");
        }

        AdLockStatus adLockStatus = adLockStatusRepository.findById(1)
                .orElseThrow(() -> new ResourceNotFoundException("Ad lock status not found"));

        // Validar dinero
        if (user.getAvailableMoney() < dto.getCost()) {
            throw new MoneyException("Total Cost Exceeded");
        }

        // Validar fecha
        if (!dto.getExpirationDate().isAfter(LocalDate.now())) {
            throw new ValidationException("Expiration Date must be future");
        }

        // Descontar dinero
        user.setAvailableMoney(user.getAvailableMoney() - dto.getCost());
        userRepository.save(user);

        // Cambiar estado del anuncio
        AdStatus adStatus = adStatusRepository.findById(2)
                .orElseThrow(() -> new ResourceNotFoundException("Ad status not found"));

        advertisement.setAdStatus(adStatus);
        advertisementRepository.save(advertisement);

        // Crear bloqueo
        AdBlock adBlock = dto.createEntity(magazine, advertisement, adLockStatus);
        adBlockRepository.save(adBlock);
    }

    @Override
    public Integer getBlockCostByMagazine(Integer idMagazine) throws ResourceNotFoundException {
        Magazine magazine = magazineRepository.findById(idMagazine)
                .orElseThrow(() -> new ResourceNotFoundException("Magazine not found"));

        return magazine.getAdBlockCost();
    }

    @Override
    public void disableAd(Integer idAdvertisement)
            throws ResourceNotFoundException {
        Advertisement advertisement = advertisementRepository.findById(idAdvertisement)
                .orElseThrow(() -> new ResourceNotFoundException("Advertisement not found"));

        AdStatus adStatus = adStatusRepository.findById(2)
                .orElseThrow(() -> new ResourceNotFoundException("Ad status not found"));
        advertisement.setAdStatus(adStatus);
        advertisementRepository.save(advertisement);
    }

    @Override
    public List<AdFindResponse> findAllByAdvertiser (Integer idUser) {
        return advertisementRepository.findAllByUser_Id(idUser)
                .stream()
                .map(ad -> {
                    List<ExtraContent> extras = extraContentRepository.findByAdvertisement_Id(ad.getId());

                    List<String> links = extras.stream()
                            .map(ExtraContent::getResource)
                            .toList();

                    return new AdFindResponse(
                            ad.getId(),
                            ad.getAdType().getId(),
                            ad.getAdStatus().getName(),
                            ad.getExpirationDate(),
                            links
                    );
                })
                .toList();
    }

    @Override
    public List<AdFindResponse> findAllAdByMagazine(Integer idMagazine) throws ResourceNotFoundException {
        return advertisementRepository.findAllowedAdsByMagazine_Id(idMagazine)
                .stream()
                .map(ad -> {
                    List<ExtraContent> extras = extraContentRepository.findByAdvertisement_Id(ad.getId());

                    List<String> links = extras.stream()
                            .map(ExtraContent::getResource)
                            .toList();

                    return new AdFindResponse(
                            ad.getId(),
                            ad.getAdType().getId(),
                            ad.getAdStatus().getName(),
                            ad.getExpirationDate(),
                            links
                    );
                })
                .toList();
    }

    @Override
    public List<AdFindResponse> findAllAdvertisement() {
        return advertisementRepository.findAllByAdStatus_Id(1)
                .stream()
                .map(ad -> {
                    List<ExtraContent> extras = extraContentRepository.findByAdvertisement_Id(ad.getId());

                    List<String> links = extras.stream()
                            .map(ExtraContent::getResource)
                            .toList();

                    return new AdFindResponse(
                            ad.getId(),
                            ad.getAdType().getId(),
                            ad.getAdStatus().getName(),
                            ad.getExpirationDate(),
                            links
                    );
                })
                .toList();
    }
}