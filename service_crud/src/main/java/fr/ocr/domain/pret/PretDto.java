package fr.ocr.domain.pret;

import lombok.Value;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Value
@NoRepositoryBean
public class PretDto {
    @Temporal(TemporalType.DATE)
    Date  dateEmprunt;
    String auteur;
    String titre;
    String email;

}
