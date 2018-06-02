package com.me.exception;

public class AdminException extends Exception{
	public AdminException(String message)
	{
		super("AdminException-"+message);
	}
	
	public AdminException(String message, Throwable cause)
	{
		super("AdminException-"+message,cause);
	}
}
