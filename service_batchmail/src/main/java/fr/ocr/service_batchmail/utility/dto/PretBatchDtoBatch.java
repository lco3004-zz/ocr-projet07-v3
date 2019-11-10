package fr.ocr.service_batchmail.utility.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PretBatchDtoBatch implements Serializable {

     static final long serialVersionUID = 3453281303625368221L;

     int userIduser;
     int ouvrageIdouvrage;
     Date dateEmprunt;
}
