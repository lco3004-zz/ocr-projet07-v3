package fr.ocr.application.ouvrage;

import fr.ocr.utility.OuvrageJacksonFilters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@Api(value = "APIs de gestion des Ouvrages.")
@RestController
public class OuvrageController {

    final OuvrageService ouvrageService;

    final OuvrageJacksonFilters<List<Ouvrage>> ouvrageJacksonFilters;

    public OuvrageController(OuvrageService ouvrageService, OuvrageJacksonFilters<List<Ouvrage>> jf) {
        this.ouvrageService = ouvrageService;
        this.ouvrageJacksonFilters = jf;
    }

    @ApiOperation(value = "Recherche d'ouvrage par titre ou par auteur")
    @PostMapping(value="/LookForOuvrage")
    public MappingJacksonValue getOuvrageByQuery(@RequestBody(required = false) Map<String,String> criterionList) {
        return ouvrageJacksonFilters.filtersOnAttributes(ouvrageService.getOuvrageByQuerie(criterionList));
    }

    @ApiOperation(value = "Recherche d'ouvrage par /Id")
    @GetMapping(value="/OuvrageDtoByID/{idOuvrage}")
    public OuvrageDtoBatch getOuvrageDtoById(@PathVariable Integer idOuvrage) {
        return ouvrageService.getOuvrageDtoById(idOuvrage);
    }
}
