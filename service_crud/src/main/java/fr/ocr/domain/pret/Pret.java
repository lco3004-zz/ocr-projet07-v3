package fr.ocr.domain.pret;

import com.fasterxml.jackson.annotation.JsonFilter;
import fr.ocr.domain.usager.Usager;
import fr.ocr.domain.ouvrage.Ouvrage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "pret", schema = "pret", catalog = "db_projet07")
@IdClass(PretPK.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("PretFiltreDynamique")
@NamedQuery(name = "Pret.findPretsByUsagerName",
            query = "select  p.dateEmprunt , o.auteur , o.titre from Pret p , " +
                    "in (p.ouvrageByOuvrageIdouvrage) as o " +
                    " where p.usagerByUsagerIdusager = :Emprunteur")

public class Pret {

    @Id
    @Column(name = "ouvrage_idouvrage", nullable = false)
    private int ouvrageIdouvrage;

    @Id
    @Column(name = "usager_idusager", nullable = false)
    private int usagerIdusager;

    @Basic
    @Column(name = "pret_prolonge", nullable = false)
    private int pretprolonge;

    @Basic
    @Column(name = "date_emprunt", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateEmprunt;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "ouvrage_idouvrage", referencedColumnName = "idouvrage", nullable = false, insertable=false, updatable=false)
    private Ouvrage ouvrageByOuvrageIdouvrage;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "usager_idusager", referencedColumnName = "idusager", nullable = false, insertable=false, updatable=false)
    private Usager usagerByUsagerIdusager;

    @ToString.Exclude
    @Transient
    private String auteurOuvrage;

    @ToString.Exclude
    @Transient
    private String titreOuvrage;

    public Pret(int idouvrage, int idusager, int pretprolonge, Date dateEmprunt) {
        this.ouvrageIdouvrage=idouvrage;
        this.usagerIdusager=idusager;
        this.pretprolonge=pretprolonge;
        this.dateEmprunt=dateEmprunt;
    }

    @PostLoad
    void chargeInfosOuvrage() {
        auteurOuvrage=ouvrageByOuvrageIdouvrage.getAuteur();
        titreOuvrage=ouvrageByOuvrageIdouvrage.getTitre();
    }

}
