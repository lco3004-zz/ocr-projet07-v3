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
public class OuvrageCrudController {

    final OuvrageCrudService ouvrageCrudService;

    final OuvrageJacksonFilters<List<Ouvrage>> ouvrageJacksonFilters;

    public OuvrageCrudController(OuvrageCrudService ouvrageCrudService, OuvrageJacksonFilters<List<Ouvrage>> jf) {
        this.ouvrageCrudService = ouvrageCrudService;
        this.ouvrageJacksonFilters = jf;
    }

    @ApiOperation(value = "Recherche d'ouvrage par titre ou par auteur")
    @PostMapping(value="/LookForOuvrage")
    public MappingJacksonValue getOuvrageByQuery(@RequestBody(required = false) Map<String,String> criterionList) {
        return ouvrageJacksonFilters.filtersOnAttributes(ouvrageCrudService.getOuvrageByQuerie(criterionList));
    }

    @ApiOperation(value = "Recherche d'ouvrage par /Id")
    @GetMapping(value="/OuvrageDtoByID/{idOuvrage}")
    public OuvrageCrudDtoBatch getOuvrageDtoById(@PathVariable Integer idOuvrage) {
        return ouvrageCrudService.getOuvrageDtoById(idOuvrage);
    }
}
