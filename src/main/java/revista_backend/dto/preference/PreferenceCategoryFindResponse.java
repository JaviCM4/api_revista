package revista_backend.dto.preference;

import lombok.Value;
import revista_backend.models.categories.PreferenceCategory;

@Value
public class PreferenceCategoryFindResponse {

    Integer id;
    String typeCategoryMagazineName;
    String typePreferenceName;
    String name;

    public PreferenceCategoryFindResponse(PreferenceCategory preferenceCategory) {
        this.id = preferenceCategory.getId();
        this.typeCategoryMagazineName = preferenceCategory.getMagazineCategoryType().getName();
        this.typePreferenceName = preferenceCategory.getPreferenceType().getName();
        this.name = preferenceCategory.getName();
    }
}
