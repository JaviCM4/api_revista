package revista_backend.services.magazine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import revista_backend.dto.magazine.request.MagazineTagCreateRequest;
import revista_backend.dto.magazine.response.MagazineTagResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.magazine.MagazineTag;
import revista_backend.repositories.magazine.MagazineRepository;
import revista_backend.repositories.magazine.MagazineTagRepository;
import revista_backend.services.magazine.interfaces.MagazineTagService;

import java.text.Normalizer;
import java.util.List;

@Service
public class MagazineTagServiceImplementation implements MagazineTagService {

    private final MagazineTagRepository magazineTagRepository;
    private final MagazineRepository magazineRepository;

    @Autowired
    public MagazineTagServiceImplementation(MagazineTagRepository magazineTagRepository, MagazineRepository magazineRepository) {
        this.magazineTagRepository = magazineTagRepository;
        this.magazineRepository = magazineRepository;
    }

    @Override
    public void create(MagazineTagCreateRequest dto) throws ResourceNotFoundException, ConflictException {
        Magazine magazine = magazineRepository.findById(dto.getIdMagazine())
                .orElseThrow(() -> new ResourceNotFoundException("Magazine not found"));

        String normalizedDetail = normalize(dto.getDetail());

        if (magazineTagRepository.existsByMagazine_IdAndDetailIgnoreCase(dto.getIdMagazine(), normalizedDetail)) {
            throw new ConflictException("That label already exists for this magazine");
        }

        MagazineTag newMagazineTag = dto.createEntity(magazine);
        magazineTagRepository.save(newMagazineTag);
    }

    @Override
    public List<MagazineTagResponse> findByMagazineTagId(Integer idMagazine) {

        return magazineTagRepository.findByMagazine_Id(idMagazine)
                .stream()
                .map(MagazineTagResponse::new)
                .toList();
    }

    @Override
    public void delete(Integer idTag) throws ResourceNotFoundException {
        if (!magazineTagRepository.existsById(idTag)) {
            throw new ResourceNotFoundException("Tag not found");
        }

        magazineTagRepository.deleteById(idTag);
    }

    private String normalize(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return normalized.trim().toLowerCase();
    }
}