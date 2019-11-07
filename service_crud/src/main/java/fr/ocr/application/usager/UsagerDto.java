package fr.ocr.application.usager;

import lombok.Value;

import java.io.Serializable;


@Value
public class UsagerDto implements Serializable {
    static final long serialVersionUID = 5453281303625368221L;

    private String nom;
    private String courriel;

}
