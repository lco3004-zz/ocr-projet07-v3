package fr.ocr.application.user;

import fr.ocr.utility.UserJacksonFilters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(value = "APIs de gestion des Users (Usagers).")
@RestController
public class UserController {
    final
    UserService userService;

    final
    UserJacksonFilters<List<User>> userJacksonFilters;

    public UserController(UserService userService, UserJacksonFilters<List<User>> userJacksonFilters) {
        this.userService = userService;
        this.userJacksonFilters = userJacksonFilters;
    }

    @ApiOperation(value = "user par son nom.")
    @GetMapping(value = "/UserByName/{nom}")
    public UserDtoWeb getUsers(@PathVariable String nom) {

        return userService.getUserByNom(nom);
    }

    @ApiOperation(value = "Détails information User (Nom-Email).")
    @GetMapping(value = "/UserById/{idUser}", produces= MediaType.APPLICATION_JSON_VALUE)
    public UserDto getUserDTOById(@PathVariable Integer idUser) {

        return userService.getUserDTOById(idUser);
    }
}
