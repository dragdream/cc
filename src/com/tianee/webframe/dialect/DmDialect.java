package com.tianee.webframe.dialect;

public class DmDialect extends org.hibernate.dialect.DmDialect{
	public DmDialect() {
		super();
		this.registerFunction("bitand", new BitAndFunction());
		this.registerFunction("find_in_set", new FindInSetFunction());
	}
}
