package fr.ocr.front_mvc.model;


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


