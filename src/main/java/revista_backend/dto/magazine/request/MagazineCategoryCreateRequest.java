package revista_backend.dto.magazine.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import revista_backend.models.categories.MagazineCategory;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.types.MagazineCategoryType;

@Value
public class MagazineCategoryCreateRequest {

    @NotNull
    @Min(1)
    Integer idMagazine;

    @NotNull
    @Min(1)
    Integer idCategoryMagazine;

    public MagazineCategory createEntity(Magazine magazine, MagazineCategoryType magazineType) {
        MagazineCategory newMagazineCategory = new MagazineCategory();
        newMagazineCategory.setMagazine(magazine);
        newMagazineCategory.setMagazineCategoryType(magazineType);
        return newMagazineCategory;
    }
}
