package fr.ocr.pret;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PretWebDtoWeb implements Serializable {
    static final long serialVersionUID = 8453281303625368221L;

     int ouvrageIdouvrage;
     int userIduser;
     Date dateEmprunt;
     String auteur;
     String titre;
}


