package com.asd.cms.model;

import java.math.BigDecimal;

public class TAdminView {

	private String AdminViewId;
	private String AdminConveyanceViewDate;
	private String AdminSubmitViewDate;
	private String AdminRequisitionViewDate;
	private String AdminSubmitReqViewDate;
	private String AdminViewName;
	private BigDecimal AdminViewAmount;
	private BigDecimal AdminViewQuantity;
	
	public String getAdminViewId() {
		return AdminViewId;
	}
	public void setAdminViewId(String empId) {
		AdminViewId = empId;
	}
	public String getAdminViewName() {
		return AdminViewName;
	}
	public void setAdminViewName(String adminViewName) {
		AdminViewName = adminViewName;
	}
	public BigDecimal getAdminViewAmount() {
		return AdminViewAmount;
	}
	public void setAdminViewAmount(BigDecimal adminViewAmount) {
		AdminViewAmount = adminViewAmount;
	}
	public String getAdminConveyanceViewDate() {
		return AdminConveyanceViewDate;
	}
	public void setAdminConveyanceViewDate(String adminConveyanceViewDate) {
		AdminConveyanceViewDate = adminConveyanceViewDate;
	}
	public String getAdminSubmitViewDate() {
		return AdminSubmitViewDate;
	}
	public void setAdminSubmitViewDate(String adminSubmitViewDate) {
		AdminSubmitViewDate = adminSubmitViewDate;
	}
	public String getAdminRequisitionViewDate() {
		return AdminRequisitionViewDate;
	}
	public void setAdminRequisitionViewDate(String adminRequisitionViewDate) {
		AdminRequisitionViewDate = adminRequisitionViewDate;
	}
	public BigDecimal getAdminViewQuantity() {
		return AdminViewQuantity;
	}
	public void setAdminViewQuantity(BigDecimal adminViewQuantity) {
		AdminViewQuantity = adminViewQuantity;
	}
	public String getAdminSubmitReqViewDate() {
		return AdminSubmitReqViewDate;
	}
	public void setAdminSubmitReqViewDate(String adminSubmitReqViewDate) {
		AdminSubmitReqViewDate = adminSubmitReqViewDate;
	}
	
}	
