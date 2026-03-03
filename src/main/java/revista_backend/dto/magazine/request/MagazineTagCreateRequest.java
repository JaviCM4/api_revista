package revista_backend.dto.magazine.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.magazine.MagazineTag;

@Value
public class MagazineTagCreateRequest {

    @NotNull
    @Min(1)
    Integer idMagazine;

    @NotBlank
    @Size(max = 250)
    String detail;

    public MagazineTag createEntity(Magazine magazine) {
        MagazineTag newMagazineTag = new MagazineTag();
        newMagazineTag.setMagazine(magazine);
        newMagazineTag.setDetail(this.detail);
        return newMagazineTag;
    }
}
