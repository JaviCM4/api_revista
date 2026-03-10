package revista_backend.services.preference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import revista_backend.dto.preference.PreferenceFindResponse;
import revista_backend.exceptions.AccessDeniedException;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.categories.PreferenceCategory;
import revista_backend.models.preference.Preference;
import revista_backend.models.user.User;
import revista_backend.repositories.categories.PreferenceCategoryRepository;
import revista_backend.repositories.preference.PreferenceRepository;
import revista_backend.repositories.user.UserRepository;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class PreferenceServiceImplementation implements PreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final UserRepository userRepository;
    private final PreferenceCategoryRepository preferenceCategoryRepository;

    @Autowired
    public PreferenceServiceImplementation(PreferenceRepository preferenceRepository, UserRepository userRepository, PreferenceCategoryRepository preferenceCategoryRepository) {
        this.preferenceRepository = preferenceRepository;
        this.userRepository = userRepository;
        this.preferenceCategoryRepository = preferenceCategoryRepository;
    }

    @Override
    public void create(Integer idCategoryPreference, Integer idUser)
            throws ResourceNotFoundException, ConflictException {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        PreferenceCategory category = preferenceCategoryRepository.findById(idCategoryPreference)
                .orElseThrow(() -> new ResourceNotFoundException("Category Preference not found"));

        if (preferenceRepository.existsByUser_IdAndPreferenceCategory_Id(idUser, idCategoryPreference)) {
            throw new ConflictException("Preference already exists");
        }

        Preference newPreference = new Preference();
        newPreference.setUser(user);
        newPreference.setPreferenceCategory(category);
        preferenceRepository.save(newPreference);
    }

    @Override
    public List<PreferenceFindResponse> findAllByUser(Integer idUser)
            throws ResourceNotFoundException {

        if (!userRepository.existsById(idUser)) {
            throw new ResourceNotFoundException("User not found");
        }

        return preferenceRepository.findByUser_Id(idUser)
                .stream()
                .map(PreferenceFindResponse::new)
                .toList();
    }

    @Override
    public void delete(Integer idPreference, Integer idUser)
            throws ResourceNotFoundException, AccessDeniedException {
        Preference preference = preferenceRepository.findById(idPreference)
                .orElseThrow(() -> new ResourceNotFoundException("Preference not found"));

        if (!preference.getUser().getId().equals(idUser)) {
            throw new AccessDeniedException("User Not Found");
        }
        preferenceRepository.deleteById(idPreference);
    }
}