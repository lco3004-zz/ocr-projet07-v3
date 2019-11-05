package fr.ocr.domain.ouvrage;

import fr.ocr.domain.ouvrage.Ouvrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OuvrageRepository extends JpaRepository<Ouvrage, Integer>
{
     List<Ouvrage> findAll();

     List<Ouvrage> findOuvrageByAuteurLike(String gAuteur);

     List<Ouvrage> findOuvrageByTitreLike(String gTitre);

     Optional<Ouvrage> findOuvrageByIdouvrage(Integer idOuvrage);

}

