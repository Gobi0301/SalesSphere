package com.scube.crm.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.service.GenericService;
import com.scube.crm.utils.ErrorCodes;
import com.scube.crm.vo.BODTO;
import com.scube.crm.vo.BasicEntity;

/**
 * This class to be form an common function for this application
 * 
 * @author Scube Technologies
 * @param <E>
 */
@Repository("genericDAO")
public abstract class GenericDAOImpl<T extends BasicEntity> implements GenericDAO<T> {

	private static final MySalesLogger LOGGER = MySalesLogger
			.getLogger(GenericDAOImpl.class);

	protected abstract GenericService<T> getBasicService();

	@Autowired
	private SessionFactory sessionFactory;

	private final Class<BasicEntity> tClass;
	
	
	protected Session getSession() {
	
		return sessionFactory.getCurrentSession();
		
	}

	

	@SuppressWarnings("unchecked")
	public GenericDAOImpl() throws MySalesException {
		this.tClass = (Class<BasicEntity>) new com.scube.crm.utils.GenericTypeResolver<T>()
				.resolveGenericType(this.getClass());
	}

	/**
	 * This method save the data in data base in any entity
	 * 
	 * @param entity
	 * @throws MySalesException
	 * @return entity
	 */

	@Override
	public T create(T entity) throws MySalesException {
		GenericDAOImpl.LOGGER.entry();
		try {

			getSession().save(entity);
			getSession().flush();
		} catch (final HibernateException he) {
			if (GenericDAOImpl.LOGGER.isDebugEnabled()) {
				GenericDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL,
					ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {
			
			
			GenericDAOImpl.LOGGER.exit();
		}

		return entity;
	}

	@Override
	public void create(List<BODTO<T>> list) throws MySalesException {

	
		if (null == list) {
			// log.error("list must not be null");
			throw new MySalesException("Input Parametrs Cannot be Empty");
		}
		int i = 0;
		for (final BODTO<T> entity : list) {
			try {
				getSession().save(entity);
				if (i++ % 30 == 0) {
					getSession().flush();

				}
			} catch (final HibernateException he) {
				if (GenericDAOImpl.LOGGER.isDebugEnabled()) {
					GenericDAOImpl.LOGGER
							.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
				}
				throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL,
						ErrorCodes.ENTITY_CRE_FAIL_MSG);
			} finally {
				
				
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(Long id) throws MySalesException {

		try {
			
			final T entity = (T) getSession().get(this.tClass, id);
			getSession().delete(entity);
			getSession().flush();
		} catch (final HibernateException he) {
			// log.debug(he.getStackTrace());
		}

	}

	public void delete(List<BODTO<T>> list) throws MySalesException {

		
		if (null == list) {
			// log.error("list must not be null");
			throw new MySalesException("Input Parametrs Cannot be Empty");
		}
		int i = 0;
		for (final BODTO<T> to : list) {
			try {
				getSession().delete(to);
				if (i++ % 30 == 0) {
					getSession().flush();
					
				}
			} catch (final IllegalArgumentException iaex) {
				// log.error(iaex.getMessage(), iaex);
				throw new MySalesException(" aruguments are not valid!");
			} catch (final Throwable t) {
				// log.error(t.getMessage(), t);
				throw new MySalesException(" aruguments are not valid!");
			}
		}

	}

	@Override
	public void update(T entity) throws MySalesException {

		if (entity == null) {
			// log.error("entity must not be null");
			throw new MySalesException("Input Parameters Cannot be Empty ");
		}
		
		getSession().update(entity);
		getSession().flush();
	}

	@Override
	public void update(List<BODTO<T>> list) throws MySalesException {

	
		if (null == list) {
			// log.error("list must not be null");
			throw new MySalesException("Input Parametrs Cannot be Empty");
		}
		int i = 0;
		for (final BODTO<T> to : list) {
			try {
				getSession().update(to);
				if (i++ % 30 == 0) {
					getSession().flush();
					
				}
			} catch (final IllegalArgumentException iaex) {
				// log.error(iaex.getMessage(), iaex);
				throw new MySalesException(" aruguments are not valid!");
			} catch (final Throwable t) {
				// log.error(t.getMessage(), t);
				throw new MySalesException(" aruguments are not valid!");
			}

		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByDate(Date fDate, Date tDate)
			throws MySalesException {

		
		final Criteria cr = getSession().createCriteria(this.tClass);
		cr.add(Restrictions.between("createdDate", fDate, tDate));
		final List<T> entityResponse = cr.list();

		return entityResponse;
	}

	@Override
	public T findById(Long id) throws MySalesException {

		
		@SuppressWarnings("unchecked")
		final T entity = (T) getSession().get(this.tClass, id);

		return entity;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public T findByParam(String entityParam, String entityParamValue)
			throws MySalesException {
		GenericDAOImpl.LOGGER.entry();

		
		T entityResponse = null;
		try {
			final Criteria cr = getSession().createCriteria(this.tClass);
			if (cr == null) {
				entityResponse = (T) cr;
			}
			cr.add(Restrictions.eq(entityParam, entityParamValue));
			if (0 != cr.list().size()) {

				entityResponse = (T) cr.list().get(0);
			}
			if (null == entityResponse) {
				GenericDAOImpl.LOGGER
						.error(" No Entity exist for given param!");
			}
		} catch (final IllegalArgumentException iaex) {
			if (GenericDAOImpl.LOGGER.isDebugEnabled()) {
				GenericDAOImpl.LOGGER.debug(ErrorCodes.LOGIN_FAIL + iaex);
			}
			throw new MySalesException(ErrorCodes.LOGIN_FAIL,
					ErrorCodes.LOGIN_FAIL_MSG);
		} catch (final HibernateException he) {
			he.printStackTrace();
			if (GenericDAOImpl.LOGGER.isDebugEnabled()) {
				GenericDAOImpl.LOGGER.debug(ErrorCodes.LOGIN_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.LOGIN_FAIL,
					ErrorCodes.LOGIN_FAIL_MSG);
		} catch (final Exception e) {
			e.printStackTrace();
			if (GenericDAOImpl.LOGGER.isDebugEnabled()) {
				GenericDAOImpl.LOGGER.debug(ErrorCodes.LOGIN_FAIL + e);
			}
			throw new MySalesException(ErrorCodes.LOGIN_FAIL,
					ErrorCodes.LOGIN_FAIL_MSG);
		}
		GenericDAOImpl.LOGGER.exit();
		return entityResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> retrieveAll() throws MySalesException {		
		final Criteria cr = getSession().createCriteria(this.tClass);
		cr.add(Restrictions.eq("isActive", true));
		final List<T> ul = cr.list();
		return ul;
	}

	

	

}
