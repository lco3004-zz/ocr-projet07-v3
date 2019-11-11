package fr.ocr.application.user;


import fr.ocr.exception.PrjExceptionHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserCrudService {
    final UserCrudRepository userCrudRepository;
    final PrjExceptionHandler prjExceptionHandler;

    public UserCrudService(UserCrudRepository userCrudRepository, PrjExceptionHandler prjExceptionHandler) {
        this.userCrudRepository = userCrudRepository;
        this.prjExceptionHandler = prjExceptionHandler;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public UserCrudDtoWeb getUserByNom(String nom) {
        Optional<UserCrudDtoWeb> optionalUsager = userCrudRepository.findUserByUserName(nom);
        if (optionalUsager.isEmpty())
            prjExceptionHandler.throwUserUnAuthorized();
        return optionalUsager.get();
    }


    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public UserCrudDto getUserDTOById(Integer id) {
        Optional<UserCrudDto> optionalUsager = userCrudRepository.findUserByIdUser(id);
        if (optionalUsager.isEmpty())
            prjExceptionHandler.throwUserUnAuthorized();

        return optionalUsager.get();
    }
}
