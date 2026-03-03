package revista_backend.dto.magazine.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;
import revista_backend.models.magazine.Edition;
import revista_backend.models.magazine.Magazine;

import java.time.LocalDate;

@Value
public class EditionCreateRequest {

    @NotNull
    @Min(1)
    Integer magazineId;

    @NotBlank
    @Size(max = 500)
    String resource;

    public Edition createEntity(Magazine magazine) {
        Edition newEdition = new Edition();
        newEdition.setMagazine(magazine);
        newEdition.setResource(this.resource);
        newEdition.setCreationDate(LocalDate.now());
        return newEdition;
    }
}
