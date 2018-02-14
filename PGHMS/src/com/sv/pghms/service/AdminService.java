package com.sv.pghms.service;

import java.util.List;

import com.sv.pghms.model.TBatchEntry;
import com.sv.pghms.model.TCourseDetails;
import com.sv.pghms.model.TResultForm;
import com.sv.pghms.model.TUser;

public interface AdminService {
	
	//*** Result ***\\
	public boolean deletePreviousRow(String regNoFromPathVariable, String courseNoFromPathVariable, String examHeldFromPathVariable);
	public boolean deletePreviousROW(String regNo, String courseNo, String batchNo);
	public boolean insertResult(TResultForm resultForm);	
	public List<TResultForm> getresultFormList();
	public List<TResultForm> getresultListFromQuery(String courseNo, String examHeld, String batchNo);
	public List<TResultForm> getresultListQuery(String courseNo, String batchNo);
	public List<TResultForm> getresultListFor3Query(String regNo,String courseNo, String batchNo);
	public List<TResultForm> getSingleResultForm(String regNo, String courseNo, String examHeld, String batchNo);
	public boolean deleteSingleResult(String id);
	public boolean deleteSingleStudent(String regNo, String courseNo, String batchNo);
	
	//*** Course ***\\
	public boolean insertCourseDetails(TCourseDetails courseDetails);
	public List<TCourseDetails> getCourseDetailsList();
	public List<TCourseDetails> getSingleCourse(String courseNo,String batchNo);
	public List<TCourseDetails> getCourseListFormQuery(String courseNo,String examHeld, String batchNo);	
	public boolean deleteSingleCourse(String courseNo, String batchNo);
	
	//*** User ***\\
	public boolean insertUser(TUser user);
	public TUser getUserByLoginID(String id); 
	public TUser getSingleUser(String id);
	public List<TUser> getAllUser();
	public boolean deleteSingleUser(String id);
	public boolean activateUser(String userLoginId);
	public boolean deactivateUser(String userLoginId);
	
	//*** Batch Entry ***\\
	public List<TBatchEntry> getBatch();
	public boolean insertBatch(TResultForm resultForm );
	public TBatchEntry getSinglePersonFromBatch(String id);
	public boolean deleteSinglePersonFromBatch(String id);
	public List<TBatchEntry> getBatchListFormQuery(String batchNo);
	
	
	
	
}
