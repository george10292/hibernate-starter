package com.dmdev.entity;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UsersChat.class)
public abstract class UsersChat_ extends com.dmdev.entity.AuditableEntity_ {

	public static volatile SingularAttribute<UsersChat, Chat> chat;
	public static volatile SingularAttribute<UsersChat, Long> id;
	public static volatile SingularAttribute<UsersChat, User> user;

	public static final String CHAT = "chat";
	public static final String ID = "id";
	public static final String USER = "user";

}

