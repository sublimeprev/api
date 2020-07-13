package com.sublimeprev.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AddressMother.class)
public abstract class AddressMother_ extends com.sublimeprev.api.bases.BaseEntity_ {

	public static volatile SingularAttribute<AddressMother, String> zipcode;
	public static volatile SingularAttribute<AddressMother, Mother> mother;
	public static volatile SingularAttribute<AddressMother, Integer> numberHouse;
	public static volatile SingularAttribute<AddressMother, String> city;
	public static volatile SingularAttribute<AddressMother, String> street;
	public static volatile SingularAttribute<AddressMother, Long> id;
	public static volatile SingularAttribute<AddressMother, String> neighborhood;
	public static volatile SingularAttribute<AddressMother, String> state;
	public static volatile SingularAttribute<AddressMother, String> complement;

	public static final String ZIPCODE = "zipcode";
	public static final String MOTHER = "mother";
	public static final String NUMBER_HOUSE = "numberHouse";
	public static final String CITY = "city";
	public static final String STREET = "street";
	public static final String ID = "id";
	public static final String NEIGHBORHOOD = "neighborhood";
	public static final String STATE = "state";
	public static final String COMPLEMENT = "complement";

}

