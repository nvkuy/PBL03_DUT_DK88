package service;

import java.util.List;
import java.util.Map;

import model.QueryStudentClass;
import model.ResponseObject;
import model.Student;
import model.User;
import repository.ClassRepository;
import repository.GroupRepository;
import repository.UserRepository;

public class ClassService {

	public static ResponseObject changeStudentClass(String token, QueryStudentClass query) {
		Map<String, Object> token_data = TokenService.getDataFromToken(token);
		if (!User.ROLE_CODE_STUDENT.equals((Integer)token_data.get("roleCode")) || !Student.STATUS_ACTIVE_NOGROUP_USER.equals(UserRepository.readStudentStatus((String)token_data.get("studentID"))))
			return new ResponseObject(ResponseObject.RESPONSE_REQUEST_ERROR, "Your account is not active or already join a group!", null);
		if (!query.getTargetID().equals((String)token_data.get("studentID")))
			return new ResponseObject(ResponseObject.RESPONSE_REQUEST_ERROR, "Your not allow to change class!", null);
		Map<String, Object> tmp = ClassRepository.readClassByStudentID(query.getTargetID());
		List<String> oldHaveClass = (List<String>) tmp.get("haveClass");
		String oldWantClass = (String) tmp.get("wantClass");
		if (oldWantClass != null && !oldWantClass.equals(query.getWantClass())) {
			for (String groupID : GroupRepository.groupInvole(query.getTargetID(), oldWantClass)) {
				GroupRepository.delGroup(groupID);
				for (String id : groupID.split("-"))
					GroupService.leaveGroup(id);
			}
		}
		List<String> newClass = query.getHaveClass();
		for (String classID : oldHaveClass) {
			if (newClass.contains(classID)) continue;
			for (String groupID : GroupRepository.groupInvole(query.getTargetID(), classID)) {
				GroupRepository.delGroup(groupID);
				for (String id : groupID.split("-"))
					GroupService.leaveGroup(id);
			}
		}
		ClassRepository.delQueryByTargetID(query.getTargetID());
		return ClassRepository.updateStudentClass(query);
	}
	
	public static ResponseObject readNewQueryClass(String token, Integer curQueryID) {
		Map<String, Object> token_data = TokenService.getDataFromToken(token);
		Integer studentStatus = UserRepository.readStudentStatus((String)token_data.get("studentID"));
		if (!User.ROLE_CODE_STUDENT.equals((Integer)token_data.get("roleCode")) || Student.STATUS_BAN_USER.equals(studentStatus) || Student.STATUS_NEW_USER.equals(studentStatus))
			return new ResponseObject(ResponseObject.RESPONSE_REQUEST_ERROR, "Your account is not active!", null);
		return ClassRepository.readNewQuery(curQueryID);
	}
	
}
