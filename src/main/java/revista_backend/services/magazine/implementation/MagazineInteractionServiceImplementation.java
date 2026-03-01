package revista_backend.services.magazine.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import revista_backend.dto.magazine.request.InteractionCommentRequest;
import revista_backend.dto.magazine.request.InteractionLikeRequest;
import revista_backend.dto.magazine.response.CommentsResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.RestrictedException;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.magazine.MagazineInteraction;
import revista_backend.models.user.User;
import revista_backend.repositories.magazine.MagazineInteractionRepository;
import revista_backend.repositories.magazine.MagazineRepository;
import revista_backend.repositories.magazine.MagazineSubscriptionRepository;
import revista_backend.repositories.user.UserRepository;
import revista_backend.services.magazine.interfaces.MagazineInteractionService;

import java.time.LocalDate;
import java.util.List;

@Service
public class MagazineInteractionServiceImplementation implements MagazineInteractionService {

    private final MagazineRepository magazineRepository;
    private final UserRepository userRepository;
    private final MagazineInteractionRepository magazineInteractionRepository;

    @Autowired
    public MagazineInteractionServiceImplementation(MagazineRepository magazineRepository, UserRepository userRepository, MagazineSubscriptionRepository magazineSubscriptionRepository, MagazineInteractionRepository magazineInteractionRepository) {
        this.magazineRepository = magazineRepository;
        this.userRepository = userRepository;
        this.magazineInteractionRepository = magazineInteractionRepository;
    }

    @Override
    public void createLike(InteractionLikeRequest dto)
            throws ResourceNotFoundException, RestrictedException {
        Magazine magazine = magazineRepository.findById(dto.getIdMagazine())
                .orElseThrow(() -> new ResourceNotFoundException("Magazine not found"));

        if (magazine.isAllowReactions()) {
            User user = userRepository.findById(dto.getIdUser())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            MagazineInteraction interaction = magazineInteractionRepository.findByMagazine_IdAndUser_Id(dto.getIdMagazine(), dto.getIdUser());

            if (interaction == null) {
                interaction = new MagazineInteraction();
                interaction.setMagazine(magazine);
                interaction.setUser(user);
            }
            interaction.setLiked(!Boolean.TRUE.equals(interaction.getLiked()));
            interaction.setLikeDate(LocalDate.now());
            magazineInteractionRepository.save(interaction);
        } else {
            throw new RestrictedException("No esta habilitado dar reacciones");
        }
    }

    @Override
    public void createComment(InteractionCommentRequest dto)
            throws ResourceNotFoundException, RestrictedException {
        Magazine magazine = magazineRepository.findById(dto.getIdMagazine())
                .orElseThrow(() -> new ResourceNotFoundException("Magazine not found"));

        if (magazine.isAllowComments()) {
            User user = userRepository.findById(dto.getIdUser())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            MagazineInteraction interaction = dto.createEntity(magazine, user);
            magazineInteractionRepository.save(interaction);
        } else {
            throw new RestrictedException("No se puede realizar comentarios");
        }
    }

    public Integer findQuantityLikeByMagazine(Integer idMagazine) {
        return magazineInteractionRepository.countByMagazine_IdAndLikedTrue(idMagazine);
    }

    @Override
    public List<CommentsResponse> findAllCommentsByMagazine(Integer idMagazine) {

        return magazineInteractionRepository.findByMagazine_Id(idMagazine)
                .stream()
                .map(CommentsResponse::new)
                .toList();
    }

}
