package revista_backend.dto.preference;

import revista_backend.models.preference.Preference;

public class PreferenceFindResponse {

    Integer idPreference;
    String categoryName;

    public PreferenceFindResponse(Preference preference) {
        this.idPreference = preference.getId();
        this.categoryName = preference.getPreferenceCategory().getName();
    }
}
