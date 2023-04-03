package model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class Student extends User {

	private String studentID;
	private Integer status;
	
	public static final int STATUS_NEW_USER = 0;
	public static final int STATUS_ACTIVE_USER = 1;
	public static final int STATUS_BAN_USER = 2;
	
	public Student() {
		super();
	}
	
	public Student(String userName, String name, String phoneNumber, String studentID, Integer status) {
		super(userName, name, phoneNumber);
		this.studentID = studentID;
		this.status = status;
	}
	
	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			return ow.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			
		}
		return "";
		
	}
	
}
