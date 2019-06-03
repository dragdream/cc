package com.tianee.webframe.dialect;

import java.util.List;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.Type;

public class FindInSetFunction implements SQLFunction {

	@Override
	public boolean hasArguments() {
		return true;
	}

	@Override
	public boolean hasParenthesesIfNoArguments() {
		return true;
	}

	@Override
	public Type getReturnType(Type firstArgumentType, Mapping mapping)
			throws QueryException {
		return org.hibernate.type.StringType.INSTANCE;
	}

	@Override
	public String render(Type firstArgumentType, List arguments,
			SessionFactoryImplementor factory) throws QueryException {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException(
					"FindInSetFunction requires 2 arguments!");
		}
		String x = "find_in_set("+arguments.get(0).toString()+","+arguments.get(1).toString()+")";
		return x;
	}

}
