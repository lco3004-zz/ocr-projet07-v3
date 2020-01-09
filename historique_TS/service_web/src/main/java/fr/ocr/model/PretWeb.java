package fr.ocr.model;


import lombok.Data;

import java.util.Date;

@Data
public class PretWeb {
     int ouvrageIdouvrage;
     int userIduser;
     int pretprolonge;
     Date dateEmprunt;
     String auteur;
     String titre;
}


