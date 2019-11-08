/*

 */
package fr.ocr.application.ouvrage;

import fr.ocr.exception.PrjExceptionHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Laurent Cordier
 */
@Service
public class OuvrageService {

    final OuvrageRepository ouvrageRepository;
    final PrjExceptionHandler prjExceptionHandler;

    public OuvrageService(OuvrageRepository ouvrageRepository, PrjExceptionHandler prjExceptionHandler) {
        this.ouvrageRepository = ouvrageRepository;
        this.prjExceptionHandler = prjExceptionHandler;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Ouvrage> getOuvrageByQuerie(Map<String, String> requeteSearch) {

        List<Ouvrage> ouvrageList;

        String gTitre = requeteSearch.get("titre");
        String gAuteur = requeteSearch.get("auteur");

        if ((gAuteur != null && ! gAuteur.isEmpty()) && (gTitre != null && ! gTitre.isEmpty())) {
            ouvrageList = ouvrageRepository.findOuvrageByAuteurLikeAndTitreLike(gAuteur+"%",gTitre+"%");
        }
        else if (gAuteur != null && ! gAuteur.isEmpty()) {
            ouvrageList = ouvrageRepository.findOuvrageByAuteurLike(gAuteur+"%");
        } else if (gTitre != null && ! gTitre.isEmpty()) {
            ouvrageList = ouvrageRepository.findOuvrageByTitreLike(gTitre+"%");
        } else  {
             ouvrageList = ouvrageRepository.findAll();
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
    public OuvrageDtoBatch getOuvrageDtoById(Integer id) {
         Optional<OuvrageDtoBatch> optionalOuvrageDtoBatch = ouvrageRepository.findOuvrageDtoByIdouvrage(id);

        if (optionalOuvrageDtoBatch.isEmpty())
            prjExceptionHandler.throwOuvrageNotFound();
        return optionalOuvrageDtoBatch.get();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Ouvrage setQuantiteByIdOuvrage(Integer id, Integer quantiteOuvrage) {

        Optional<Ouvrage> optionalOuvrage = ouvrageRepository.findOuvrageByIdouvrage(id);

        if (optionalOuvrage.isEmpty())
            prjExceptionHandler.throwOuvrageNotFound();

        Ouvrage ouvrage = optionalOuvrage.get();

        if (quantiteOuvrage < 0 && ouvrage.getQuantite() == 0) {
            prjExceptionHandler.throwOuvrageNotContentForLoan("Aucun Ouvrage disponible en Bibliothèque !");
        }
        ouvrage.setQuantite(ouvrage.getQuantite() +quantiteOuvrage);
        ouvrageRepository.saveAndFlush(ouvrage);
        return  ouvrage;
    }
}
