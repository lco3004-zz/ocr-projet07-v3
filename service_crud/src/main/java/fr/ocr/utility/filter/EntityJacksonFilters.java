package fr.ocr.utility.filter;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.Data;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Component;

@Data
@Component
abstract class EntityJacksonFilters<T> {
    private String nameOfFilter;
    private String [] listOfExceptions;

    public MappingJacksonValue filtersOnAttributes  (T x ) {

        SimpleBeanPropertyFilter filtre = SimpleBeanPropertyFilter.serializeAllExcept(getListOfExceptions());

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter(nameOfFilter, filtre);

        MappingJacksonValue filteredReference = new MappingJacksonValue(x);

        filteredReference.setFilters(filterProvider);

        return filteredReference;
    }
}
