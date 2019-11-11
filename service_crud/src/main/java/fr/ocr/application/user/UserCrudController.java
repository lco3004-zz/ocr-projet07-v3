package fr.ocr.application.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "APIs de gestion des Users (Usagers).")
@RestController
public class UserCrudController {
    final
    UserCrudService userCrudService;

    public UserCrudController(UserCrudService userCrudService) {
        this.userCrudService = userCrudService;
    }

    @ApiOperation(value = "user par son nom.")
    @GetMapping(value = "/UserByName/{nom}")
    public UserCrudDtoWeb getUsers(@PathVariable String nom) {

        return userCrudService.getUserByNom(nom);
    }

    @ApiOperation(value = "Détails information User (Nom-Email).")
    @GetMapping(value = "/UserById/{idUser}", produces= MediaType.APPLICATION_JSON_VALUE)
    public UserCrudDto getUserDTOById(@PathVariable Integer idUser) {

        return userCrudService.getUserDTOById(idUser);
    }
}
