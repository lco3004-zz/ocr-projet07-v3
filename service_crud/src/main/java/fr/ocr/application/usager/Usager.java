package fr.ocr.application.usager;

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
@Table(name = "usager", schema = "usager", catalog = "db_projet07")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("UsagerFiltreDynamique")
public class Usager  implements  Serializable{
    @Transient
    static final long serialVersionUID = 2453281303625368221L;

    @Id
    @Column(name = "idusager", nullable = false)
    private int idusager;

    @Basic
    @Column(name = "nom", nullable = false, length = 256)
    private String nom;

    @Basic
    @Column(name = "mdp", nullable = false, length = 1024)
    private String mdp;

    @Basic
    @Column(name = "courriel", nullable = false, length = 1024)
    private String courriel;

    @ToString.Exclude
    @OneToMany(mappedBy = "usagerByUsagerIdusager")
    private Collection<Pret> pretsByIdusager;
}
