package fr.ocr.domain.pret;

import lombok.Value;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Value
public class PretDto {
    private int usagerIdusager;
    private int ouvrageIdouvrage;
    @Temporal(TemporalType.DATE)
    private Date dateEmprunt;
}
