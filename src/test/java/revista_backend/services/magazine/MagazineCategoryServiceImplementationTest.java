package revista_backend.services.magazine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revista_backend.dto.magazine.request.MagazineCategoryCreateRequest;
import revista_backend.dto.magazine.response.MagazineCategoryResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.AccessDeniedException;
import revista_backend.models.categories.MagazineCategory;
import revista_backend.models.types.MagazineCategoryType;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.user.User;
import revista_backend.repositories.categories.MagazineCategoryRepository;
import revista_backend.repositories.magazine.MagazineRepository;
import revista_backend.repositories.types.MagazineCategoryTypeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MagazineCategoryServiceImplementationTest {

    private static final int MAGAZINE_ID = 1;
    private static final int CATEGORY_TYPE_ID = 2;
    private static final int USER_ID = 1;
    private static final int CATEGORY_ID = 5;
    private static final int CATEGORY_RESPONSE_ID = 10;
    private static final String CATEGORY_NAME = "Categoria";

    @Mock
    private MagazineCategoryRepository magazineCategoryRepository;

    @Mock
    private MagazineRepository magazineRepository;

    @Mock
    private MagazineCategoryTypeRepository magazineCategoryTypeRepository;

    @InjectMocks
    private MagazineCategoryServiceImplementation service;

    @Test
    void testCreateSuccess() throws ResourceNotFoundException, ConflictException, AccessDeniedException {
        // Arrange
        MagazineCategoryCreateRequest dto = createCategoryRequest();
        Magazine magazine = createMagazine(MAGAZINE_ID, USER_ID);
        MagazineCategoryType type = createCategoryType(CATEGORY_TYPE_ID);

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(magazineCategoryTypeRepository.findById(CATEGORY_TYPE_ID)).thenReturn(Optional.of(type));
        when(magazineCategoryRepository.existsByMagazine_IdAndMagazineCategoryType_Id(MAGAZINE_ID, CATEGORY_TYPE_ID)).thenReturn(false);

        ArgumentCaptor<MagazineCategory> categoryCaptor = ArgumentCaptor.forClass(MagazineCategory.class);

        // Act
        service.create(dto, USER_ID);

        // Assert
        verify(magazineRepository).findById(MAGAZINE_ID);
        verify(magazineCategoryTypeRepository).findById(CATEGORY_TYPE_ID);
        verify(magazineCategoryRepository).existsByMagazine_IdAndMagazineCategoryType_Id(MAGAZINE_ID, CATEGORY_TYPE_ID);
        verify(magazineCategoryRepository).save(categoryCaptor.capture());

        MagazineCategory savedCategory = categoryCaptor.getValue();

        assertAll(
                () -> assertNotNull(savedCategory),
                () -> assertEquals(magazine, savedCategory.getMagazine()),
                () -> assertEquals(type, savedCategory.getMagazineCategoryType())
        );
    }

    @Test
    void testCreateMagazineNotFound() {
        // Arrange
        MagazineCategoryCreateRequest dto = createCategoryRequest();
        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, 
                () -> service.create(dto, USER_ID));
        assertEquals("Magazine not found", ex.getMessage());
        verify(magazineCategoryRepository, never()).save(any());
    }

    @Test
    void testCreateTypeNotFound() {
        // Arrange
        MagazineCategoryCreateRequest dto = createCategoryRequest();
        Magazine magazine = createMagazine(MAGAZINE_ID, USER_ID);
        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(magazineCategoryTypeRepository.findById(CATEGORY_TYPE_ID)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, 
                () -> service.create(dto, USER_ID));
        assertEquals("Type Category not found", ex.getMessage());
        verify(magazineCategoryRepository, never()).save(any());
    }

    @Test
    void testCreateConflict() {
        // Arrange
        MagazineCategoryCreateRequest dto = createCategoryRequest();
        Magazine magazine = createMagazine(MAGAZINE_ID, USER_ID);
        MagazineCategoryType type = createCategoryType(CATEGORY_TYPE_ID);

        when(magazineRepository.findById(MAGAZINE_ID)).thenReturn(Optional.of(magazine));
        when(magazineCategoryTypeRepository.findById(CATEGORY_TYPE_ID)).thenReturn(Optional.of(type));
        when(magazineCategoryRepository.existsByMagazine_IdAndMagazineCategoryType_Id(MAGAZINE_ID, CATEGORY_TYPE_ID)).thenReturn(true);

        // Act & Assert
        assertThrows(ConflictException.class, () -> service.create(dto, USER_ID));
        verify(magazineCategoryRepository, never()).save(any());
    }

    @Test
    void testFindByMagazineId() {
        // Arrange
        MagazineCategory category = createMagazineCategory(CATEGORY_RESPONSE_ID, CATEGORY_NAME);
        List<MagazineCategory> categories = Arrays.asList(category);

        when(magazineCategoryRepository.findByMagazine_Id(MAGAZINE_ID)).thenReturn(categories);

        // Act
        List<MagazineCategoryResponse> res = service.findByMagazineId(MAGAZINE_ID);

        // Assert
        verify(magazineCategoryRepository).findByMagazine_Id(MAGAZINE_ID);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(1, res.size()),
                () -> assertEquals(CATEGORY_RESPONSE_ID, res.get(0).getId()),
                () -> assertEquals(CATEGORY_NAME, res.get(0).getMagazineCategoryName())
        );
    }

    @Test
    void testDeleteSuccess() throws ResourceNotFoundException, AccessDeniedException {
        // Arrange
        MagazineCategory category = createMagazineCategory(CATEGORY_ID, CATEGORY_NAME);
        Magazine magazine = createMagazine(MAGAZINE_ID, USER_ID);
        
        when(magazineCategoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));
        when(magazineRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(magazine));

        // Act
        service.delete(CATEGORY_ID, USER_ID);

        // Assert
        verify(magazineCategoryRepository).findById(CATEGORY_ID);
        verify(magazineRepository).findById(CATEGORY_ID);
        verify(magazineCategoryRepository).deleteById(CATEGORY_ID);
    }

    @Test
    void testDeleteNotFound() {
        // Arrange
        when(magazineCategoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.empty());
        
        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, 
                () -> service.delete(CATEGORY_ID, USER_ID));
        assertEquals("Category Magazine not found", ex.getMessage());
        verify(magazineCategoryRepository, never()).deleteById(anyInt());
    }

    private MagazineCategoryCreateRequest createCategoryRequest() {
        return new MagazineCategoryCreateRequest(MAGAZINE_ID, CATEGORY_TYPE_ID);
    }

    private Magazine createMagazine(Integer id, Integer userId) {
        Magazine magazine = new Magazine();
        magazine.setId(id);
        User user = new User();
        user.setId(userId);
        magazine.setUser(user);
        return magazine;
    }

    private MagazineCategoryType createCategoryType(Integer id) {
        MagazineCategoryType type = new MagazineCategoryType();
        type.setId(id);
        return type;
    }

    private MagazineCategory createMagazineCategory(Integer id, String typeName) {
        MagazineCategory category = new MagazineCategory();
        category.setId(id);
        MagazineCategoryType type = new MagazineCategoryType();
        type.setName(typeName);
        category.setMagazineCategoryType(type);
        return category;
    }
}
