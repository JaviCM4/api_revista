package revista_backend.services.magazine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional(rollbackFor = Exception.class)
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
    public void create(SubscriptionCreateRequest dto, Integer idUser)
            throws ResourceNotFoundException, ConflictException, RestrictedException, ValidationException {

        Magazine magazine = magazineRepository.findById(dto.getIdMagazine())
                .orElseThrow(() -> new ResourceNotFoundException("Magazine not found"));

        if (!magazine.isAllowSubscription()) {
            throw new RestrictedException("You are not eligible for subscriptions to this magazine");
        }

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (dto.getCreateDate().isBefore(magazine.getCreationDate())) {
            throw new ValidationException("You cannot subscribe before the magazine exists.");
        }

        boolean exists = magazineSubscriptionRepository
                .existsByMagazine_IdAndUser_Id(dto.getIdMagazine(), idUser);

        if (exists) {
            throw new ConflictException("You are already subscribed to this magazine");
        }

        MagazineSubscription newSubscription = dto.create(magazine, user);
        magazineSubscriptionRepository.save(newSubscription);
    }


    @Override
    public void delete(Integer idMagazine, Integer idUser) throws ResourceNotFoundException {
        MagazineSubscription subscription = magazineSubscriptionRepository.findByMagazine_IdAndUser_Id(idMagazine, idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
        magazineSubscriptionRepository.delete(subscription);
    }
}