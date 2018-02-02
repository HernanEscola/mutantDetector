package org.magneto.mutantDetector.DTO;

public class ResponseDTO {
   String message;
 
	public ResponseDTO(String message) {
		super();
		this.message = message;
	}
	public ResponseDTO(Exception e) {
		this(e.getMessage());
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
    
}