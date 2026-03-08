package revista_backend.services.magazine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revista_backend.dto.magazine.request.EditionCreateRequest;
import revista_backend.dto.magazine.response.EditionFindResponse;
import revista_backend.exceptions.AccessDeniedException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.magazine.Edition;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.user.User;
import revista_backend.repositories.magazine.EditionRepository;
import revista_backend.repositories.magazine.MagazineRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditionServiceImplementationTest {

    private static final int MAGAZINE_ID = 1;
    private static final int USER_ID = 1;
    private static final int EDITION_ID = 1;
    private static final String RESOURCE = "res.pdf";

    @Mock
    private MagazineRepository magazineRepository;

    @Mock
    private EditionRepository editionRepository;

    @InjectMocks
    private EditionServiceImplementation service;

    @Test
    void testCreateSuccess() throws ResourceNotFoundException, AccessDeniedException {
        // Arrange
        EditionCreateRequest dto = createEditionRequest();
        Magazine magazine = createMagazine(MAGAZINE_ID, USER_ID);

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(editionRepository.save(any(Edition.class))).thenAnswer(i -> i.getArgument(0));

        ArgumentCaptor<Edition> editionCaptor = ArgumentCaptor.forClass(Edition.class);

        // Act
        Edition created = service.create(dto, USER_ID);

        // Assert
        verify(magazineRepository).findById(MAGAZINE_ID);
        verify(editionRepository).save(editionCaptor.capture());

        Edition savedEdition = editionCaptor.getValue();

        assertAll(
                () -> assertNotNull(created),
                () -> assertEquals(RESOURCE, savedEdition.getResource()),
                () -> assertEquals(magazine, savedEdition.getMagazine())
        );
    }

    @Test
    void testCreateMagazineNotFound() {
        // Arrange
        EditionCreateRequest dto = createEditionRequest();
        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, 
                () -> service.create(dto, USER_ID));
        assertEquals("Magazine not found", ex.getMessage());
        verify(editionRepository, never()).save(any(Edition.class));
    }

    @Test
    void testFindAllEditionsByMagazineSuccess() throws ResourceNotFoundException {
        // Arrange
        Edition edition = createEdition(EDITION_ID, MAGAZINE_ID);
        List<Edition> editions = Arrays.asList(edition);

        when(magazineRepository.existsById(MAGAZINE_ID)).thenReturn(true);
        when(editionRepository.findByMagazine_Id(MAGAZINE_ID)).thenReturn(editions);

        // Act
        EditionFindResponse res = service.findAllEditionsByMagazine(MAGAZINE_ID);

        // Assert
        verify(magazineRepository).existsById(MAGAZINE_ID);
        verify(editionRepository).findByMagazine_Id(MAGAZINE_ID);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(MAGAZINE_ID, res.getIdMagazine()),
                () -> assertEquals(1, res.getLinks().size())
        );
    }

    @Test
    void testFindAllEditionsByMagazineMagazineNotFound() {
        // Arrange
        int invalidMagazineId = 999;
        when(magazineRepository.existsById(invalidMagazineId)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, 
                () -> service.findAllEditionsByMagazine(invalidMagazineId));
        assertEquals("Magazine not found", ex.getMessage());
        verify(editionRepository, never()).findByMagazine_Id(invalidMagazineId);
    }

    private EditionCreateRequest createEditionRequest() {
        return new EditionCreateRequest(MAGAZINE_ID, RESOURCE);
    }

    private Magazine createMagazine(Integer id, Integer userId) {
        Magazine magazine = new Magazine();
        magazine.setId(id);
        User user = new User();
        user.setId(userId);
        magazine.setUser(user);
        return magazine;
    }

    private Edition createEdition(Integer id, Integer magazineId) {
        Edition edition = new Edition();
        edition.setId(id);
        edition.setResource(RESOURCE);
        Magazine magazine = new Magazine();
        magazine.setId(magazineId);
        edition.setMagazine(magazine);
        return edition;
    }
}
