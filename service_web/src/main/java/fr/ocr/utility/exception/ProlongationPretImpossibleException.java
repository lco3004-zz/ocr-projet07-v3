package fr.ocr.utility.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProlongationPretImpossibleException extends RuntimeException {

    public ProlongationPretImpossibleException(String message) {
        super(message);
    }
}
