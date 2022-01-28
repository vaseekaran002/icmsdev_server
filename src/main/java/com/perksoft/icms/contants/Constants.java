package com.perksoft.icms.contants;

public class Constants {
	
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAILURE";
	public static final String EXCEPTION = "FAILURE";
	public static final String SUCCESS_MESSAGE = "Successfully retrieved records";
	
	public static final String STATUS_ACTIVE = "ACTIVE";
	public static final String STATUS_INACTIVE = "INACTIVE";
	
	public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    public static final String ROLES = "ROLES";
    
    public static final String RECORD_TYPE = "$type$";
    public static final String RECORD_TYPE_MUSICIAN = "Musician";
    public static final String RECORD_TYPE_CONTRACT = "Contract";
    public static final String RECORD_TYPE_INVOICE = "Invoice";
}