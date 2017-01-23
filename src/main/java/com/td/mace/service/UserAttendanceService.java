package com.td.mace.service;

import java.util.List;

import com.td.mace.model.UserAttendance;


public interface UserAttendanceService {
	
	UserAttendance findById(int id);
	
	UserAttendance findByUserId(String userId);
	
	UserAttendance findBySSOId(String ssoId);
	
	void saveUserAttendance(UserAttendance userAttendance);
	
	void updateUserAttendance(UserAttendance userAttendance);
	
	void deleteUserAttendanceById(int id);
	
	void deleteUserAttendanceByUserId(int userId);

	List<UserAttendance> findAllUserAttendances();
	
	List<UserAttendance> findAllUserAttendancesBySSOId(String ssoId); 
	
	List<UserAttendance> findAllUserAttendancesUpdated();

}