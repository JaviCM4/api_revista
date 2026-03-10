package revista_backend.services.magazine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import revista_backend.dto.magazine.request.InteractionCommentRequest;
import revista_backend.dto.magazine.response.CommentsResponse;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.RestrictedException;
import revista_backend.models.magazine.InteractionComment;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.magazine.InteractionLike;
import revista_backend.models.user.User;
import revista_backend.repositories.magazine.InteractionCommentRepository;
import revista_backend.repositories.magazine.InteractionLikeRepository;
import revista_backend.repositories.magazine.MagazineRepository;
import revista_backend.repositories.user.UserRepository;
import revista_backend.services.magazine.interfaces.MagazineInteractionService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class MagazineInteractionServiceImplementation implements MagazineInteractionService {

    private final MagazineRepository magazineRepository;
    private final UserRepository userRepository;
    private final InteractionLikeRepository interactionLikeRepository;
    private final InteractionCommentRepository interactionCommentRepository;

    @Autowired
    public MagazineInteractionServiceImplementation(MagazineRepository magazineRepository, UserRepository userRepository, InteractionLikeRepository interactionLikeRepository, InteractionCommentRepository interactionCommentRepository) {
        this.magazineRepository = magazineRepository;
        this.userRepository = userRepository;
        this.interactionLikeRepository = interactionLikeRepository;
        this.interactionCommentRepository = interactionCommentRepository;
    }

    @Override
    public void createLike(Integer idMagazine, Integer idUser)
            throws ResourceNotFoundException, RestrictedException {

        Magazine magazine = magazineRepository.findById(idMagazine)
                .orElseThrow(() -> new ResourceNotFoundException("Magazine not found"));

        if (!magazine.isAllowReactions()) {
            throw new RestrictedException("You are not able to give feedback for this magazine");
        }

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        InteractionLike interaction = interactionLikeRepository.findByMagazine_IdAndUser_Id(idMagazine, idUser);

        if (interaction == null) {
            interaction = new InteractionLike();
            interaction.setMagazine(magazine);
            interaction.setUser(user);
        }

        interaction.setLiked(!Boolean.TRUE.equals(interaction.getLiked()));
        interaction.setLikeDate(LocalDate.now());
        interactionLikeRepository.save(interaction);
    }

    @Override
    public void createComment(InteractionCommentRequest dto, Integer idUser)
            throws ResourceNotFoundException, RestrictedException {

        Magazine magazine = magazineRepository.findById(dto.getIdMagazine())
                .orElseThrow(() -> new ResourceNotFoundException("Magazine not found"));

        if (!magazine.isAllowComments()) {
            throw new RestrictedException("Comments cannot be made");
        }

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        InteractionComment interaction = new InteractionComment();
        interaction.setMagazine(magazine);
        interaction.setUser(user);
        interaction.setComment(dto.getComment());
        interaction.setCommentDate(LocalDate.now());
        interactionCommentRepository.save(interaction);
    }

    @Override
    public void deleteComment(Integer idComment)
            throws ResourceNotFoundException {
        InteractionComment interaction = interactionCommentRepository.findById(idComment)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        interactionCommentRepository.delete(interaction);
    }

    @Override
    public boolean findLikeByUser(Integer idMagazine, Integer idUser) {
        InteractionLike interaction = interactionLikeRepository.findByMagazine_IdAndUser_Id(idMagazine, idUser);
        return interaction != null && Boolean.TRUE.equals(interaction.getLiked());
    }

    @Override
    public Integer findQuantityLikeByMagazine(Integer idMagazine) {
        return interactionLikeRepository.countByMagazine_IdAndLikedTrue(idMagazine);
    }

    @Override
    public List<CommentsResponse> findAllCommentsByMagazine(Integer idMagazine) {
        return interactionCommentRepository.findByMagazine_Id(idMagazine)
                .stream()
                .map(CommentsResponse::new)
                .toList();
    }
}