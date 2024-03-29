package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ResponseObject {
	private int respCode;
	private String message;
	private Object data;
	
	public static final int RESPONSE_OK = 0;
	public static final int RESPONSE_REQUEST_ERROR = 1; 
	public static final int RESPONSE_SYSTEM_ERROR = 2;
	public static final int RESPONSE_TOKEN_EXPIRED = 3;
	public static final int RESPONSE_OUTDATE_DATA = 4;
	
	public ResponseObject() {
		super();
		respCode = RESPONSE_OK;
		message = "OK";
	}

	public ResponseObject(int respCode, String message, Object data) {
		super();
		this.respCode = respCode;
		this.message = message;
		this.data = data;
	}

	public int getRespCode() {
		return respCode;
	}

	public void setRespCode(int respCode) {
		this.respCode = respCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
