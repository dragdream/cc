package com.tianee.webframe.dialect;

public class MySQLDialect extends org.hibernate.dialect.MySQLDialect {
	public MySQLDialect() {
		super();
		this.registerFunction("bitand", new BitAndFunction());
		this.registerFunction("find_in_set", new FindInSetFunction());
	}
}
