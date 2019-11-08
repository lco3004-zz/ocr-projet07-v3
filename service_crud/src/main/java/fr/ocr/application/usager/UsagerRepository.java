package fr.ocr.application.usager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsagerRepository extends JpaRepository<Usager,Integer>
{
    Optional<UsagerDtoWeb> findUsagerByNom(String nom);
    Optional<UsagerDto> getUsagerByIdusager(Integer idUsager);

}

