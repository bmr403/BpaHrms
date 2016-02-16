package com.bpa.hrms.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bpa.hrms.entity.Role;




@Repository("RoleDAO")
public class RoleDAO extends AbstractDao {
	
	public RoleDAO() {
		super();
	}

	
	private DetachedCriteria createRoleCriteria(Role role,String matchType) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);
		if (role.getRoleDescription() != null) {
			criteria.add(Restrictions.like("roleDescription",
					"%" + role.getRoleDescription() + "%").ignoreCase());
		}
/*		if (role.getIsActive() != null) {
			criteria.add(Restrictions.eq("isActive", "" + role.getIsActive()
					+ ""));
		}*/
		/*if (role.getOrganization() != null) {
			criteria.add(Restrictions.like("organization",
					"%" + role.getOrganization() + "%").ignoreCase());
		}*/
		if (role.getRoleName() != null) 
		{
			if (matchType.equalsIgnoreCase("exact"))
			{
				criteria.add(Restrictions.eq("roleName", ""+role.getRoleName()+"").ignoreCase());
			}
			else
			{
				criteria.add(Restrictions.like("roleName",
						"%" + role.getRoleName() + "%").ignoreCase());
			}
			
		}

		return criteria;

	}
	
	
	
	public Role getRolesById(Integer Id) {
		return (Role) super.find(Role.class, Id);
	}
	

	public int getRoleFilterCount(Role role) {
		DetachedCriteria criteria = createRoleCriteria(role,"");
		return super.getObjectListCount(Role.class, criteria);
	}
	
	public List getAllRoleList(Role role) {
		DetachedCriteria criteria = createRoleCriteria(role,"");
		criteria.addOrder(Order.desc("roleId"));
		return super.getAllObjectList(Role.class, criteria);
	}
	
	
	public void saveRole(Role role) {
		super.saveOrUpdate(role);
	}
	
	public void removeRole(Role role) {
		super.delete(role);
	}
	
	
	public List getPaginationList(Role role, int start, int limit) {
		DetachedCriteria criteria = createRoleCriteria(role,"");
		criteria.addOrder(Order.asc("roleId"));
		//criteria.add(Restrictions.eq("organization", role.getOrganization()));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setFetchMode("testSuites", FetchMode.SELECT); // We expect few projects will not have test suites so we are using select.
		return super.getObjectListByRangeByValue(Role.class, criteria,
				start, limit);

	}
	
	public Role getRoleById(Integer Id) {
		return (Role) super.find(Role.class, Id);
	}
	
	

}
