package fr.ocr.application.user;

import fr.ocr.application.pret.Pret;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, Integer> idUser;
	public static volatile CollectionAttribute<User, Pret> pretsByIdusager;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> username;

	public static final String ID_USER = "idUser";
	public static final String PRETS_BY_IDUSAGER = "pretsByIdusager";
	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";
	public static final String USERNAME = "username";

}

