package com.me.exception;

public class SellerException extends Exception {

	public SellerException(String message)
	{
		super("SellerException-"+message);
	}
	
	public SellerException(String message, Throwable cause)
	{
		super("SellerException-"+message,cause);
	}
	
}