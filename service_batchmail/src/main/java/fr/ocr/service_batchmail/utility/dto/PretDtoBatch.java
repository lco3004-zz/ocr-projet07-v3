package fr.ocr.service_batchmail.utility.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PretDtoBatch implements Serializable {

     static final long serialVersionUID = 3453281303625368221L;

     int usagerIdusager;
     int ouvrageIdouvrage;
     Date dateEmprunt;
}
