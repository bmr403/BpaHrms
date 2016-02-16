package com.bpa.hrms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Null;

@Entity
@Table(name="user")
public class User 
{
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;
	
	@Column(name="user_email")
	private String userEmail;
	
	@Column(name="password")
	private String password;
	
	/*@Column(name="user_name")
	private String userName; 
	
	@Column(name="employee_code")
	private String employeeCode;
	
	@Column(name="employee_department")
	private String employeeDepartment;*/
	
	@Column(name="is_Active")
	private char isActive;
	
	@Column(name="created_by")
	private int createdBy;
	
	@Column(name="created_on")
	private Date createdOn;
	
	@Column(name="updated_by")
	@Null
	private int updatedBy;
	
	@Column(name="updated_on")
	@Null
	private Date updatedOn;
	

	/*@Column(name="manager")
	@Null
	private int managerId;*/
	
	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public String getUserEmail()
	{
		return userEmail;
	}

	public void setUserEmail(String userEmail) 
	{
		this.userEmail = userEmail;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	/*public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	public String getEmployeeCode()
	{
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) 
	{
		this.employeeCode = employeeCode;
	}

	public String getEmployeeDepartment() 
	{
		return employeeDepartment;
	}

	public void setEmployeeDepartment(String employeeDepartment) 
	{
		this.employeeDepartment = employeeDepartment;
	}*/

	public char isActive() 
	{
		return isActive;
	}

	public void setActive(char isActive)
{
		this.isActive = isActive;
	}

	public int getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(int createdBy)
	{
		this.createdBy = createdBy;
	}

	public Date getCreatedOn()
	{
		return createdOn;
	}

	public void setCreatedOn(Date createdOn)
	{
		this.createdOn = createdOn;
	}

	public int getUpdatedBy()
	{
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() 
	{
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) 
	{
		this.updatedOn = updatedOn;
	}

	/*public int getManagerId()
	{
		return managerId;
	}

	public void setManagerId(int managerId) 
	{
		this.managerId = managerId;
	}*/
	
}
