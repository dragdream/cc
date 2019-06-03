package com.tianee.webframe.dialect;

public class SQLServerDialect extends org.hibernate.dialect.SQLServerDialect{
	public SQLServerDialect() {
		super();
		this.registerFunction("bitand", new BitAndFunction());
		this.registerFunction("find_in_set", new FindInSetFunction());
	}
}
