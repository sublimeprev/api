package com.sublimeprev.api.model;

import com.sublimeprev.api.domain.MaritalStatus;
import com.sublimeprev.api.domain.Schooling;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Mother.class)
public abstract class Mother_ extends com.sublimeprev.api.bases.BaseEntity_ {

	public static volatile SingularAttribute<Mother, String> fatherName;
	public static volatile SingularAttribute<Mother, LocalDate> birthdate;
	public static volatile SingularAttribute<Mother, String> phone;
	public static volatile SingularAttribute<Mother, String> rg;
	public static volatile SingularAttribute<Mother, String> name;
	public static volatile SingularAttribute<Mother, String> cpf;
	public static volatile SingularAttribute<Mother, String> motherName;
	public static volatile SingularAttribute<Mother, Long> id;
	public static volatile SingularAttribute<Mother, String> pis;
	public static volatile SingularAttribute<Mother, Schooling> schooling;
	public static volatile SingularAttribute<Mother, String> email;
	public static volatile SingularAttribute<Mother, MaritalStatus> maritalStatus;

	public static final String FATHER_NAME = "fatherName";
	public static final String BIRTHDATE = "birthdate";
	public static final String PHONE = "phone";
	public static final String RG = "rg";
	public static final String NAME = "name";
	public static final String CPF = "cpf";
	public static final String MOTHER_NAME = "motherName";
	public static final String ID = "id";
	public static final String PIS = "pis";
	public static final String SCHOOLING = "schooling";
	public static final String EMAIL = "email";
	public static final String MARITAL_STATUS = "maritalStatus";

}

