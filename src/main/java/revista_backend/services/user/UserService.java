package revista_backend.services.user;

import revista_backend.dto.user.UserCreateRequest;
import revista_backend.dto.user.UserCreateAdminRequest;
import revista_backend.dto.user.UserFindResponse;
import revista_backend.dto.user.UserUpdateRequest;
import revista_backend.dto.user.UserUpdateResponse;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.ValidationException;
import revista_backend.models.user.User;

import java.util.List;

public interface UserService {

    User create(UserCreateRequest dto) throws ResourceNotFoundException;

    User createAdmin(UserCreateAdminRequest dto) throws ResourceNotFoundException;

    List<UserFindResponse> findAll();

    UserUpdateResponse update(UserUpdateRequest dto, Integer idUser) throws ResourceNotFoundException;

    UserFindResponse findById(Integer idUser) throws ResourceNotFoundException;

    void updateMoney(Integer money, Integer idUser) throws ResourceNotFoundException, ValidationException;

    void suspendUser(Integer idUser) throws ResourceNotFoundException;

    void deleteById(Integer idUser) throws ResourceNotFoundException;
}

