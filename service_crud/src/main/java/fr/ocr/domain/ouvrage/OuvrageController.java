package fr.ocr.domain.ouvrage;

import fr.ocr.utility.filter.OuvrageJacksonFilters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "Recherche d'ouvrage par /{titre} ou par /{auteur}")
    @GetMapping(value="/LookForOuvrage")
    public MappingJacksonValue getReferenceSearch(@RequestBody(required = false) Map<String,String> criterionList) {
        List<Ouvrage> ouvrageList =  ouvrageService.getOuvrageByQuerie(criterionList);
        return ouvrageJacksonFilters.filtersOnAttributes(ouvrageList);
    }

}
