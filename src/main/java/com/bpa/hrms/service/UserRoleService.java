package com.bpa.hrms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bpa.hrms.entity.UserRole;
import com.bpa.hrms.repository.UserRoleDAO;


@Service("UserRoleService")
@Transactional
public class UserRoleService {

@Autowired
private UserRoleDAO userRoleDao;
	

public UserRole searchbyUser(UserRole userRole) {
		return userRoleDao.searchbyUser(userRole);
}
	
}
