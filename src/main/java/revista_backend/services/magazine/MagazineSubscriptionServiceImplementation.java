package revista_backend.services.magazine.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import revista_backend.services.magazine.interfaces.MagazineSubscriptionService;

@Service
public class MagazineSubscriptionServiceImplementation implements MagazineSubscriptionService {

    private final MagazineSubscriptionRepository magazineSubscriptionRepository;
    private final MagazineRepository magazineRepository;
    private final UserRepository userRepository;

    @Autowired
    public MagazineSubscriptionServiceImplementation(MagazineRepository magazineRepository, UserRepository userRepository, MagazineSubscriptionRepository magazineSubscriptionRepository) {
        this.magazineSubscriptionRepository = magazineSubscriptionRepository;
        this.magazineRepository = magazineRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void create(SubscriptionCreateRequest dto)
            throws ResourceNotFoundException, ConflictException, RestrictedException, ValidationException {

        Magazine magazine = magazineRepository.findById(dto.getIdMagazine())
                .orElseThrow(() -> new ResourceNotFoundException("Magazine not found"));

        if (!magazine.isAllowSubscription()) {
            throw new RestrictedException("No está habilitado para suscripciones");
        }

        User user = userRepository.findById(dto.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (magazine.getCreationDate().isBefore(dto.getCreateDate())) {
            throw new ValidationException("La fecha de creación de la revista es anterior a la fecha de suscripción");
        }

        boolean exists = magazineSubscriptionRepository
                .existsByMagazine_IdAndUser_Id(dto.getIdMagazine(), dto.getIdUser());

        if (exists) {
            throw new ConflictException("Ya está suscripto a esta revista");
        }

        MagazineSubscription newSubscription = dto.create(magazine, user);
        magazineSubscriptionRepository.save(newSubscription);
    }


    @Override
    public void delete(SubscriptionCreateRequest dto) throws ResourceNotFoundException {
        MagazineSubscription subscription = magazineSubscriptionRepository.findByMagazine_IdAndUser_Id(dto.getIdMagazine(), dto.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
        magazineSubscriptionRepository.delete(subscription);
    }
}
