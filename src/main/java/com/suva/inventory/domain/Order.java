package com.suva.inventory.domain;

public class Order {

	private long id;

	private String status;
	
	private String failureReason;
	
	public Order() {}

	public Order(long id, String status, String failureReason) {
		super();
		this.id = id;
		this.status = status;
		this.failureReason = failureReason;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

}
