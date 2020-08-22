package com.bank.bankingapi.Status;

public class EntityCreationSatus {
	
	private EntityType type;
	public EntityType getType() {
		return type;
	}
	public Status getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	private Status status;
	private String message;
	public EntityCreationSatus(EntityType type, Status status, String message) {
		super();
		this.type = type;
		this.status = status;
		this.message = message;
	}
	public static EntityCreationSatus createStatusObject(boolean isSuccess, EntityType type)
	{
		return createStatusObject(isSuccess, "", type);
	}
	public static EntityCreationSatus createStatusObject(boolean isSuccess, String message, EntityType type)
	{
		if(isSuccess)
			return new EntityCreationSatus(type, isSuccess?Status.SUCCESS:Status.FAILURE, type.toString() +" successfully created !"+message);
		return new EntityCreationSatus(type, isSuccess?Status.SUCCESS:Status.FAILURE, type.toString() +" creation failed!"+message);
	}
}
