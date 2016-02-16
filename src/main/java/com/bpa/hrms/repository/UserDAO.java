package com.bpa.hrms.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;



import com.bpa.hrms.entity.User;
import com.bpa.hrms.entity.UserRole;



@Repository("UserDAO")
public class UserDAO extends AbstractDao {

	public UserDAO() {
		super();
	}
	
	private DetachedCriteria createUserCriteria(User user,String matchType) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
	
		if (user.getUserEmail()!= null) 
		{
			if (matchType.equalsIgnoreCase("exact"))
			{
				criteria.add(Restrictions.eq("userEmail", ""+user.getUserEmail()+"").ignoreCase());
			}
			else
			{
				criteria.add(Restrictions.like("userEmail",
						"%" + user.getUserEmail() + "%").ignoreCase());
			}
		}
		if (user.getPassword()!= null) 
		{
			if (matchType.equalsIgnoreCase("exact"))
			{
				criteria.add(Restrictions.eq("password", ""+user.getPassword()+"").ignoreCase());
			}
			else
			{
				criteria.add(Restrictions.like("password",
						"%" + user.getPassword() + "%").ignoreCase());
			}
		}
		
		return criteria;

		}

	public User getUserById(Integer Id) {
		return (User) super.find(User.class, Id);
	}
	
	public User getUserBySearchParameter(User searchUser) {
		DetachedCriteria criteria = createUserCriteria(searchUser,"exact");
		return (User) super.getUniqueObject(User.class, criteria);	
	}
	

}
