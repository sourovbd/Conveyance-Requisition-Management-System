package com.asd.cms.service;

import java.util.List;

import org.springframework.core.task.TaskRejectedException;

import com.asd.cms.model.TRequisition;

public interface RequisitionService {

	public boolean insert(TRequisition requisition);
	public List<TRequisition> getEmployeeInfo(String loginId);
	public boolean updateStatusDate(long id);
	public boolean delete(TRequisition requisition);
	public List<TRequisition> getRequisitionViewDetails(String reqDateFrom,String reqDateTo, String loginId);
}
