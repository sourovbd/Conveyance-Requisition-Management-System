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
@Table(name="t_Expense")
public class TExpense {
	
	@Id 
	@Column(name="expense_Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int expenseId;
	
	@Temporal(TemporalType.DATE)
	@Column(name="expense_ConveyanceDate")
	public Date expenseConveyanceDate;
	@Temporal(TemporalType.DATE)
	@Column(name="expense_SubmitDate")
	public Date expenseSubmitDate;
	@Temporal(TemporalType.DATE)
	@Column(name="expense_ApprovedRejectedDate")
	public Date expenseApprovedRejectedDate;
	@Temporal(TemporalType.DATE)
	@Column(name="expense_PaidDate")
	public Date expensePaidDate;
	
	@Column(name="expense_Department")
	public String expenseDepartment;
	@Column(name="expense_Type")
	public String expenseType;
	@Column(name="expense_Amount")
	public BigDecimal expenseAmount;
	@Column(name="expense_Details")
	public String expenseDetails;
	@Column(name="expense_Status")
	public Integer expenseStatus;
	@Column(name="expense_EmployeeId")
	public String expenseEmployeeId;
	
	public int getExpenseId() {
		return expenseId;
	}
	public void setExpenseId(int expenseId) {
		this.expenseId = expenseId;
	}
	public Date getExpenseConveyanceDate() {
		return expenseConveyanceDate;
	}
	public void setExpenseConveyanceDate(Date expenseConveyanceDate) {
		this.expenseConveyanceDate = expenseConveyanceDate;
	}
	public Date getExpenseSubmitDate() {
		return expenseSubmitDate;
	}
	public void setExpenseSubmitDate(Date expenseSubmitDate) {
		this.expenseSubmitDate = expenseSubmitDate;
	}
	public Date getExpenseApprovedRejectedDate() {
		return expenseApprovedRejectedDate;
	}
	public void setExpenseApprovedRejectedDate(Date expenseApprovedRejectedDate) {
		this.expenseApprovedRejectedDate = expenseApprovedRejectedDate;
	}
	public Date getExpensePaidDate() {
		return expensePaidDate;
	}
	public void setExpensePaidDate(Date expensePaidDate) {
		this.expensePaidDate = expensePaidDate;
	}
	public String getExpenseDepartment() {
		return expenseDepartment;
	}
	public void setExpenseDepartment(String expenseDepartment) {
		this.expenseDepartment = expenseDepartment;
	}
	public String getExpenseType() {
		return expenseType;
	}
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
	public BigDecimal getExpenseAmount() {
		return expenseAmount;
	}
	public void setExpenseAmount(BigDecimal expenseAmount) {
		this.expenseAmount = expenseAmount;
	}
	public String getExpenseDetails() {
		return expenseDetails;
	}
	public void setExpenseDetails(String expenseDetails) {
		this.expenseDetails = expenseDetails;
	}
	public Integer getExpenseStatus() {
		return expenseStatus;
	}
	public void setExpenseStatus(Integer expenseStatus) {
		this.expenseStatus = expenseStatus;
	}
	public String getExpenseEmployeeId() {
		return expenseEmployeeId;
	}
	public void setExpenseEmployeeId(String expenseEmployeeId) {
		this.expenseEmployeeId = expenseEmployeeId;
	}
}
