package fr.ocr.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserJacksonFilters<T>  extends EntityJacksonFilters<T>  {

    public UserJacksonFilters(@Value("${listeexclusionsjson.user}") String[] s) {
        super();
        setListOfExceptions(s);
        setNameOfFilter("UserFiltreDynamique");
    }
}
