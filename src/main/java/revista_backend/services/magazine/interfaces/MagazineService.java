package revista_backend.services.magazine.interfaces;

import revista_backend.dto.magazine.request.MagazineCreateRequest;
import revista_backend.dto.magazine.request.MagazineUpdateCostRequest;
import revista_backend.dto.magazine.request.MagazineUpdatePermissionsRequest;
import revista_backend.dto.magazine.response.MagazineFindNormalResponse;
import revista_backend.dto.magazine.response.MagazineFindResponse;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.ValidationException;
import revista_backend.models.magazine.Magazine;

import java.util.List;

public interface MagazineService {

    Magazine create(MagazineCreateRequest dto) throws ResourceNotFoundException, ValidationException;

    void updateCostMagazine(MagazineUpdateCostRequest dto) throws ResourceNotFoundException;

    void updatePermissionsMagazine(MagazineUpdatePermissionsRequest dto) throws ResourceNotFoundException;

    List<MagazineFindNormalResponse> findAllNormal();

    List<MagazineFindNormalResponse> findAllSubscriber(Integer idUser);

    List<MagazineFindResponse> findAllEditor(Integer idUser);

    List<MagazineFindResponse> findAllAdmin();

    List<MagazineFindNormalResponse> findAllCategory(Integer idCategory);

    List<MagazineFindNormalResponse> findAllTag(String tag);
}
