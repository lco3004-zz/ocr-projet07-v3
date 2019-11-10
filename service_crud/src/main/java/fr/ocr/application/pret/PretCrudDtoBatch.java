package fr.ocr.application.pret;

import lombok.Value;

import java.io.Serializable;
import java.util.Date;

@Value
public class PretCrudDtoBatch implements Serializable {

     static final long serialVersionUID = 3453281303625368221L;

     int userIduser;
     int ouvrageIdouvrage;
     Date dateEmprunt;
}
