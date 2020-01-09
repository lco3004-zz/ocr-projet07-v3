/*
 *
 * les 'repositories'
 * findPretBydUserIdWithCriteria est basée sur API Criteria
 * le repo est étendu par cette interface findPretBydUserIdWithCriteria
 *
 *
 */


package fr.ocr.application.pret;

import fr.ocr.application.ouvrage.Ouvrage;
import fr.ocr.application.ouvrage.Ouvrage_;
import fr.ocr.application.user.User;
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
public interface PretCrudRepository extends JpaRepository<Pret,Integer> , JpaSpecificationExecutor<Pret>,PretRepositoryCustom {

    Collection<Tuple> findPretsByUser(@Param("User") User user);

    Optional<Pret> findPretByOuvrageIdouvrageAndUserIduser(int ouvrageIdouvrage, int userIduser);

    List<PretCrudDtoBatch> findPretsByDateEmpruntIsBefore(Date dateCourante);

}

interface PretRepositoryCustom{
    List<PretCrudDtoWeb> findPretBydUserIdWithCriteria(Integer idUsager);
}

class PretRepositoryCustomImpl implements PretRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<PretCrudDtoWeb> findPretBydUserIdWithCriteria(Integer idUser) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PretCrudDtoWeb> criteriaQuery = criteriaBuilder.createQuery(PretCrudDtoWeb.class);

        Root<Pret> pretRoot = criteriaQuery.from(Pret.class);

        Join<Pret, Ouvrage> pretOuvrageJoin = pretRoot.join(Pret_.ouvrageByOuvrageIdouvrage);

        Predicate  predicateUsager = criteriaBuilder.equal(pretRoot.get(Pret_.userIduser),idUser);

        Expression champIdOuvrageViaPret = pretRoot.get(Pret_.ouvrageIdouvrage);
        Expression champIdOuvrage = pretOuvrageJoin.get(Ouvrage_.idouvrage);
        Predicate joiturePredicateOuvrage = criteriaBuilder.equal(champIdOuvrage,champIdOuvrageViaPret);

        pretOuvrageJoin.on(joiturePredicateOuvrage);

        criteriaQuery.where(predicateUsager);

        criteriaQuery.multiselect(
                pretRoot.get(Pret_.ouvrageIdouvrage),
                pretRoot.get(Pret_.userIduser),
                pretRoot.get(Pret_.pretprolonge),
                pretRoot.get(Pret_.dateEmprunt),
                pretOuvrageJoin.get(Ouvrage_.auteur),
                pretOuvrageJoin.get(Ouvrage_.titre)
        );

        TypedQuery<PretCrudDtoWeb> query = entityManager.createQuery(criteriaQuery);
        List<PretCrudDtoWeb> pretCrudDtoWebs = query.getResultList();

        return pretCrudDtoWebs;
    }

}

