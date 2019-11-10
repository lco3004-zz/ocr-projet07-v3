package fr.ocr.application.user;


import fr.ocr.exception.PrjExceptionHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    final UserRepository userRepository;
    final PrjExceptionHandler prjExceptionHandler;

    public UserService(UserRepository userRepository, PrjExceptionHandler prjExceptionHandler) {
        this.userRepository = userRepository;
        this.prjExceptionHandler = prjExceptionHandler;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public UserDtoWeb getUserByNom(String nom) {
        Optional<UserDtoWeb> optionalUsager = userRepository.findUserByUserName(nom);
        if (optionalUsager.isEmpty())
            prjExceptionHandler.throwUsagerUnAuthorized();
        return optionalUsager.get();
    }


    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public UserDto getUserDTOById(Integer id) {
        Optional<UserDto> optionalUsager = userRepository.getUserByIdUser(id);
        if (optionalUsager.isEmpty())
            prjExceptionHandler.throwUsagerUnAuthorized();

        return optionalUsager.get();
    }
}
