package fr.ocr.application.usager;

import fr.ocr.utility.UsagerJacksonFilters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(value = "APIs de gestion des Usagers.")
@RestController
public class UsagerController {
    final
    UsagerService usagerService;

    final
    UsagerJacksonFilters<List<Usager>> usagerJacksonFilters;

    public UsagerController(UsagerService usagerService, UsagerJacksonFilters<List<Usager>> usagerJacksonFilters) {
        this.usagerService = usagerService;
        this.usagerJacksonFilters = usagerJacksonFilters;
    }

    @ApiOperation(value = "usager par son nom.")
    @GetMapping(value = "/UsagerByNom/{nom}")
    public UsagerDtoWeb getUsagers(@PathVariable String nom) {
        return usagerService.getUsagerByNom(nom);
    }
    @ApiOperation(value = "Détails information Usager (Nom-Email).")
    @GetMapping(value = "/UsagerById/{idUsager}", produces= MediaType.APPLICATION_JSON_VALUE)
    public  UsagerDto getUsagerDTOById(@PathVariable Integer idUsager) {
        return usagerService.getUsagerDTOById(idUsager);
    }
}
