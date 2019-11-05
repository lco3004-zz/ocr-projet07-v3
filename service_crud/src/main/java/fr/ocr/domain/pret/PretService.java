package fr.ocr.domain.pret;

import fr.ocr.domain.ouvrage.Ouvrage;
import fr.ocr.domain.usager.Usager;
import fr.ocr.utility.exception.PretNotFoundException;
import fr.ocr.utility.exception.ProlongationPretImpossibleException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class PretService {

    final PretRepository pretRepository;



    public PretService(PretRepository pretRepository) {
        this.pretRepository = pretRepository;

    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<PretDtoWeb> getPretsByUsagerNameWithCriteria(Usager usager) {

        List<PretDtoWeb> pretDtoWebs= pretRepository.findPretBydUsagerIdWithCriteria(usager.getIdusager());

        if (pretDtoWebs.isEmpty())
            throw new PretNotFoundException("aucun prêt en cours");
        return pretDtoWebs;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Collection<Tuple> getPretsByUsager(Usager usager) {

        Collection<Tuple> pretsByUsagerName = pretRepository.findPretsByUsagerName(usager);

        if (pretsByUsagerName.isEmpty())
            throw new PretNotFoundException("aucun prêt en cours");

        return pretsByUsagerName;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Pret setProlongationPret(Integer idOuvrage, Integer idUsager) {

        Optional<Pret> optionalPret = isPretExiste(idOuvrage,idUsager);
        if (optionalPret.isEmpty()) {
            throw new PretNotFoundException("Pret n'existe pas");
        }
        else {
            if (optionalPret.get().getPretprolonge() >1 ) {
                throw new ProlongationPretImpossibleException("ce prêt a déja été renouvelé ou est hors-délai");
            }
            optionalPret.get().setPretprolonge(1);

            Date proloDate = new Date();
            optionalPret.get().setDateEmprunt(proloDate);
        }

        return pretRepository.saveAndFlush(optionalPret.get());
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Pret addPret(Ouvrage ouvrage , Usager usager) {

        Pret pretTmp = new Pret(ouvrage.getIdouvrage(), usager.getIdusager(),0,new Date());

        return pretRepository.saveAndFlush(pretTmp);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deletePret(Ouvrage ouvrage , Usager usager) {

        Pret pretTmp = getPretByPretPartiel(ouvrage.getIdouvrage(),usager.getIdusager());

        pretRepository.delete(pretTmp);
        pretRepository.flush();

    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Optional<Pret> isPretExiste(int idouvrage, int idusager) {

        return pretRepository.findPretByOuvrageIdouvrageAndUsagerIdusager(idouvrage,idusager);

    }

    private Pret getPretByPretPartiel(int idOuvrage, int idUsager) {
        Optional<Pret> optionalPret = pretRepository.findPretByOuvrageIdouvrageAndUsagerIdusager(idOuvrage,idUsager);
        if (optionalPret.isEmpty())
            throw new PretNotFoundException("aucun prêt ne correspond à ces critères ");
        return optionalPret.get();
    }

    public List<PretDtoBatch> getPretByeDueDate(Date dateCourante) {
        List<PretDtoBatch> pretDtoBatchList = pretRepository.findPretsByDateEmpruntIsBefore(dateCourante);
        return pretDtoBatchList;
    }


}

