package fr.ocr.utility.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PretJacksonFilters<T>  extends EntityJacksonFilters<T>  {
    public PretJacksonFilters(@Value("${listeexclusionsjson.pret}") String[] s) {
        super();
        setListOfExceptions(s);
        setNameOfFilter("PretFiltreDynamique");
    }
}
