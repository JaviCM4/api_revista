package revista_backend.dto.preference;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class PreferenceCreateResquest {

    @NotNull
    @Min(1)
    Integer idCategoryPreference;
}
