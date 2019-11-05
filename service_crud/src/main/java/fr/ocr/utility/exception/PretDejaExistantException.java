package fr.ocr.utility.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PretDejaExistantException extends RuntimeException{
    public PretDejaExistantException (String msg) {
        super(msg);
    }
}

