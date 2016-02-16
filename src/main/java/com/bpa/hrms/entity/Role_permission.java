package com.bpa.hrms.entity;

import java.security.Permission;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ROLEPERMISSION")
public class Role_permission {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ROLEPERMISSION_ID")
	private Integer rolepermission ;
	
	@ManyToOne
	@JoinColumn(name = "roleId", referencedColumnName = "role_id")
	private Role role;
	
	
	@ManyToOne
	@JoinColumn(name = "permissionId", referencedColumnName = "Permission_id")
	private Permission permission;


	public Integer getRolepermission() {
		return rolepermission;
	}


	public void setRolepermission(Integer rolepermission) {
		this.rolepermission = rolepermission;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public Permission getPermission() {
		return permission;
	}


	public void setPermission(Permission permission) {
		this.permission = permission;
	}


}
