package revista_backend.services.magazine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revista_backend.dto.magazine.request.InteractionCommentRequest;
import revista_backend.dto.magazine.response.CommentsResponse;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.RestrictedException;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.magazine.InteractionComment;
import revista_backend.models.magazine.InteractionLike;
import revista_backend.models.user.User;
import revista_backend.repositories.magazine.InteractionCommentRepository;
import revista_backend.repositories.magazine.InteractionLikeRepository;
import revista_backend.repositories.magazine.MagazineRepository;
import revista_backend.repositories.user.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MagazineInteractionServiceImplementationTest {

    private static final int MAGAZINE_ID = 1;
    private static final int USER_ID = 2;
    private static final int COMMENT_ID = 5;
    private static final int LIKE_COUNT = 5;
    private static final String COMMENT_TEXT = "hola";
    private static final String USER_NAMES = "Juan";
    private static final String USER_LAST_NAMES = "Perez";
    private static final String ALLOW_REACTIONS_ERROR = "You are not able to give feedback for this magazine";
    private static final String ALLOW_COMMENTS_ERROR = "Comments cannot be made";
    private static final String MAGAZINE_NOT_FOUND = "Magazine not found";
    private static final String USER_NOT_FOUND = "User not found";
    private static final String COMMENT_NOT_FOUND = "Comment not found";

    @Mock
    private MagazineRepository magazineRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private InteractionLikeRepository interactionLikeRepository;

    @Mock
    private InteractionCommentRepository interactionCommentRepository;

    @InjectMocks
    private MagazineInteractionServiceImplementation service;

    @Test
    void testCreateLikeSuccessWhenNoInteraction() throws Exception {
        // Arrange
        Magazine magazine = createMagazine(MAGAZINE_ID, true, true);
        User user = createUser(USER_ID);

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(interactionLikeRepository.findByMagazine_IdAndUser_Id(MAGAZINE_ID, USER_ID)).thenReturn(null);

        ArgumentCaptor<InteractionLike> likeCaptor = ArgumentCaptor.forClass(InteractionLike.class);

        // Act
        service.createLike(MAGAZINE_ID, USER_ID);

        // Assert
        verify(magazineRepository).findById(MAGAZINE_ID);
        verify(userRepository).findById(USER_ID);
        verify(interactionLikeRepository).findByMagazine_IdAndUser_Id(MAGAZINE_ID, USER_ID);
        verify(interactionLikeRepository).save(likeCaptor.capture());

        InteractionLike savedLike = likeCaptor.getValue();

        assertAll(
                () -> assertNotNull(savedLike),
                () -> assertEquals(magazine, savedLike.getMagazine()),
                () -> assertEquals(user, savedLike.getUser()),
                () -> assertTrue(savedLike.getLiked())
        );
    }

    @Test
    void testCreateLikeRestricted() {
        // Arrange
        Magazine magazine = createMagazine(MAGAZINE_ID, false, true);
        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));

        // Act & Assert
        RestrictedException ex = assertThrows(RestrictedException.class, 
                () -> service.createLike(MAGAZINE_ID, USER_ID));
        assertEquals(ALLOW_REACTIONS_ERROR, ex.getMessage());
        verify(userRepository, never()).findById(anyInt());
        verify(interactionLikeRepository, never()).save(any());
    }

    @Test
    void testCreateLikeMagazineNotFound() {
        // Arrange
        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, 
                () -> service.createLike(MAGAZINE_ID, USER_ID));
        assertEquals(MAGAZINE_NOT_FOUND, ex.getMessage());
        verify(interactionLikeRepository, never()).save(any());
    }

    @Test
    void testCreateLikeUserNotFound() {
        // Arrange
        Magazine magazine = createMagazine(MAGAZINE_ID, true, true);
        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, 
                () -> service.createLike(MAGAZINE_ID, USER_ID));
        assertEquals(USER_NOT_FOUND, ex.getMessage());
        verify(interactionLikeRepository, never()).save(any());
    }

    @Test
    void testCreateLikeToggleExisting() throws Exception {
        // Arrange
        Magazine magazine = createMagazine(MAGAZINE_ID, true, true);
        User user = createUser(USER_ID);
        InteractionLike existingLike = createInteractionLike(MAGAZINE_ID, USER_ID, true);

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(interactionLikeRepository.findByMagazine_IdAndUser_Id(MAGAZINE_ID, USER_ID)).thenReturn(existingLike);

        ArgumentCaptor<InteractionLike> likeCaptor = ArgumentCaptor.forClass(InteractionLike.class);

        // Act
        service.createLike(MAGAZINE_ID, USER_ID);

        // Assert
        verify(interactionLikeRepository).save(likeCaptor.capture());

        InteractionLike savedLike = likeCaptor.getValue();

        assertAll(
                () -> assertNotNull(savedLike),
                () -> assertFalse(savedLike.getLiked()) // Toggled from true to false
        );
    }

    @Test
    void testCreateCommentSuccess() throws Exception {
        // Arrange
        InteractionCommentRequest dto = createCommentRequest();
        Magazine magazine = createMagazine(MAGAZINE_ID, true, true);
        User user = createUser(USER_ID);

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        ArgumentCaptor<InteractionComment> commentCaptor = ArgumentCaptor.forClass(InteractionComment.class);

        // Act
        service.createComment(dto, USER_ID);

        // Assert
        verify(magazineRepository).findById(MAGAZINE_ID);
        verify(userRepository).findById(USER_ID);
        verify(interactionCommentRepository).save(commentCaptor.capture());

        InteractionComment savedComment = commentCaptor.getValue();

        assertAll(
                () -> assertNotNull(savedComment),
                () -> assertEquals(magazine, savedComment.getMagazine()),
                () -> assertEquals(user, savedComment.getUser()),
                () -> assertEquals(COMMENT_TEXT, savedComment.getComment()),
                () -> assertEquals(LocalDate.now(), savedComment.getCommentDate())
        );
    }

    @Test
    void testCreateCommentMagazineNotFound() {
        // Arrange
        InteractionCommentRequest dto = createCommentRequest();
        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, 
                () -> service.createComment(dto, USER_ID));
        assertEquals(MAGAZINE_NOT_FOUND, ex.getMessage());
        verify(interactionCommentRepository, never()).save(any());
    }

    @Test
    void testCreateCommentRestricted() {
        // Arrange
        InteractionCommentRequest dto = createCommentRequest();
        Magazine magazine = createMagazine(MAGAZINE_ID, true, false);
        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));

        // Act & Assert
        RestrictedException ex = assertThrows(RestrictedException.class, 
                () -> service.createComment(dto, USER_ID));
        assertEquals(ALLOW_COMMENTS_ERROR, ex.getMessage());
        verify(interactionCommentRepository, never()).save(any());
    }

    @Test
    void testCreateCommentUserNotFound() {
        // Arrange
        InteractionCommentRequest dto = createCommentRequest();
        Magazine magazine = createMagazine(MAGAZINE_ID, true, true);
        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, 
                () -> service.createComment(dto, USER_ID));
        assertEquals(USER_NOT_FOUND, ex.getMessage());
        verify(interactionCommentRepository, never()).save(any());
    }

    @Test
    void testDeleteCommentSuccess() throws ResourceNotFoundException {
        // Arrange
        InteractionComment comment = createInteractionComment(COMMENT_ID);
        when(interactionCommentRepository.findById(COMMENT_ID)).thenReturn(Optional.of(comment));

        // Act
        service.deleteComment(COMMENT_ID);

        // Assert
        verify(interactionCommentRepository).findById(COMMENT_ID);
        verify(interactionCommentRepository).delete(comment);
    }

    @Test
    void testDeleteCommentNotFound() {
        // Arrange
        when(interactionCommentRepository.findById(COMMENT_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, 
                () -> service.deleteComment(COMMENT_ID));
        assertEquals(COMMENT_NOT_FOUND, ex.getMessage());
        verify(interactionCommentRepository, never()).delete(any());
    }

    @Test
    void testFindLikeByUserWhenLiked() {
        // Arrange
        InteractionLike like = createInteractionLike(MAGAZINE_ID, USER_ID, true);
        when(interactionLikeRepository.findByMagazine_IdAndUser_Id(MAGAZINE_ID, USER_ID)).thenReturn(like);

        // Act
        boolean result = service.findLikeByUser(MAGAZINE_ID, USER_ID);

        // Assert
        verify(interactionLikeRepository).findByMagazine_IdAndUser_Id(MAGAZINE_ID, USER_ID);
        assertTrue(result);
    }

    @Test
    void testFindLikeByUserWhenNotLiked() {
        // Arrange
        InteractionLike like = createInteractionLike(MAGAZINE_ID, USER_ID, false);
        when(interactionLikeRepository.findByMagazine_IdAndUser_Id(MAGAZINE_ID, USER_ID)).thenReturn(like);

        // Act
        boolean result = service.findLikeByUser(MAGAZINE_ID, USER_ID);

        // Assert
        verify(interactionLikeRepository).findByMagazine_IdAndUser_Id(MAGAZINE_ID, USER_ID);
        assertFalse(result);
    }

    @Test
    void testFindLikeByUserWhenNoInteraction() {
        // Arrange
        when(interactionLikeRepository.findByMagazine_IdAndUser_Id(MAGAZINE_ID, USER_ID)).thenReturn(null);

        // Act
        boolean result = service.findLikeByUser(MAGAZINE_ID, USER_ID);

        // Assert
        verify(interactionLikeRepository).findByMagazine_IdAndUser_Id(MAGAZINE_ID, USER_ID);
        assertFalse(result);
    }

    @Test
    void testFindQuantityLikeByMagazine() {
        // Arrange
        when(interactionLikeRepository.countByMagazine_IdAndLikedTrue(MAGAZINE_ID)).thenReturn(LIKE_COUNT);

        // Act
        Integer quantity = service.findQuantityLikeByMagazine(MAGAZINE_ID);

        // Assert
        verify(interactionLikeRepository).countByMagazine_IdAndLikedTrue(MAGAZINE_ID);
        assertAll(
                () -> assertNotNull(quantity),
                () -> assertEquals(LIKE_COUNT, quantity)
        );
    }

    @Test
    void testFindAllCommentsByMagazineSuccess() {
        // Arrange
        InteractionComment comment = createInteractionComment(COMMENT_ID);
        List<InteractionComment> comments = Arrays.asList(comment);

        when(interactionCommentRepository.findByMagazine_Id(MAGAZINE_ID)).thenReturn(comments);

        // Act
        List<CommentsResponse> res = service.findAllCommentsByMagazine(MAGAZINE_ID);

        // Assert
        verify(interactionCommentRepository).findByMagazine_Id(MAGAZINE_ID);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(1, res.size()),
                () -> assertEquals(COMMENT_ID, res.get(0).getIdComment()),
                () -> assertEquals(COMMENT_TEXT, res.get(0).getComments())
        );
    }

    @Test
    void testFindAllCommentsByMagazineEmpty() {
        // Arrange
        when(interactionCommentRepository.findByMagazine_Id(MAGAZINE_ID)).thenReturn(Arrays.asList());

        // Act
        List<CommentsResponse> res = service.findAllCommentsByMagazine(MAGAZINE_ID);

        // Assert
        verify(interactionCommentRepository).findByMagazine_Id(MAGAZINE_ID);
        assertAll(
                () -> assertNotNull(res),
                () -> assertTrue(res.isEmpty())
        );
    }

    private InteractionCommentRequest createCommentRequest() {
        return new InteractionCommentRequest(MAGAZINE_ID, COMMENT_TEXT);
    }

    private Magazine createMagazine(Integer id, boolean allowReactions, boolean allowComments) {
        Magazine magazine = new Magazine();
        magazine.setId(id);
        magazine.setAllowReactions(allowReactions);
        magazine.setAllowComments(allowComments);
        return magazine;
    }

    private User createUser(Integer id) {
        User user = new User();
        user.setId(id);
        user.setNames(USER_NAMES);
        user.setLastNames(USER_LAST_NAMES);
        return user;
    }

    private InteractionLike createInteractionLike(Integer magazineId, Integer userId, Boolean liked) {
        InteractionLike like = new InteractionLike();
        Magazine magazine = new Magazine();
        magazine.setId(magazineId);
        like.setMagazine(magazine);
        
        User user = new User();
        user.setId(userId);
        like.setUser(user);
        
        like.setLiked(liked);
        like.setLikeDate(LocalDate.now());
        return like;
    }

    private InteractionComment createInteractionComment(Integer id) {
        InteractionComment comment = new InteractionComment();
        comment.setId(id);
        comment.setComment(COMMENT_TEXT);
        comment.setCommentDate(LocalDate.now());
        
        User user = new User();
        user.setNames(USER_NAMES);
        user.setLastNames(USER_LAST_NAMES);
        comment.setUser(user);
        
        Magazine magazine = new Magazine();
        magazine.setId(MAGAZINE_ID);
        comment.setMagazine(magazine);
        
        return comment;
    }
}
