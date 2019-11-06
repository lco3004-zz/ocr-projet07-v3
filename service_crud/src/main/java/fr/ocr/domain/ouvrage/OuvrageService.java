/*

 */
package fr.ocr.domain.ouvrage;

import fr.ocr.utility.exception.OuvrageNotAvailableForLoan;
import fr.ocr.utility.exception.OuvrageNotFoundException;
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

    public OuvrageService(OuvrageRepository ouvrageRepository) {
        this.ouvrageRepository = ouvrageRepository;
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
                 throw  new OuvrageNotFoundException("Aucun Ouvrage en Bibliothèque !");
             }
         }
        if (ouvrageList.isEmpty()) {
            throw  new OuvrageNotFoundException("Aucun Ouvrage ne correspond aux critères de recherche");
        }
        return ouvrageList;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public OuvrageDtoBatch getOuvrageDtoById(Integer id) {
         Optional<OuvrageDtoBatch> optionalOuvrageDtoBatch = ouvrageRepository.findOuvrageDtoByIdouvrage(id);

        if (optionalOuvrageDtoBatch.isEmpty())
            throw  new OuvrageNotFoundException("Aucun Ouvrage en Bibliothèque !");
        return optionalOuvrageDtoBatch.get();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Ouvrage setQuantiteByIdOuvrage(Integer id, Integer quantiteOuvrage) {

        Optional<Ouvrage> optionalOuvrage = ouvrageRepository.findOuvrageByIdouvrage(id);

        if (optionalOuvrage.isEmpty())
            throw  new OuvrageNotFoundException("Aucun Ouvrage en Bibliothèque !");
        Ouvrage ouvrage = optionalOuvrage.get();

        if (quantiteOuvrage < 0 && ouvrage.getQuantite() == 0) {
            throw  new OuvrageNotAvailableForLoan("Aucun Ouvrage disponible en Bibliothèque !");
        }
        ouvrage.setQuantite(ouvrage.getQuantite() +quantiteOuvrage);
        ouvrageRepository.saveAndFlush(ouvrage);
        return  ouvrage;
    }
}
