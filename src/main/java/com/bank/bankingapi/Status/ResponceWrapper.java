package com.bank.bankingapi.Status;

import java.util.List;

import org.springframework.util.StringUtils;



public class ResponceWrapper<T> {
	Status status;
	List<T> response;
	String message;
    public ResponceWrapper(boolean isSuccess, List<T> response, String message) {
		super();
		this.status = isSuccess? Status.SUCCESS: Status.FAILURE;
		this.response = response;
		StringBuffer msg =new StringBuffer(isSuccess?"operation completed successfully!":"operation failed !!"); 
		msg.append(StringUtils.isEmpty(message)?"":message);
		this.message = msg.toString();
	}
	public Status getStatus() {
		return status;
	}
	public List<T> getResponse() {
		return response;
	}
	public String getMessage() {
		return message;
	}
}
