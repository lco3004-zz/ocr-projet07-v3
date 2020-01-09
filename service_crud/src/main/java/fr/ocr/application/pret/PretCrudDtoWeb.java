package fr.ocr.application.pret;


import lombok.Value;

import java.util.Date;

@Value
public class PretCrudDtoWeb {
     int ouvrageIdouvrage;
     int userIduser;
     int pretprolonge;
     Date dateEmprunt;
     String auteur;
     String titre;
}


