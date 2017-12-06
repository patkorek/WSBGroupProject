package com.WSBGroupProject.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseDTO {
	
	private String status;
	private List<CodeDTO> code;
	private Object request;
	private Object response;
	
	public ResponseDTO() {
	}
	
	public ResponseDTO(String status, List<CodeDTO> codes, Object request, Object response){
		this.status = status;
		this.code = codes;
		this.request = request;
		this.response = response;
	}
	public ResponseDTO(Object request){
		this.code = new ArrayList<CodeDTO>();
		this.request = request;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<CodeDTO> getCode() {
		return code;
	}
	public void setCode(List<CodeDTO> code) {
		this.code = code;
	}
	public Object getRequest() {
		return request;
	}
	public void setRequest(Object request) {
		this.request = request;
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
	
	public void addCodeDTO(CodeDTO codeDTO) {
		this.code.add(codeDTO);
	}

}
