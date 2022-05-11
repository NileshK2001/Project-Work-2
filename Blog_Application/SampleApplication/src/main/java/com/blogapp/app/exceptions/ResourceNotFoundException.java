package com.blogapp.app.exceptions;

public class ResourceNotFoundException extends RuntimeException{

	String resourceName;
	String fieldName;
	long numericFieldValue;
	String stringFieldValue;
	
	public String getResourceName() {
		return resourceName;
	}


	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}


	public String getFieldName() {
		return fieldName;
	}


	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


	public long getFieldValue() {
		return numericFieldValue;
	}


	public void setFieldValue(long fieldValue) {
		this.numericFieldValue = fieldValue;
	}


	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.numericFieldValue = fieldValue;
	}
	
	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.stringFieldValue = fieldValue;
	}
}
