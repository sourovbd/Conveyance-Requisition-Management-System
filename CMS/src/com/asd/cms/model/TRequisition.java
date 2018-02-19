package com.asd.cms.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="t_Requisition")
public class TRequisition {
		
	@Id 
	@Column(name="req_Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int reqId;
	
	@Temporal(TemporalType.DATE)
	@Column(name="req_EntryDate")
	public Date reqEntryDate;
	@Temporal(TemporalType.DATE)
	@Column(name="req_SubmitDate")
	public Date reqSubmitDate;
	@Temporal(TemporalType.DATE)
	@Column(name="req_ApprovedRejectedDate")
	public Date reqApprovedRejectedDate;
	@Temporal(TemporalType.DATE)
	@Column(name="req_OrderedDate")
	public Date reqOrderedDate;
	
	@Column(name="req_Department")
	public String reqDepartment;
	@Column(name="req_Type")
	public String reqType;
	@Column(name="req_Quantity")
	public BigDecimal reqQuantity;
	@Column(name="req_Details")
	public String reqDetails;
	@Column(name="req_Status")
	public Integer reqStatus;
	@Column(name="req_EmployeeId")
	public String reqEmployeeId;
	public int getReqId() {
		return reqId;
	}
	public void setReqId(int reqId) {
		this.reqId = reqId;
	}
	public Date getReqEntryDate() {
		return reqEntryDate;
	}
	public void setReqEntryDate(Date reqEntryDate) {
		this.reqEntryDate = reqEntryDate;
	}
	public Date getReqSubmitDate() {
		return reqSubmitDate;
	}
	public void setReqSubmitDate(Date reqSubmitDate) {
		this.reqSubmitDate = reqSubmitDate;
	}
	public Date getReqApprovedRejectedDate() {
		return reqApprovedRejectedDate;
	}
	public void setReqApprovedRejectedDate(Date reqApprovedRejectedDate) {
		this.reqApprovedRejectedDate = reqApprovedRejectedDate;
	}
	public Date getReqOrderedDate() {
		return reqOrderedDate;
	}
	public void setReqOrderedDate(Date reqOrderedDate) {
		this.reqOrderedDate = reqOrderedDate;
	}
	public String getReqDepartment() {
		return reqDepartment;
	}
	public void setReqDepartment(String reqDepartment) {
		this.reqDepartment = reqDepartment;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public BigDecimal getReqQuantity() {
		return reqQuantity;
	}
	public void setReqQuantity(BigDecimal reqQuantity) {
		this.reqQuantity = reqQuantity;
	}
	public String getReqDetails() {
		return reqDetails;
	}
	public void setReqDetails(String reqDetails) {
		this.reqDetails = reqDetails;
	}
	public Integer getReqStatus() {
		return reqStatus;
	}
	public void setReqStatus(Integer reqStatus) {
		this.reqStatus = reqStatus;
	}
	public String getReqEmployeeId() {
		return reqEmployeeId;
	}
	public void setReqEmployeeId(String reqEmployeeId) {
		this.reqEmployeeId = reqEmployeeId;
	}
}
