package revista_backend.services.preference;

import revista_backend.dto.preference.PreferenceCategoryFindResponse;

import java.util.List;

public interface PreferenceCategoryService {

    List<PreferenceCategoryFindResponse> findAll();
}
