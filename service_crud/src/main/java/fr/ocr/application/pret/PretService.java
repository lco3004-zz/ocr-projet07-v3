package fr.ocr.application.pret;

import fr.ocr.application.user.User;
import fr.ocr.exception.PrjExceptionHandler;
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
    final PrjExceptionHandler prjExceptionHandler;

    public PretService(PretRepository pretRepository, PrjExceptionHandler prjExceptionHandler) {
        this.pretRepository = pretRepository;
        this.prjExceptionHandler = prjExceptionHandler;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<PretDtoWeb> getPretsByUsagerNameWithCriteria(int idUsager) {
        List<PretDtoWeb> pretDtoWebs= pretRepository.findPretBydUserIdWithCriteria(idUsager);
        if (pretDtoWebs.isEmpty())
            prjExceptionHandler.throwPretNotAcceptable("aucun prêt en cours");
        return pretDtoWebs;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Collection<Tuple> getPretsByUsager(User user) {
        Collection<Tuple> pretsByUsagerName = pretRepository.findPretsByUser(user);
        if (pretsByUsagerName.isEmpty())
            prjExceptionHandler.throwPretNotAcceptable("aucun prêt en cours");
        return pretsByUsagerName;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Pret setProlongationPret(Integer idOuvrage, Integer idUsager) {
        Optional<Pret> optionalPret = isPretExiste(idOuvrage,idUsager);
        if (optionalPret.isEmpty()) {
            prjExceptionHandler.throwPretConflict("Pret n'existe pas");
        }
        else {
            if (optionalPret.get().getPretprolonge() >0 ) {
                prjExceptionHandler.throwProlongationAlreadyReported("ce prêt a déja été renouvelé ou est hors-délai");
            }
            optionalPret.get().setPretprolonge(1);
            Date proloDate = new Date();
            optionalPret.get().setDateEmprunt(proloDate);
        }
        return pretRepository.saveAndFlush(optionalPret.get());
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Pret addPret(int idOuvrage , int idUsager) {
        Pret pretTmp = new Pret(idOuvrage, idUsager,0,new Date());
        return pretRepository.saveAndFlush(pretTmp);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deletePret(int idouvrage, int idusager) {
        Pret pretTmp = getPretByPretPartiel(idouvrage,idusager);
        pretRepository.delete(pretTmp);
        pretRepository.flush();
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Optional<Pret> isPretExiste(int idouvrage, int idusager) {
        return pretRepository.findPretByOuvrageIdouvrageAndUserIduser(idouvrage,idusager);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<PretDtoBatch> getPretByeDueDate(Date dateCourante) {
        return pretRepository.findPretsByDateEmpruntIsBefore(dateCourante);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pret getPretByPretPartiel(int idOuvrage, int idUsager) {
        Optional<Pret> optionalPret = pretRepository.findPretByOuvrageIdouvrageAndUserIduser(idOuvrage,idUsager);
        if (optionalPret.isEmpty())
            prjExceptionHandler.throwPretNotAcceptable("aucun prêt ne correspond à ces critères ");
        return optionalPret.get();
    }

}

