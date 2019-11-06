package fr.ocr.service_batchmail.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class UsagerDtoBatch implements Serializable {
    static final long serialVersionUID = 5453281303625368221L;

    private String nom;
    private String courriel;

}
