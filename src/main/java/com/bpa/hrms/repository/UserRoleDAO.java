package com.bpa.hrms.repository;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bpa.hrms.entity.UserRole;

@Repository("UserRoleDAO")
public class UserRoleDAO extends AbstractDao{

	public UserRoleDAO() {
		super();
	}	
	
	
	private DetachedCriteria createUserRoleCriteria(UserRole userRole) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserRole.class);
		
		if (userRole.getRole() != null)
		{
			criteria.add(Restrictions.eq("role", userRole.getRole()));
		}
		if (userRole.getUser() != null)
		{
			criteria.add(Restrictions.eq("user", userRole.getUser()));
		}

		return criteria;

	}

	
	
	
public UserRole searchbyUser(UserRole userRole) {
DetachedCriteria criteria = createUserRoleCriteria(userRole);
return (UserRole) super.getUniqueObject(UserRole.class, criteria);
}
	
	
}
