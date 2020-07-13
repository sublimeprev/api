package com.sublimeprev.api.model;

import com.sublimeprev.api.domain.Role;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.sublimeprev.api.bases.BaseEntity_ {

	public static volatile SingularAttribute<User, LocalDate> birthdate;
	public static volatile SingularAttribute<User, String> comments;
	public static volatile SingularAttribute<User, String> city;
	public static volatile SingularAttribute<User, String> phone;
	public static volatile SetAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, String> encryptedPassword;

	public static final String BIRTHDATE = "birthdate";
	public static final String COMMENTS = "comments";
	public static final String CITY = "city";
	public static final String PHONE = "phone";
	public static final String ROLES = "roles";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String USERNAME = "username";
	public static final String ENCRYPTED_PASSWORD = "encryptedPassword";

}

