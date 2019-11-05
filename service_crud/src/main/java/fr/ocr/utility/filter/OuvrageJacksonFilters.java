package fr.ocr.utility.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OuvrageJacksonFilters<T>  extends EntityJacksonFilters<T> {

    public OuvrageJacksonFilters(@Value("${listeexclusionsjson.ouvrage}") String[] s) {
        super();
        setListOfExceptions(s);
        setNameOfFilter("OuvrageFiltreDynamique");
    }
}
