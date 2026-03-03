package revista_backend.dto.magazine.response;

import lombok.Value;
import revista_backend.models.categories.MagazineCategory;

@Value
public class MagazineCategoryResponse {

    Integer id;
    String magazineCategoryName;

    public MagazineCategoryResponse(MagazineCategory magazineCategory) {
        this.id = magazineCategory.getId();
        this.magazineCategoryName = magazineCategory.getMagazineCategoryType().getName();
    }
}
