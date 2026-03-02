package revista_backend.services.magazine.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import revista_backend.dto.magazine.request.EditionCreateRequest;
import revista_backend.dto.magazine.response.EditionFindResponse;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.magazine.Edition;
import revista_backend.models.magazine.Magazine;
import revista_backend.repositories.magazine.EditionRepository;
import revista_backend.repositories.magazine.MagazineRepository;
import revista_backend.services.magazine.interfaces.EditionService;

import java.util.List;

@Service
public class EditionServiceImplementation implements EditionService {

    private final MagazineRepository magazineRepository;
    private final EditionRepository editionRepository;

    @Autowired
    public EditionServiceImplementation(MagazineRepository magazineRepository, EditionRepository editionRepository) {
        this.magazineRepository = magazineRepository;
        this.editionRepository = editionRepository;
    }

    @Override
    public Edition create(EditionCreateRequest dto) throws ResourceNotFoundException {
        Magazine magazine = magazineRepository.findById(dto.getMagazineId()).
                orElseThrow(() -> new ResourceNotFoundException("Magazine not found"));

        Edition newEdition = dto.createEntity(magazine);
        editionRepository.save(newEdition);
        return newEdition;
    }

    @Override
    public EditionFindResponse findAllEditionsByMagazine(Integer idMagazine) throws ResourceNotFoundException {

        if (!magazineRepository.existsById(idMagazine)) {
            throw new ResourceNotFoundException("Magazine not found");
        }

        EditionFindResponse response = new EditionFindResponse(
            idMagazine,
            editionRepository.findByMagazine_Id(idMagazine)
                .stream()
                .map(Edition::getResource)
                .toList()
        );

        return response;
    }
}
