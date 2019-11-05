package fr.ocr.utility.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OuvrageNotFoundException extends RuntimeException {

    public OuvrageNotFoundException(String message) {
        super(message);
    }
}
