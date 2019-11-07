package fr.ocr.application.pret;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PretPK implements Serializable {

    @Column(name = "ouvrage_idouvrage", nullable = false)
    @Id
    private int ouvrageIdouvrage;

    @Column(name = "usager_idusager", nullable = false)
    @Id
    private int usagerIdusager;

}
