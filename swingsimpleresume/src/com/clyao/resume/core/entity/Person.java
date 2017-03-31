package com.clyao.resume.core.entity;

import java.sql.Date;

public class Person {
	
	//简历id
	private int userId;
	
	//简历中用户的姓名
	private String userName;
	
	//简历中用户的性别
	private String sex;
	
	//简历中用户的年龄
	private int age;
	
	//简历中用户的籍贯
	private String nation;
	
	//简历中用户的电话
	private String phone;
	
	//简历中用户的意向职位
	private String position;
	
	//简历中用户的工作年限
	private int workYear;
	
	//简历中用户的身份证号
	private String charer;
	
	//简历中用户的婚姻状况
	private int marriage;
	
	//简历中用户的身高
	private int height;
	
	//简历中用户的毕业学校
	private String school;
	
	//简历中用户的学历
	private String degree;
	
	//简历中用户的地址
	private String address;
	
	//简历中用户的电子邮件
	private String email;
	
	//简历中用户的专业
	private String speciality;
	
	//简历中用户的工作描述
	private String workDetails;
	
	//简历中用户的创建时间
	private Date createDate;
	
	//简历中用户的工作状态
	private Boolean comfalg;
	
	//简历中用户的操作人
	private int operator;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getWorkYear() {
		return workYear;
	}

	public void setWorkYear(int workYear) {
		this.workYear = workYear;
	}

	public String getCharer() {
		return charer;
	}

	public void setCharer(String charer) {
		this.charer = charer;
	}

	public int getMarriage() {
		return marriage;
	}

	public void setMarriage(int marriage) {
		this.marriage = marriage;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getWorkDetails() {
		return workDetails;
	}

	public void setWorkDetails(String workDetails) {
		this.workDetails = workDetails;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getComfalg() {
		return comfalg;
	}

	public void setComfalg(Boolean comfalg) {
		this.comfalg = comfalg;
	}

	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}
	
}
