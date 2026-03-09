package revista_backend.controllers;

import jakarta.annotation.security.PermitAll;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import revista_backend.models.types.MagazineCategoryType;
import revista_backend.services.categories.MagazineCategoryTypeServiceImplementation;

import java.util.List;

@RestController
@RequestMapping("/v1/type/cat/magazine")
public class MagazineCategoryTypeController {


    private final MagazineCategoryTypeServiceImplementation magazineCategoryTypeServiceImplementation;

    public MagazineCategoryTypeController(MagazineCategoryTypeServiceImplementation magazineCategoryTypeServiceImplementation) {
        this.magazineCategoryTypeServiceImplementation = magazineCategoryTypeServiceImplementation;
    }

    @GetMapping()
    @PermitAll
    public ResponseEntity<List<MagazineCategoryType>> getAllCategories() {
        List<MagazineCategoryType> response = magazineCategoryTypeServiceImplementation.getAllCategories();
        return ResponseEntity.ok(response);
    }
}