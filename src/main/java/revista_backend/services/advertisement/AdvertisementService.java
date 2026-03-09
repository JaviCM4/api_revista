package revista_backend.services.advertisement;

import revista_backend.dto.advertisement.AdBlockCreateRequest;
import revista_backend.dto.advertisement.AdCreateRequest;
import revista_backend.dto.advertisement.AdFindResponse;
import revista_backend.exceptions.AccessDeniedException;
import revista_backend.exceptions.MoneyException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.ValidationException;

import java.util.List;

public interface AdvertisementService {

    void create(AdCreateRequest dto, Integer idUser)
            throws ResourceNotFoundException, ValidationException, MoneyException;

    void blockAd(AdBlockCreateRequest dto, Integer idUser)
            throws ResourceNotFoundException, MoneyException, ValidationException, AccessDeniedException;

    Integer getBlockCostByMagazine(Integer idMagazine) throws ResourceNotFoundException;

    void disableAd(Integer idAdvertisement)
            throws ResourceNotFoundException;

    List<AdFindResponse> findAllByAdvertiser (Integer idUser);

    List<AdFindResponse> findAllAdByMagazine(Integer idMagazine)
            throws ResourceNotFoundException;

    List<AdFindResponse> findAllAdvertisement();
}
