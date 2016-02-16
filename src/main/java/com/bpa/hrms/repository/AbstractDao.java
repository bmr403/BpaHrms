package com.bpa.hrms.repository;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

import com.bpa.hrms.repository.AbstractDao;

public abstract class AbstractDao {

	@Autowired
	protected SessionFactory sessionFactory;

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected final Log logger = LogFactory.getLog(AbstractDao.class);
	
	protected List getAllObjectList(Class clazz, DetachedCriteria objectCriteria) {
		List list = null;
		Session session = sessionFactory.openSession();
		logger.info("In abstract DAO get All Object List method");
		try {

			Criteria crit = objectCriteria.getExecutableCriteria(session);
			list = crit.list();

		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.close();
		}
		return list;

	}
	
	
	protected Object getUniqueObject(Class clazz,DetachedCriteria objectCriteria) {
        Object obj = null;
   	 	Session session = sessionFactory.openSession();
   	 logger.info("In abstract DAO getUniqueObject method");
        try {
           
       	 Criteria crit = objectCriteria.getExecutableCriteria(session);
       	 List list =  crit.list();
       	logger.info("In abstract DAO getUniqueObject method List Size :"+list.size());
       	 if (crit.list().size() != 0)
       	 {
       		 obj = list.get(0);
       	 }
       	 
        } catch (HibernateException e) {
            handleException(e);
        } finally {
        	session.close();
        }       
        
        return obj;
    }
	
	protected int getObjectListCount(Class clazz,
			DetachedCriteria objectCriteria) {
		Integer resultTotal = null;
		List rowlist = null;
		Session session = sessionFactory.openSession();
		logger.info("In abstract DAO get Object List Count method");
		try {
			logger.info("AbstractDAO getObjectListCount method");
			Criteria crit = objectCriteria.getExecutableCriteria(session);
			crit.setProjection(Projections.rowCount());
			rowlist = crit.list();
			logger.info("rowlist is :"+rowlist.size());
			if (rowlist.size() > 0) {
				resultTotal = ((Long) rowlist.get(0)).intValue();
				logger.info("Total records: " + resultTotal);
			}

		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.close();
		}
		return resultTotal.intValue();

	}
	
	protected List getObjectListByRange(Class clazz,
			DetachedCriteria objectCriteria) {
		List list = null;
		Session session = sessionFactory.openSession();
		logger.info("In abstract DAO getObjectListByRangeByValue method");
		try {

			Criteria crit = objectCriteria.getExecutableCriteria(session);
			// crit.setFirstResult(start);
			// crit.setMaxResults(limit);
			list = crit.list();

		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.close();
		}
		return list;

	}
	
	
	protected List getObjectListByRangeByValue(Class clazz,
			DetachedCriteria objectCriteria, int start, int limit) {
		List list = null;
		Session session = sessionFactory.openSession();
		logger.info("In abstract DAO getObjectListByRangeByValue method");
		try {

			Criteria crit = objectCriteria.getExecutableCriteria(session);
			crit.setFirstResult(start);
			crit.setMaxResults(limit);
			list = crit.list();

		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.close();
		}
		return list;

	}
	
	protected Object find(Class clazz, Integer id) {
		Object obj = null;
		Session session = sessionFactory.openSession();
		logger.info("In abstract DAO find method");
		try {
			obj = session.get(clazz, id);

		} catch (HibernateException e) {
			handleException(e);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return obj;
	}

	
	
	
	protected Object saveOrUpdatewithReturn(Object obj) {
		Session session = sessionFactory.openSession();
		try {
			logger.info("In abstract DAO saveOrUpdatewithReturn method");
			session.saveOrUpdate(obj);
			session.flush();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.close();
		}
		return obj;
	}

	private void handleException(HibernateException e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	protected void delete(Object obj) {
		Session session = sessionFactory.openSession();
		logger.info("In abstract DAO delete method");
		try {
			session.delete(obj);
			session.flush();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			session.close();
		}
	}
	
	protected Object saveOrUpdate(Object obj) {
		Session session = sessionFactory.openSession();
		logger.info("In abstract DAO saveOrUpdate method");
		try {
			logger.info("before save");
			session.saveOrUpdate(obj);
			logger.info("after save object");
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
			handleException(e);
			logger.info("message");
		} finally {
			session.close();
		}
		return obj;
	}
	
}
