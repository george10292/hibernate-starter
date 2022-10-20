package com.dmdev.entity;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PersonalInfo.class)
public abstract class PersonalInfo_ {

	public static volatile SingularAttribute<PersonalInfo, LocalDate> birth_day;
	public static volatile SingularAttribute<PersonalInfo, String> last_name;
	public static volatile SingularAttribute<PersonalInfo, String> first_name;

	public static final String BIRTH_DAY = "birth_day";
	public static final String LAST_NAME = "last_name";
	public static final String FIRST_NAME = "first_name";

}

