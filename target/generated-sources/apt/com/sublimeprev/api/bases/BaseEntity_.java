package com.sublimeprev.api.bases;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BaseEntity.class)
public abstract class BaseEntity_ {

	public static volatile SingularAttribute<BaseEntity, LocalDateTime> createdAt;
	public static volatile SingularAttribute<BaseEntity, Boolean> deleted;
	public static volatile SingularAttribute<BaseEntity, String> updatedBy;
	public static volatile SingularAttribute<BaseEntity, String> createdBy;
	public static volatile SingularAttribute<BaseEntity, LocalDateTime> updatedAt;

	public static final String CREATED_AT = "createdAt";
	public static final String DELETED = "deleted";
	public static final String UPDATED_BY = "updatedBy";
	public static final String CREATED_BY = "createdBy";
	public static final String UPDATED_AT = "updatedAt";

}

