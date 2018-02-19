package com.asd.cms.dao;

import java.util.List;

import com.asd.cms.model.TRequisition;

public interface RequisitionDao {

	public boolean insert(TRequisition requisition);
	public List<TRequisition> getEmployeeInfo(String loginId);
	public boolean updateStatusDate(long id);
	public boolean delete(TRequisition requisition);
	public List<TRequisition> getRequisitionViewDetails(String reqDateFrom,String reqDateTo, String loginId);
}
