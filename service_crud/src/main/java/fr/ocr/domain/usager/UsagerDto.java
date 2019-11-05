package fr.ocr.domain.usager;

import lombok.Value;
import org.springframework.data.repository.NoRepositoryBean;

@Value
@NoRepositoryBean
public class UsagerDto {
    private String nom;
    private String courriel;

}
