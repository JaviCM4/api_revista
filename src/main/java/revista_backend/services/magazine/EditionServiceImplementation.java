package revista_backend.services.magazine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import revista_backend.dto.magazine.request.EditionCreateRequest;
import revista_backend.dto.magazine.response.EditionFindResponse;
import revista_backend.dto.magazine.response.EditionResponse;
import revista_backend.exceptions.AccessDeniedException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.magazine.Edition;
import revista_backend.models.magazine.Magazine;
import revista_backend.repositories.magazine.EditionRepository;
import revista_backend.repositories.magazine.MagazineRepository;
import revista_backend.services.magazine.interfaces.EditionService;

@Service
@Transactional(rollbackFor = Exception.class)
public class EditionServiceImplementation implements EditionService {

    private final MagazineRepository magazineRepository;
    private final EditionRepository editionRepository;

    @Autowired
    public EditionServiceImplementation(MagazineRepository magazineRepository, EditionRepository editionRepository) {
        this.magazineRepository = magazineRepository;
        this.editionRepository = editionRepository;
    }

    @Override
    public Edition create(EditionCreateRequest dto, Integer idUser)
            throws ResourceNotFoundException, AccessDeniedException {
        Magazine magazine = magazineRepository.findById(dto.getMagazineId()).
                orElseThrow(() -> new ResourceNotFoundException("Magazine not found"));

        if (!magazine.getUser().getId().equals(idUser)) {
            throw new AccessDeniedException("You do not have permission to edit this magazine");
        }
        Edition newEdition = dto.createEntity(magazine);
        editionRepository.save(newEdition);
        return newEdition;
    }

    @Override
    public EditionFindResponse findAllEditionsByMagazine(Integer idMagazine)
            throws ResourceNotFoundException {

        if (!magazineRepository.existsById(idMagazine)) {
            throw new ResourceNotFoundException("Magazine not found");
        }

        return new EditionFindResponse(
                idMagazine,
                editionRepository.findByMagazine_Id(idMagazine)
                        .stream()
                        .map(EditionResponse::new)
                        .toList()
        );
    }

    @Override
    public void delete(Integer idEdition)
            throws ResourceNotFoundException {
        Edition edition = editionRepository.findById(idEdition).
                orElseThrow(() -> new ResourceNotFoundException("Edition not found"));
        editionRepository.delete(edition);
    }
}