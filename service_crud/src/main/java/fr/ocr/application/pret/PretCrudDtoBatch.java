package fr.ocr.application.pret;

import lombok.Value;

import java.util.Date;

@Value
public class PretCrudDtoBatch {

     int userIduser;
     int ouvrageIdouvrage;
     Date dateEmprunt;
}
