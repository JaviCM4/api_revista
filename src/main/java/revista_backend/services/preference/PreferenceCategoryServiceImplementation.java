package revista_backend.services.preference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import revista_backend.dto.preference.PreferenceCategoryFindResponse;
import revista_backend.repositories.categories.PreferenceCategoryRepository;

import java.util.List;

@Service
@Transactional
public class PreferenceCategoryServiceImplementation implements PreferenceCategoryService {

    private final PreferenceCategoryRepository preferenceCategoryRepository;

    @Autowired
    public PreferenceCategoryServiceImplementation(PreferenceCategoryRepository preferenceCategoryRepository) {
        this.preferenceCategoryRepository = preferenceCategoryRepository;
    }

    @Override
    public List<PreferenceCategoryFindResponse> findAll() {

        return preferenceCategoryRepository.findAll()
                .stream()
                .map(PreferenceCategoryFindResponse::new)
                .toList();
    }
}