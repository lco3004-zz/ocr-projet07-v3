package fr.ocr.utility.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class UsagerDtoWeb implements Serializable {
    static final long serialVersionUID = 5453481303625368221L;

    private Integer idusager;
    private String nom;
    private String mdp;
    private String courriel;
}
