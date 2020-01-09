package fr.ocr.model;

import lombok.Data;

import java.util.Date;

@Data
public class InfosBatchMailDtoBatch {

    String username;
    String email;
    String titre;
    String auteur;
    Date dateEmprunt;
}
