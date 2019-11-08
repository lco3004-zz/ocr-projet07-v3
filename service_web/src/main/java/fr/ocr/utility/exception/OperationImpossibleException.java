package fr.ocr.utility.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class OperationImpossibleException extends RuntimeException {


    public OperationImpossibleException(String message) {
        super(message);
    }
}
