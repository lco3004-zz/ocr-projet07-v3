package fr.ocr.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class PretDtoWeb implements Serializable {

     static final long serialVersionUID = 3453281303625368221L;

     int usagerIdusager;
     int ouvrageIdouvrage;
}
