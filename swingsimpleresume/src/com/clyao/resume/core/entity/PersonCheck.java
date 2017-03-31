package com.clyao.resume.core.entity;

import java.util.Date;

public class PersonCheck {
	
	//简历登记的id
	private int id;
	
	//简历登记的电话号码
	private String phone;
	
	//简历登记的阅读数量
	private int checkNum;
	
	//简历登记今天是否阅读
	private int isCheck;
	
	//简历登记的时间
	private Date createDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(int checkNum) {
		this.checkNum = checkNum;
	}

	public int getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(int isCheck) {
		this.isCheck = isCheck;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
