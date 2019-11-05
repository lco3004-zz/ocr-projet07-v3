package fr.ocr.domain.usager;

import fr.ocr.domain.usager.Usager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsagerRepository extends JpaRepository<Usager,Integer>
{
    Optional<Usager> findUsagerByNom(String nom);
    Optional<Usager> findUsagerByIdusager(Integer idUsager);

    List<Usager> findAll();
}

