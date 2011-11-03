package com.cn.common;

import java.sql.Types;

import org.hibernate.Hibernate;

public class MySQLDialect extends org.hibernate.dialect.MySQLDialect {
	public MySQLDialect(){
		super();
		registerHibernateType(Types.REAL, Hibernate.BIG_INTEGER.getName());
		registerHibernateType(Types.REAL, Hibernate.FLOAT.getName());
	}
}
