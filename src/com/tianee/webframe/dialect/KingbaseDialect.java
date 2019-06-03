package com.tianee.webframe.dialect;

public class KingbaseDialect extends org.hibernate.dialect.KingbaseDialect{
	public KingbaseDialect() {
		super();
		this.registerFunction("bitand", new BitAndFunction());
	}
}
