package fr.ocr.service;

import fr.ocr.utility.InfosConnexionUsager;
import fr.ocr.utility.dto.User;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public Boolean acLogin(User user, InfosConnexionUsager infosConnexionUsager) {
        return user.getPassword().equals(infosConnexionUsager.getMdp());
    }
}
