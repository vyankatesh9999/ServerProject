/**
 * 
 */
package com.ServerProject.employee.payload;

/**
 * @author Vyanky
 *
 */
public class ApiResponse {
    private Boolean success;
    private String message;

    public ApiResponse( ) {
         
    }

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

	/**
	 * @return the success
	 */
	public Boolean getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

    
}

