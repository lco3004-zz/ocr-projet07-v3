package fr.ocr.domain.usager;

import fr.ocr.domain.ouvrage.OuvrageController;
import fr.ocr.utility.filter.UsagerJacksonFilters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "APIs de gestion des Usagers.")
@RestController
public class UsagerController {
    private final Logger logger = LoggerFactory.getLogger(OuvrageController.class);

    final
    UsagerService usagerService;

    final
    UsagerJacksonFilters<List<Usager>> usagerJacksonFilters;

    public UsagerController(UsagerService usagerService, UsagerJacksonFilters<List<Usager>> jf) {
        this.usagerService = usagerService;
        this.usagerJacksonFilters = jf;
    }

    @ApiOperation(value = "Liste de tous les usagers de la bibliothèque.")
    @GetMapping(value = "/Usagers")
    public MappingJacksonValue getUsagers() {
        return usagerJacksonFilters.filtersOnAttributes(usagerService.getUsagers());
    }

    @ApiOperation(value = "Détails information Usager (Nom-Email).")
    @GetMapping(value = "/UsagerDto/{idUsager}", produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Usager getUsagerById(@PathVariable Integer idUsager) {
        Usager usager = usagerService.getUsagerByIdDto(idUsager);
        return usager;
    }
}
