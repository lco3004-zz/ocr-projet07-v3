package fr.ocr.service;

import fr.ocr.security.User;
import fr.ocr.utility.InfosConnexionUser;
import org.springframework.stereotype.Service;

@Service
public class SecurityWebService {

    public Boolean acLogin(User user, InfosConnexionUser infosConnexionUser) {
        return user.getPassword().equals(infosConnexionUser.getPassword());
    }
}
