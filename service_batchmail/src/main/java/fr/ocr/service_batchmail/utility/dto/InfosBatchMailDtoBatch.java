package fr.ocr.service_batchmail.utility.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class InfosBatchMailDtoBatch implements Serializable {
    static final long serialVersionUID = 5453281303625368221L;

    String username;
    String email;
    String titre;
    String auteur;
    Date dateEmprunt;
}
