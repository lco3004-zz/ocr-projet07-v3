package fr.ocr.application.usager;


import fr.ocr.exception.PrjExceptionHandler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsagerService {
    final UsagerRepository usagerRepository;
    final PrjExceptionHandler prjExceptionHandler;

    public UsagerService(UsagerRepository usagerRepository, PrjExceptionHandler prjExceptionHandler) {
        this.usagerRepository = usagerRepository;
        this.prjExceptionHandler = prjExceptionHandler;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Usager getUsagerByNom(String nom) {
        Optional<Usager> optionalUsager =usagerRepository.findUsagerByNom(nom);
        if (optionalUsager.isEmpty())
            prjExceptionHandler.throwUsagerUnAuthorized();
        return optionalUsager.get();
    }


    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public UsagerDto getUsagerDTOById(Integer id) {
        Optional<UsagerDto> optionalUsager =usagerRepository.getUsagerByIdusager(id);
        if (optionalUsager.isEmpty())
            prjExceptionHandler.throwUsagerUnAuthorized();

        return optionalUsager.get();
    }
}
