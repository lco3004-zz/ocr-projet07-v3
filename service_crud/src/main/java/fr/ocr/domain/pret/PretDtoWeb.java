package fr.ocr.domain.pret;


import lombok.Value;

import java.util.Date;

@Value
public class PretDtoWeb {
    private Date dateEmprunt;
    private String auteur;
    private String titre;
}


