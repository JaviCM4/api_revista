package revista_backend.services.magazine.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import revista_backend.dto.magazine.request.MagazineCategoryCreateRequest;
import revista_backend.dto.magazine.response.MagazineCategoryResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.categories.MagazineCategory;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.types.MagazineCategoryType;
import revista_backend.repositories.categories.MagazineCategoryRepository;
import revista_backend.repositories.magazine.MagazineRepository;
import revista_backend.repositories.types.MagazineCategoryTypeRepository;
import revista_backend.services.magazine.interfaces.MagazineCategoryService;

import java.util.List;

@Service
public class MagazineCategoryServiceImplementation implements MagazineCategoryService {

    private final MagazineCategoryRepository magazineCategoryRepository;
    private final MagazineRepository magazineRepository;
    private final MagazineCategoryTypeRepository magazineCategoryTypeRepository;

    @Autowired
    public MagazineCategoryServiceImplementation(MagazineCategoryRepository magazineCategoryRepository, MagazineRepository magazineRepository, MagazineCategoryTypeRepository magazineCategoryTypeRepository) {
        this.magazineCategoryRepository = magazineCategoryRepository;
        this.magazineRepository = magazineRepository;
        this.magazineCategoryTypeRepository = magazineCategoryTypeRepository;
    }

    @Override
    public void create(MagazineCategoryCreateRequest dto) throws ResourceNotFoundException, ConflictException {
        Magazine magazine = magazineRepository.findById(dto.getIdMagazine())
                .orElseThrow(() -> new ResourceNotFoundException("Magazine not found"));

        MagazineCategoryType magazineType = magazineCategoryTypeRepository.findById(dto.getIdCategoryMagazine())
                .orElseThrow(() -> new ResourceNotFoundException("Type Category not found"));

        if (!magazineCategoryRepository.existsByMagazine_IdAndMagazineCategoryType_Id(dto.getIdMagazine(), dto.getIdCategoryMagazine())) {
            MagazineCategory newMagazineCategory = new MagazineCategory();
            newMagazineCategory.setMagazine(magazine);
            newMagazineCategory.setMagazineCategoryType(magazineType);
            magazineCategoryRepository.save(newMagazineCategory);
        } else {
            throw new ConflictException("Duplicidad de la Categoria");
        }
    }

    @Override
    public List<MagazineCategoryResponse> findByMagazineId(Integer idMagazine) {

        return magazineCategoryRepository.findByMagazine_Id(idMagazine)
                .stream()
                .map(MagazineCategoryResponse::new)
                .toList();
    }

    @Override
    public void delete(Integer idMagazineCategorie) throws ResourceNotFoundException {
        if (!magazineCategoryRepository.existsById(idMagazineCategorie)) {
             throw new ResourceNotFoundException("Magazine Categoria not found");
        }
        magazineCategoryRepository.deleteById(idMagazineCategorie);
    }
}
