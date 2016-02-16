package com.bpa.hrms.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bpa.hrms.repository.UserDAO;
import com.bpa.hrms.testutil.LoginServiceTest;
import com.bpa.hrms.entity.User;

@Service("UserService")
@Transactional
public class UserService {
	
	protected final Log logger = LogFactory.getLog(UserService.class);
	
	@Autowired
	public UserDAO userDAO;

	public User getUserById(Integer Id) {
		logger.info("In User Service @ getUserById method");
		return userDAO.getUserById(Id);
	}
	
	public User getUserBySearchParameter(User searchUser) {
		logger.info("In User Service @ getUserBySearchParameter method");
		return userDAO.getUserBySearchParameter(searchUser);
	}
	
}
