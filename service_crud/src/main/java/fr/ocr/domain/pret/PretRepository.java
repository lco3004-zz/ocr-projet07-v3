package fr.ocr.domain.pret;

import fr.ocr.domain.ouvrage.Ouvrage;
import fr.ocr.domain.ouvrage.Ouvrage_;
import fr.ocr.domain.usager.Usager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface PretRepository extends JpaRepository<Pret,Integer> , JpaSpecificationExecutor<Pret>,PretRepositoryCustom {

    Collection<Tuple> findPretsByUsagerName(@Param("Emprunteur") Usager usager);

    Optional<Pret> findPretByOuvrageIdouvrageAndUsagerIdusager(int ouvrageIdouvrage, int usagerIdusager);

    List<PretDtoBatch> findPretsByDateEmpruntIsBefore(Date dateCourante);

}

interface PretRepositoryCustom{
    List<PretDtoWeb> findPretBydUsagerIdWithCriteria(Integer idUsager);
}

class PretRepositoryCustomImpl implements PretRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<PretDtoWeb> findPretBydUsagerIdWithCriteria(Integer idUsager) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PretDtoWeb> criteriaQuery = criteriaBuilder.createQuery(PretDtoWeb.class);

        Root<Pret> pretRoot = criteriaQuery.from(Pret.class);

        Join<Pret, Ouvrage> pretOuvrageJoin = pretRoot.join(Pret_.ouvrageByOuvrageIdouvrage);

        Predicate  predicateUsager = criteriaBuilder.equal(pretRoot.get(Pret_.usagerIdusager),idUsager);

        Expression champIdOuvrageViaPret = pretRoot.get(Pret_.ouvrageIdouvrage);
        Expression champIdOuvrage = pretOuvrageJoin.get(Ouvrage_.idouvrage);
        Predicate joiturePredicateOuvrage = criteriaBuilder.equal(champIdOuvrage,champIdOuvrageViaPret);

        pretOuvrageJoin.on(joiturePredicateOuvrage);

        criteriaQuery.where(predicateUsager);

        criteriaQuery.multiselect(pretRoot.get(
                Pret_.ouvrageIdouvrage),
                pretRoot.get(Pret_.usagerIdusager),
                pretRoot.get(Pret_.dateEmprunt),
                pretOuvrageJoin.get(Ouvrage_.auteur),
                pretOuvrageJoin.get(Ouvrage_.titre)
        );

        TypedQuery<PretDtoWeb> query = entityManager.createQuery(criteriaQuery);
        List<PretDtoWeb> pretDtoWebs = query.getResultList();

        return pretDtoWebs;
    }

}

