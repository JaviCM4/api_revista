package revista_backend.services.user;

import revista_backend.dto.user.UserCreateRequest;
import revista_backend.dto.user.UserFindResponse;
import revista_backend.dto.user.UserUpdateRequest;
import revista_backend.dto.user.UserUpdateResponse;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.user.User;

import java.util.List;

public interface UserService {

    User create(UserCreateRequest dto) throws ResourceNotFoundException;

    List<UserFindResponse> findAll();

    UserUpdateResponse update(Integer id, UserUpdateRequest dto) throws ResourceNotFoundException;

    UserFindResponse findById(Integer id) throws ResourceNotFoundException;

    void deleteById(Integer id) throws ResourceNotFoundException;
}

