package com.perksoft.icms.exception;

@SuppressWarnings("serial")
public class IcmsCustomException  extends RuntimeException{
	
	public IcmsCustomException() {
		super();
	}
	
	public IcmsCustomException (String messsage, Throwable throwable) {
		super(messsage, throwable);
	}
	
	public IcmsCustomException (String messsage) {
		super(messsage);
	}
	
	public IcmsCustomException (Throwable throwable) {
		super(throwable);
	}
}
