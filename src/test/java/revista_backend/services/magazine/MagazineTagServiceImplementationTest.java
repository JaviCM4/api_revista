package revista_backend.services.magazine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revista_backend.dto.magazine.request.MagazineTagCreateRequest;
import revista_backend.dto.magazine.response.MagazineTagResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.magazine.MagazineTag;
import revista_backend.repositories.magazine.MagazineRepository;
import revista_backend.repositories.magazine.MagazineTagRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MagazineTagServiceImplementationTest {

    private static final int MAGAZINE_ID = 1;
    private static final int TAG_ID = 3;
    private static final int DELETE_TAG_ID = 8;
    private static final String TAG_DETAIL = "Detalle";

    @Mock
    private MagazineTagRepository magazineTagRepository;

    @Mock
    private MagazineRepository magazineRepository;

    @InjectMocks
    private MagazineTagServiceImplementation service;

    @Test
    void testCreateSuccess() throws ResourceNotFoundException, ConflictException {
        // Arrange
        MagazineTagCreateRequest dto = createTagRequest();
        Magazine magazine = createMagazine(MAGAZINE_ID);

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(magazineTagRepository.existsByMagazine_IdAndDetailIgnoreCase(MAGAZINE_ID, TAG_DETAIL.toLowerCase())).thenReturn(false);

        ArgumentCaptor<MagazineTag> tagCaptor = ArgumentCaptor.forClass(MagazineTag.class);

        // Act
        service.create(dto);

        // Assert
        verify(magazineRepository).findById(MAGAZINE_ID);
        verify(magazineTagRepository).existsByMagazine_IdAndDetailIgnoreCase(MAGAZINE_ID, TAG_DETAIL.toLowerCase());
        verify(magazineTagRepository).save(tagCaptor.capture());

        MagazineTag savedTag = tagCaptor.getValue();

        assertAll(
                () -> assertNotNull(savedTag),
                () -> assertEquals(magazine, savedTag.getMagazine()),
                () -> assertEquals(TAG_DETAIL.toLowerCase(), savedTag.getDetail().toLowerCase())
        );
    }

    @Test
    void testCreateMagazineNotFound() {
        // Arrange
        MagazineTagCreateRequest dto = createTagRequest();
        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, 
                () -> service.create(dto));
        assertEquals("Magazine not found", ex.getMessage());
        verify(magazineTagRepository, never()).save(any());
    }

    @Test
    void testCreateConflict() {
        // Arrange
        MagazineTagCreateRequest dto = createTagRequest();
        Magazine magazine = createMagazine(MAGAZINE_ID);

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(magazineTagRepository.existsByMagazine_IdAndDetailIgnoreCase(MAGAZINE_ID, TAG_DETAIL.toLowerCase())).thenReturn(true);

        // Act & Assert
        assertThrows(ConflictException.class, () -> service.create(dto));
        verify(magazineTagRepository, never()).save(any());
    }

    @Test
    void testFindByMagazineTagId() {
        // Arrange
        MagazineTag tag = createTag(TAG_ID);
        List<MagazineTag> tags = Arrays.asList(tag);

        when(magazineTagRepository.findByMagazine_Id(MAGAZINE_ID)).thenReturn(tags);

        // Act
        List<MagazineTagResponse> res = service.findByMagazineTagId(MAGAZINE_ID);

        // Assert
        verify(magazineTagRepository).findByMagazine_Id(MAGAZINE_ID);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(1, res.size()),
                () -> assertEquals(TAG_ID, res.get(0).getId())
        );
    }

    @Test
    void testDeleteNotFound() {
        // Arrange
        when(magazineTagRepository.existsById(DELETE_TAG_ID)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, 
                () -> service.delete(DELETE_TAG_ID));
        assertEquals("Tag not found", ex.getMessage());
        verify(magazineTagRepository, never()).deleteById(anyInt());
    }

    @Test
    void testDeleteSuccess() throws ResourceNotFoundException {
        // Arrange
        when(magazineTagRepository.existsById(DELETE_TAG_ID)).thenReturn(true);

        // Act
        service.delete(DELETE_TAG_ID);

        // Assert
        verify(magazineTagRepository).existsById(DELETE_TAG_ID);
        verify(magazineTagRepository).deleteById(DELETE_TAG_ID);
    }

    private MagazineTagCreateRequest createTagRequest() {
        return new MagazineTagCreateRequest(MAGAZINE_ID, TAG_DETAIL);
    }

    private Magazine createMagazine(Integer id) {
        Magazine magazine = new Magazine();
        magazine.setId(id);
        return magazine;
    }

    private MagazineTag createTag(Integer id) {
        MagazineTag tag = new MagazineTag();
        tag.setId(id);
        return tag;
    }
}
