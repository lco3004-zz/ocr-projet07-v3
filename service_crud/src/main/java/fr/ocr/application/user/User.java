package fr.ocr.application.user;

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
@Table(name = "user", schema = "usager", catalog = "db_projet07")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("UserFiltreDynamique")
public class User implements  Serializable{
    @Transient
    static final long serialVersionUID = 2453281303625368221L;

    @Id
    @Column(name = "iduser", nullable = false)
    private int idUser;

    @Basic
    @Column(name = "user_name", nullable = false, length = 256)
    private String userName;

    @Basic
    @Column(name = "password", nullable = false, length = 1024)
    private String password;

    @Basic
    @Column(name = "email", nullable = false, length = 1024)
    private String email;

    @ToString.Exclude
    @OneToMany(mappedBy = "userByUserIduser")
    private Collection<Pret> pretsByIdusager;
}
