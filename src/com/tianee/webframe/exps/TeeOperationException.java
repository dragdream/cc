package com.tianee.webframe.exps;

public class TeeOperationException extends RuntimeException{
	public TeeOperationException(){
		super();
	}
	
	public TeeOperationException(String message){
		super(message);
	}
	
	public TeeOperationException(Exception ex){
		super(ex);
	}
	public TeeOperationException(String message,Exception ex){
		super(message,ex);
	}
}
