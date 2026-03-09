package revista_backend.services.magazine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import revista_backend.dto.magazine.request.AdRegistrationCreateRequest;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.advertisement.Advertisement;
import revista_backend.models.magazine.AdRegistration;
import revista_backend.repositories.advertisement.AdvertisementRepository;
import revista_backend.repositories.magazine.AdRegistrationRepository;
import revista_backend.services.magazine.interfaces.AdRegistrationService;

import java.time.LocalDate;

@Service
public class AdRegistrationServiceImplementation implements AdRegistrationService {

    private final AdvertisementRepository advertisementRepository;
    private final AdRegistrationRepository adRegistrationRepository;

    @Autowired
    public AdRegistrationServiceImplementation(AdvertisementRepository advertisementRepository, AdRegistrationRepository adRegistrationRepository) {
        this.advertisementRepository = advertisementRepository;
        this.adRegistrationRepository = adRegistrationRepository;
    }

    @Override
    public void create(AdRegistrationCreateRequest dto)
            throws ResourceNotFoundException {
        Advertisement ad = advertisementRepository.findById(dto.getIdAd())
                .orElseThrow(() -> new ResourceNotFoundException("Advertisement not found"));

        AdRegistration adRegistration = new AdRegistration();
        adRegistration.setAdvertisement(ad);
        adRegistration.setUrl(dto.getUrl());
        adRegistration.setNumberOfViews(dto.getNumberOfViews());
        adRegistration.setDateView(LocalDate.now());
        adRegistrationRepository.save(adRegistration);
    }
}
