package fr.ocr.service_batchmail.domain;

import lombok.Data;


import java.io.Serializable;

@Data
public class OuvrageDtoBatch implements Serializable {

    static final long serialVersionUID = 3453281303625368221L;

    String titre;
    String auteur;

}