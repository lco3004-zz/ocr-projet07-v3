package fr.ocr.domain.pret;

import lombok.Value;

import java.io.Serializable;
import java.util.Date;

@Value
public class PretDtoBatch implements Serializable {

     static final long serialVersionUID = 3453281303625368221L;

     int usagerIdusager;
     int ouvrageIdouvrage;
     Date dateEmprunt;
}
