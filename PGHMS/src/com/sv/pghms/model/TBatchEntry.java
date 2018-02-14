package com.sv.pghms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="t_batchEntry")
public class TBatchEntry {
	
	@Id
	@Column(name="t_regNo")
	private String regNo;
	@Column(name="t_name")
	private String name;
	
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
