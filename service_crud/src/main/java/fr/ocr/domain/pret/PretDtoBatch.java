package fr.ocr.domain.pret;

import lombok.Value;

import java.util.Date;

@Value
public class PretDtoBatch {
    private int usagerIdusager;
    private int ouvrageIdouvrage;
    private Date dateEmprunt;
}
