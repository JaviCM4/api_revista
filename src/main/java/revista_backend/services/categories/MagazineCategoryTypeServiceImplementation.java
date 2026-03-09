package revista_backend.services.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import revista_backend.models.types.MagazineCategoryType;
import revista_backend.repositories.types.MagazineCategoryTypeRepository;

import java.util.List;

@Service
public class MagazineCategoryTypeServiceImplementation implements MagazineCategoryTypeService {

    private final MagazineCategoryTypeRepository magazineCategoryTypeRepository;

    @Autowired
    public MagazineCategoryTypeServiceImplementation(MagazineCategoryTypeRepository magazineCategoryTypeRepository) {
        this.magazineCategoryTypeRepository = magazineCategoryTypeRepository;
    }

    @Override
    public List<MagazineCategoryType> getAllCategories() {
        return magazineCategoryTypeRepository.findAll();
    }
}