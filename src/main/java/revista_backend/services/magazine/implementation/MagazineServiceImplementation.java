package revista_backend.services.magazine.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import revista_backend.dto.magazine.request.MagazineCreateRequest;
import revista_backend.dto.magazine.request.MagazineUpdateCostRequest;
import revista_backend.dto.magazine.request.MagazineUpdatePermissionsRequest;
import revista_backend.dto.magazine.response.MagazineFindNormalResponse;
import revista_backend.dto.magazine.response.MagazineFindResponse;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.ValidationException;
import revista_backend.models.categories.MagazineCategory;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.magazine.MagazineSubscription;
import revista_backend.models.magazine.MagazineTag;
import revista_backend.models.user.User;
import revista_backend.repositories.categories.MagazineCategoryRepository;
import revista_backend.repositories.magazine.MagazineRepository;
import revista_backend.repositories.magazine.MagazineSubscriptionRepository;
import revista_backend.repositories.magazine.MagazineTagRepository;
import revista_backend.repositories.user.UserRepository;
import revista_backend.services.magazine.interfaces.MagazineService;

import java.time.LocalDate;
import java.util.List;

@Service
public class MagazineServiceImplementation implements MagazineService {

    private final MagazineRepository magazineRepository;
    private final UserRepository userRepository;
    private final MagazineSubscriptionRepository magazineSubscriptionRepository;
    private final MagazineTagRepository magazineTagRepository;
    private final MagazineCategoryRepository magazineCategoryRepository;

    @Autowired
    public MagazineServiceImplementation(MagazineRepository magazineRepository, UserRepository userRepository, MagazineSubscriptionRepository magazineSubscriptionRepository, MagazineTagRepository magazineTagRepository, MagazineCategoryRepository magazineCategoryRepository) {
        this.magazineRepository = magazineRepository;
        this.userRepository = userRepository;
        this.magazineSubscriptionRepository = magazineSubscriptionRepository;
        this.magazineTagRepository = magazineTagRepository;
        this.magazineCategoryRepository = magazineCategoryRepository;
    }

    @Override
    public Magazine create(MagazineCreateRequest dto) throws ResourceNotFoundException, ValidationException {
        User user = userRepository.findById(dto.getId_user())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuario no encontrado"));

        if (LocalDate.now().isBefore(dto.getCreateDate())) {
            throw new ValidationException("La fecha de creación debe ser menor o igual a hoy");
        }

        Magazine newMagazine =dto.createEntity(user);
        return magazineRepository.save(newMagazine);
    }

    @Override
    public void updateCostMagazine(MagazineUpdateCostRequest dto) throws ResourceNotFoundException {
        Magazine magazine = magazineRepository.findById(dto.getId()).
                orElseThrow(() -> new ResourceNotFoundException("Revista no encontrada"));

        magazine.setDailyCost(dto.getDailyCost());
        magazine.setAdBlockCost(dto.getAdBlockCost());
        magazineRepository.save(magazine);
    }

    @Override
    public void updatePermissionsMagazine(MagazineUpdatePermissionsRequest dto) throws ResourceNotFoundException {
        Magazine magazine = magazineRepository.findById(dto.getId()).
                orElseThrow(() -> new ResourceNotFoundException("Revista no encontrada"));

        magazine.setAllowSubscription(dto.isAllowSubscription());
        magazine.setAllowComments(dto.isAllowComments());
        magazine.setAllowReactions(dto.isAllowReactions());
        magazineRepository.save(magazine);
    }

    @Override
    public List<MagazineFindNormalResponse> findAllNormal() {
        List<Magazine> magazines = magazineRepository.findAll();

        return magazines.stream()
            .map(magazine -> {
                List<MagazineTag> tags = magazineTagRepository.findByMagazine_Id(magazine.getId());
                List<MagazineCategory> categories = magazineCategoryRepository.findByMagazine_Id(magazine.getId());
                return new MagazineFindNormalResponse(magazine, tags, categories);
            })
            .toList();
    }

    @Override
    public List<MagazineFindNormalResponse> findAllSubscriber(Integer idUser) {
        List<MagazineSubscription> listSubscriptions = magazineSubscriptionRepository.findByUser_Id(idUser);

        return listSubscriptions.stream()
            .map(subscription -> {
                Magazine magazine = subscription.getMagazine();
                List<MagazineTag> tags = magazineTagRepository.findByMagazine_Id(magazine.getId());
                List<MagazineCategory> categories = magazineCategoryRepository.findByMagazine_Id(magazine.getId());
                return new MagazineFindNormalResponse(magazine, tags, categories);
            })
            .toList();
    }

    @Override
    public List<MagazineFindNormalResponse> findAllCategory(Integer idCategory) {

        List<Magazine> magazines = magazineRepository.findByCategory_Id(idCategory);

        return magazines.stream()
            .map(magazine -> {
                List<MagazineTag> tags = magazineTagRepository.findByMagazine_Id(magazine.getId());
                List<MagazineCategory> categories = magazineCategoryRepository.findByMagazine_Id(magazine.getId());
                return new MagazineFindNormalResponse(magazine, tags, categories);
            })
            .toList();
    }

    @Override
    public List<MagazineFindNormalResponse> findAllTag(String tag) {
        List<MagazineTag> magazineTags = magazineTagRepository.findByDetailIgnoreCase(tag);

        return magazineTags.stream()
            .map(MagazineTag::getMagazine)
            .distinct()
            .map(magazine -> {
                List<MagazineTag> tags =
                        magazineTagRepository.findByMagazine_Id(magazine.getId());

                List<MagazineCategory> categories =
                        magazineCategoryRepository.findByMagazine_Id(magazine.getId());

                return new MagazineFindNormalResponse(magazine, tags, categories);
            })
            .toList();
    }

    @Override
    public List<MagazineFindResponse> findAllEditor(Integer idUser) {
        return magazineRepository.findByUser_Id(idUser)
            .stream()
            .map(MagazineFindResponse::new)
            .toList();
    }

    @Override
    public List<MagazineFindResponse> findAllAdmin() {
        return magazineRepository.findAll()
            .stream()
            .map(MagazineFindResponse::new)
            .toList();
    }
}
