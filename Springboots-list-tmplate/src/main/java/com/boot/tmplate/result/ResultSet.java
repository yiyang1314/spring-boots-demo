package com.boot.tmplate.result;

import lombok.Data;

@Data
public class ResultSet {	

	public ResultSet() {
		super();

	}
	public ResultSet(String message, boolean status) {
		super();
		this.message = message;
		this.status = status;
	}
	private String message;
	private boolean status;
}
