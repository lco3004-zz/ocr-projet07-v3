/*
* Component :
* spécialise les exceptions Http pour renvoyer un code Http personnalisé
 */

package fr.ocr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
public interface PrjExceptionHandler {

    static  PrjExceptionHandler getInstance() {
        return new PrjExceptionHandlerImpl();
    }

    void throwInternalServeurError(String msg);
    void throwOuvrageNotContentForLoan(String msg);
    void throwOuvrageNotFound() ;
    void throwPretConflict(String msg) ;
    void throwPretNotAcceptable(String msg) ;
    void throwUserUnAuthorized() ;
    void throwProlongationAlreadyReported(String msg) ;
}



class PrjExceptionHandlerImpl implements PrjExceptionHandler{

    public void  throwInternalServeurError(String msg) {
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        class lolo extends RuntimeException{
            public lolo (String msg) {
                super(msg);
            }
        }
        throw  new lolo(msg);
    }

    public void  throwOuvrageNotContentForLoan(String msg) {
        @ResponseStatus(HttpStatus.NO_CONTENT)
        class lolo extends RuntimeException{
            public lolo (String msg) {
                super(msg);
            }
        }
        throw  new lolo(msg);
    }

    public void throwOuvrageNotFound() {
        @ResponseStatus(HttpStatus.NOT_FOUND)
        class lolo extends RuntimeException{
            public lolo () {
                super("Aucun Ouvrage trouvé");
            }
        }
        throw  new lolo();
    }

    public void  throwPretConflict(String msg) {
        @ResponseStatus(HttpStatus.CONFLICT)
        class lolo extends RuntimeException{
            public lolo (String msg) {
                super(msg);
            }
        }
        throw  new lolo(msg);
    }

    public void throwPretNotAcceptable(String msg) {
        @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
        class lolo extends RuntimeException{
            public lolo (String msg) {
                super(msg);
            }
        }
        throw  new lolo(msg);
    }

    public void throwUserUnAuthorized() {
        @ResponseStatus(HttpStatus.UNAUTHORIZED)
        class lolo extends RuntimeException{
            public lolo () {
                super("Usager inconnu");
            }
        }
        throw  new lolo();
    }

    public void throwProlongationAlreadyReported(String msg) {
        @ResponseStatus(HttpStatus.ALREADY_REPORTED)
        class lolo extends RuntimeException{
            public lolo (String msg) {
                super(msg);
            }
        }
        throw  new lolo(msg);
    }
}


