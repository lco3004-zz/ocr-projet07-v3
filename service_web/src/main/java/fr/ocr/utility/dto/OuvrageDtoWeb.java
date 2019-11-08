package fr.ocr.utility.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OuvrageDtoWeb implements Serializable {

    static final long serialVersionUID = 3453281303625368221L;

    String titre;
    String auteur;
    Integer quantite;

}