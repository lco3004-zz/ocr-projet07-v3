package fr.ocr.application.pret;

import fr.ocr.application.ouvrage.Ouvrage;
import fr.ocr.application.user.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pret.class)
public abstract class Pret_ {

	public static volatile SingularAttribute<Pret, User> userByUserIduser;
	public static volatile SingularAttribute<Pret, Date> dateEmprunt;
	public static volatile SingularAttribute<Pret, Integer> userIduser;
	public static volatile SingularAttribute<Pret, Integer> pretprolonge;
	public static volatile SingularAttribute<Pret, Integer> ouvrageIdouvrage;
	public static volatile SingularAttribute<Pret, Ouvrage> ouvrageByOuvrageIdouvrage;

	public static final String USER_BY_USER_IDUSER = "userByUserIduser";
	public static final String DATE_EMPRUNT = "dateEmprunt";
	public static final String USER_IDUSER = "userIduser";
	public static final String PRETPROLONGE = "pretprolonge";
	public static final String OUVRAGE_IDOUVRAGE = "ouvrageIdouvrage";
	public static final String OUVRAGE_BY_OUVRAGE_IDOUVRAGE = "ouvrageByOuvrageIdouvrage";

}

