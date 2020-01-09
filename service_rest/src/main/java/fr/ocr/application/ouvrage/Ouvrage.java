/*
*
* Model
*
 */

package fr.ocr.application.ouvrage;

import com.fasterxml.jackson.annotation.JsonFilter;
import fr.ocr.application.pret.Pret;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "ouvrage", schema = "ouvrage", catalog = "db_projet07")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("OuvrageFiltreDynamique")
public class Ouvrage  implements Serializable {

    @Transient
    static final long serialVersionUID = 2453281303625368221L;

    @Id
    @Column(name = "idouvrage", nullable = false)
    private int idouvrage;

    @Basic
    @Column(name = "titre", nullable = false, length = 2048)
    private String titre;

    @Basic
    @Column(name = "auteur", nullable = false, length = 256)
    private String auteur;

    @Basic
    @Column(name = "quantite", nullable = false)
    private int quantite;

    @ToString.Exclude
    @OneToMany(mappedBy = "ouvrageByOuvrageIdouvrage")
    private Collection<Pret> pretsByIdouvrage;
}
