/*
 *
 * les 'repositories'
 *
 */


package fr.ocr.application.ouvrage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OuvrageCrudRepository extends JpaRepository<Ouvrage, Integer>
{
     List<Ouvrage> findAll();

     List<Ouvrage> findOuvrageByAuteurLikeIgnoreCase(String gAuteur);

     List<Ouvrage> findOuvrageByTitreLikeIgnoreCase(String gTitre);

     List<Ouvrage> findOuvrageByAuteurLikeIgnoreCaseAndTitreLikeIgnoreCase(String gAuteur,String gTitre);

     Optional<Ouvrage> findOuvrageByIdouvrage(Integer idOuvrage);

     Optional<OuvrageCrudDtoBatch> findOuvrageDtoByIdouvrage(Integer id);

}

