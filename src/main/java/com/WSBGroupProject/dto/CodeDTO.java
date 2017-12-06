package com.WSBGroupProject.dto;

public class CodeDTO {
	
	private String code;
	private String message;
	
	public CodeDTO(String code, String message) {
		this.code = code;
		this.message = message;
	}
	public CodeDTO() {
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
