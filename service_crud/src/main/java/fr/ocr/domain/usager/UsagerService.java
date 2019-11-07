package fr.ocr.domain.usager;


import fr.ocr.utility.exception.UsagerNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsagerService {
    final UsagerRepository usagerRepository;

    public UsagerService(UsagerRepository usagerRepository) {
        this.usagerRepository = usagerRepository;
    }

     @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Usager getUsagerByNom(String nom) {
        Optional<Usager> optionalUsager =usagerRepository.findUsagerByNom(nom);
        if (optionalUsager.isEmpty())
            throw new UsagerNotFoundException("Usager inconnu");
        return optionalUsager.get();
    }


    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public UsagerDto getUsagerDTOById(Integer id) {
        Optional<UsagerDto> optionalUsager =usagerRepository.getUsagerByIdusager(id);
        if (optionalUsager.isEmpty())
            throw new UsagerNotFoundException("Usager inconnu");

        return optionalUsager.get();
    }
}
