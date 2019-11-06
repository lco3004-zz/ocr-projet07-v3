package fr.ocr.service_batchmail.domain;

import jdk.jfr.DataAmount;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class InfosMailDtoBatch implements Serializable {
    static final long serialVersionUID = 5453281303625368221L;

    String nom;
    String courriel;
    String titre;
    String auteur;
    Date dateEmprunt;
}
