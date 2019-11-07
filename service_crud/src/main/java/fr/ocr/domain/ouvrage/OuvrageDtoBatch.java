package fr.ocr.domain.ouvrage;

import lombok.Value;

import java.io.Serializable;

@Value
public class OuvrageDtoBatch implements Serializable {

    static final long serialVersionUID = 3453281303625368221L;

    String titre; String auteur;

}