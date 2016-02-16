package com.bpa.hrms.entity;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "userrole")
public class UserRole {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userrole_id")
	private Integer userRoleId;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	
	
	@ManyToOne
	@JoinColumn(name = "role_id", referencedColumnName = "role_id")
	private Role role;


	public Integer getUserRoleId() {
		return userRoleId;
	}


	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	
	
}
