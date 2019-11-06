package fr.ocr.domain.usager;

import fr.ocr.utility.filter.UsagerJacksonFilters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
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

    @ApiOperation(value = "Liste de tous les usagers de la bibliothèque.")
    @GetMapping(value = "/UsagerByNom/{nom}")
    public MappingJacksonValue getUsagers(@PathVariable String nom) {
        return usagerJacksonFilters.filtersOnAttributes(Collections.singletonList(usagerService.getUsagerByNom(nom)));
    }
    @ApiOperation(value = "Détails information Usager (Nom-Email).")
    @GetMapping(value = "/UsagerById/{idUsager}", produces= MediaType.APPLICATION_JSON_VALUE)
    public  UsagerDto getUsagerDTOById(@PathVariable Integer idUsager) {
         UsagerDto usagerDto = usagerService.getUsagerDTOById(idUsager);
        return usagerDto;
    }
}
