package fr.ocr.application.usager;

import lombok.Value;

import java.io.Serializable;


@Value
public class UsagerDtoWeb implements Serializable {
    static final long serialVersionUID = 5453481303625368221L;

    private Integer idusager;
    private String nom;
    private String mdp;
    private String courriel;
}
