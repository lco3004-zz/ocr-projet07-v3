/*
 * Service :
 * renvoie la liste des ouvrages disponibles par recherche titre/auteur
 * retourne l'enregistrement dont l'id est égal à celui passé en paramètre de recherche
 * modifie la quantité d'ouvrage disponibles
 *
 */

package fr.ocr.application.ouvrage;

import fr.ocr.exception.PrjExceptionHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class OuvrageCrudService {

    final OuvrageCrudRepository ouvrageCrudRepository;
    final PrjExceptionHandler prjExceptionHandler;

    public OuvrageCrudService(OuvrageCrudRepository ouvrageCrudRepository, PrjExceptionHandler prjExceptionHandler) {
        this.ouvrageCrudRepository = ouvrageCrudRepository;
        this.prjExceptionHandler = prjExceptionHandler;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Ouvrage> getOuvrageByQuerie(Map<String, String> requeteSearch) {

        List<Ouvrage> ouvrageList;

        String gTitre = requeteSearch.get("titre");
        String gAuteur = requeteSearch.get("auteur");

        if ((gAuteur != null && ! gAuteur.isEmpty()) && (gTitre != null && ! gTitre.isEmpty())) {
            ouvrageList = ouvrageCrudRepository.findOuvrageByAuteurLikeIgnoreCaseAndTitreLikeIgnoreCase(gAuteur+"%",gTitre+"%");
        }
        else if (gAuteur != null && ! gAuteur.isEmpty()) {
            ouvrageList = ouvrageCrudRepository.findOuvrageByAuteurLikeIgnoreCase(gAuteur+"%");
        } else if (gTitre != null && ! gTitre.isEmpty()) {
            ouvrageList = ouvrageCrudRepository.findOuvrageByTitreLikeIgnoreCase(gTitre+"%");
        } else  {
             ouvrageList = ouvrageCrudRepository.findAll();
             if (ouvrageList.isEmpty()) {
                 prjExceptionHandler.throwOuvrageNotFound();
             }
         }
        if (ouvrageList.isEmpty()) {
            prjExceptionHandler.throwOuvrageNotFound();
        }
        return ouvrageList;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public OuvrageCrudDtoBatch getOuvrageDtoById(Integer id) {
         Optional<OuvrageCrudDtoBatch> optionalOuvrageDtoBatch = ouvrageCrudRepository.findOuvrageDtoByIdouvrage(id);

        if (optionalOuvrageDtoBatch.isEmpty())
            prjExceptionHandler.throwOuvrageNotFound();
        return optionalOuvrageDtoBatch.get();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Ouvrage setQuantiteByIdOuvrage(Integer id, Integer quantiteOuvrage) {

        Optional<Ouvrage> optionalOuvrage = ouvrageCrudRepository.findOuvrageByIdouvrage(id);

        if (optionalOuvrage.isEmpty())
            prjExceptionHandler.throwOuvrageNotFound();

        Ouvrage ouvrage = optionalOuvrage.get();

        if (quantiteOuvrage < 0 && ouvrage.getQuantite() == 0) {
            prjExceptionHandler.throwOuvrageNotContentForLoan("Aucun Ouvrage disponible en Bibliothèque !");
        }
        ouvrage.setQuantite(ouvrage.getQuantite() +quantiteOuvrage);
        ouvrageCrudRepository.saveAndFlush(ouvrage);
        return  ouvrage;
    }
}
