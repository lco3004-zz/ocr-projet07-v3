package fr.ocr.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UsagerJacksonFilters<T>  extends EntityJacksonFilters<T>  {

    public UsagerJacksonFilters(@Value("${listeexclusionsjson.usager}") String[] s) {
        super();
        setListOfExceptions(s);
        setNameOfFilter("UsagerFiltreDynamique");
    }
}
