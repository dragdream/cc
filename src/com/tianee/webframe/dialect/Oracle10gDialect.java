package com.tianee.webframe.dialect;

public class Oracle10gDialect extends org.hibernate.dialect.Oracle10gDialect{
	public Oracle10gDialect() {
		super();
		this.registerFunction("bitand", new OracleBitAndFunction());
		this.registerFunction("find_in_set", new FindInSetFunction());
	}
}
