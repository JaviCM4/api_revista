package revista_backend.services.cost;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revista_backend.dto.cost.SuggestedCostCreateResquest;
import revista_backend.dto.cost.SuggestedCostResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.cost.SuggestedCost;
import revista_backend.repositories.cost.SuggestedCostRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SuggestedCostServiceImplementationTest {

    private static final int COST = 100;
    private static final int DAYS = 30;
    private static final int SUGGESTED_COST_ID = 1;

    @Mock
    private SuggestedCostRepository suggestedCostRepository;

    @InjectMocks
    private SuggestedCostServiceImplementation service;

    @Test
    void testCreateSuccess() throws ResourceNotFoundException, ConflictException {
        SuggestedCostCreateResquest dto = new SuggestedCostCreateResquest(COST, DAYS);

        when(suggestedCostRepository.existsByDays(DAYS)).thenReturn(false);

        ArgumentCaptor<SuggestedCost> captor = ArgumentCaptor.forClass(SuggestedCost.class);

        service.create(dto);

        verify(suggestedCostRepository).existsByDays(DAYS);
        verify(suggestedCostRepository).save(captor.capture());

        SuggestedCost saved = captor.getValue();

        assertAll(
                () -> assertEquals(COST, saved.getCost()),
                () -> assertEquals(DAYS, saved.getDays())
        );
    }

    @Test
    void testCreateWhenDaysAlreadyExists() {
        SuggestedCostCreateResquest dto = new SuggestedCostCreateResquest(COST, DAYS);

        when(suggestedCostRepository.existsByDays(DAYS)).thenReturn(true);

        ConflictException exception = assertThrows(
                ConflictException.class,
                () -> service.create(dto)
        );

        assertEquals("That duration are already registered", exception.getMessage());
        verify(suggestedCostRepository).existsByDays(DAYS);
        verify(suggestedCostRepository, never()).save(any(SuggestedCost.class));
    }

    @Test
    void testFindAllSuccess() {
        SuggestedCost sc1 = createSuggestedCost(1, 100, 30);
        SuggestedCost sc2 = createSuggestedCost(2, 180, 60);

        when(suggestedCostRepository.findAll()).thenReturn(List.of(sc1, sc2));

        List<SuggestedCostResponse> result = service.findAll();

        verify(suggestedCostRepository).findAll();

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals(1, result.get(0).getIdSuggestedCost()),
                () -> assertEquals(100, result.get(0).getCost()),
                () -> assertEquals(60, result.get(1).getDays())
        );
    }

    @Test
    void testDeleteSuccess() throws ResourceNotFoundException {
        SuggestedCost suggestedCost = createSuggestedCost(SUGGESTED_COST_ID, COST, DAYS);

        when(suggestedCostRepository.findById(SUGGESTED_COST_ID)).thenReturn(Optional.of(suggestedCost));

        service.delete(SUGGESTED_COST_ID);

        verify(suggestedCostRepository).findById(SUGGESTED_COST_ID);
        verify(suggestedCostRepository).delete(suggestedCost);
    }

    @Test
    void testDeleteWhenNotFound() {
        when(suggestedCostRepository.findById(SUGGESTED_COST_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.delete(SUGGESTED_COST_ID)
        );

        assertEquals("Type User not found", exception.getMessage());
    }

    private SuggestedCost createSuggestedCost(Integer id, Integer cost, Integer days) {
        SuggestedCost suggestedCost = new SuggestedCost();
        suggestedCost.setId(id);
        suggestedCost.setCost(cost);
        suggestedCost.setDays(days);
        return suggestedCost;
    }
}
