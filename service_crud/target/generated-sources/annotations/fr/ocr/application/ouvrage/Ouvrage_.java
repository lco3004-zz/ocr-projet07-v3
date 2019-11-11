package fr.ocr.application.ouvrage;

import fr.ocr.application.pret.Pret;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Ouvrage.class)
public abstract class Ouvrage_ {

	public static volatile SingularAttribute<Ouvrage, String> titre;
	public static volatile SingularAttribute<Ouvrage, Integer> idouvrage;
	public static volatile CollectionAttribute<Ouvrage, Pret> pretsByIdouvrage;
	public static volatile SingularAttribute<Ouvrage, String> auteur;
	public static volatile SingularAttribute<Ouvrage, Integer> quantite;

	public static final String TITRE = "titre";
	public static final String IDOUVRAGE = "idouvrage";
	public static final String PRETS_BY_IDOUVRAGE = "pretsByIdouvrage";
	public static final String AUTEUR = "auteur";
	public static final String QUANTITE = "quantite";

}

