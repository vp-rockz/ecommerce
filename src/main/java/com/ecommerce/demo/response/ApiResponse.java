package com.ecommerce.demo.response;

import lombok.Data;

@Data
public class ApiResponse {
	private String message;
	private Object output;
	
	public ApiResponse(String message, Object output) {
		super();
		this.message = message;
		this.output = output;
	}

}
