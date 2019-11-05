package fr.ocr.domain.usager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsagerRepository extends JpaRepository<Usager,Integer>
{
    Optional<Usager> findUsagerByNom(String nom);
    Optional<UsagerDto> getUsagerByIdusager(Integer idUsager);

}

