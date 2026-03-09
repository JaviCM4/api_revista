package revista_backend.services.categories;

import revista_backend.models.types.MagazineCategoryType;

import java.util.List;

public interface MagazineCategoryTypeService {

    List<MagazineCategoryType> getAllCategories();
}
