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
public class PretCrudService {

    final PretCrudRepository pretCrudRepository;
    final PrjExceptionHandler prjExceptionHandler;

    public PretCrudService(PretCrudRepository pretCrudRepository, PrjExceptionHandler prjExceptionHandler) {
        this.pretCrudRepository = pretCrudRepository;
        this.prjExceptionHandler = prjExceptionHandler;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<PretCrudDtoWeb> getPretsByUsagerNameWithCriteria(int idUsager) {
        List<PretCrudDtoWeb> pretCrudDtoWebs = pretCrudRepository.findPretBydUserIdWithCriteria(idUsager);
        if (pretCrudDtoWebs.isEmpty())
            prjExceptionHandler.throwPretNotAcceptable("aucun prêt en cours");
        return pretCrudDtoWebs;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Collection<Tuple> getPretsByUsager(User user) {
        Collection<Tuple> pretsByUsagerName = pretCrudRepository.findPretsByUser(user);
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
        return pretCrudRepository.saveAndFlush(optionalPret.get());
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Pret addPret(int idOuvrage , int idUsager) {
        Pret pretTmp = new Pret(idOuvrage, idUsager,0,new Date());
        return pretCrudRepository.saveAndFlush(pretTmp);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deletePret(int idouvrage, int idusager) {
        Pret pretTmp = getPretByPretPartiel(idouvrage,idusager);
        pretCrudRepository.delete(pretTmp);
        pretCrudRepository.flush();
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Optional<Pret> isPretExiste(int idouvrage, int idusager) {
        return pretCrudRepository.findPretByOuvrageIdouvrageAndUserIduser(idouvrage,idusager);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<PretCrudDtoBatch> getPretByeDueDate(Date dateCourante) {
        return pretCrudRepository.findPretsByDateEmpruntIsBefore(dateCourante);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pret getPretByPretPartiel(int idOuvrage, int idUsager) {
        Optional<Pret> optionalPret = pretCrudRepository.findPretByOuvrageIdouvrageAndUserIduser(idOuvrage,idUsager);
        if (optionalPret.isEmpty())
            prjExceptionHandler.throwPretNotAcceptable("aucun prêt ne correspond à ces critères ");
        return optionalPret.get();
    }

}

