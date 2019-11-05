package fr.ocr.utility.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OuvrageNotAvailableForLoan extends RuntimeException {

    public OuvrageNotAvailableForLoan(String message) {
        super(message);
    }
}
