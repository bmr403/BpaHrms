package com.bpa.hrms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bpa.hrms.entity.Role;
import com.bpa.hrms.repository.RoleDAO;



@Service("RoleService")
@Transactional
public class RoleService {

	
@Autowired
private RoleDAO roleDAO;

	public Role getRoleById(Integer Id) {
		return roleDAO.getRoleById(Id);
	}
	
	
	public int getRoleFilterCount(Role role) {
		return roleDAO.getRoleFilterCount(role);
	}
	
	public List getAllRoleList(Role role) {
		return roleDAO.getAllRoleList(role);
	}
	
	public void saveRole(Role role) {
		roleDAO.saveRole(role);
	}
	public void removeRole(Role role) {
		roleDAO.removeRole(role);
	}
	
	public List getPaginationList(Role role, int start, int limit) {
		return roleDAO.getPaginationList(role, start, limit);

	}
	

	
	
}
