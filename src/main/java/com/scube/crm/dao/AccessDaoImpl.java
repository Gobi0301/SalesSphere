package com.scube.crm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.AccessVo;
import com.scube.crm.vo.RolesVO;

@Repository
public class AccessDaoImpl implements AccessDao {
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(AccessDaoImpl.class);
	@Autowired
	private SessionFactory sessionfac;

	@Override
	public AccessVo accessCreateValue(AccessVo acccessVo)throws MySalesException {
		LOGGER.entry();
		try {
		Session session=sessionfac.getCurrentSession();
		session.save(acccessVo);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("accessCreateValue has failed:" + ex.getMessage());
			}
			LOGGER.info("accessCreateValue has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return acccessVo;
	}

	@Override
	public List<AccessVo> viewAccessDao() throws MySalesException {
		LOGGER.entry();
		List<AccessVo> accessVo=new ArrayList<AccessVo>();
		try {
			Session session=sessionfac.getCurrentSession();
			Criteria criteria=session.createCriteria(AccessVo.class);
			 
			accessVo=criteria.list();
			 
		}catch (Exception ex) { 
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("viewAccessDao has failed:" + ex.getMessage());
			}
			LOGGER.info("viewAccessDao has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return accessVo;
	}

	@Override
	public AccessVo updateDao(AccessVo accessVo) throws MySalesException{
		LOGGER.entry();
		try {
		Session session=sessionfac.getCurrentSession();
		session.saveOrUpdate(accessVo);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("updateDao has failed:" + ex.getMessage());
			}
			LOGGER.info("updateDao has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return accessVo;
	}

	@Override
	public AccessVo deleteAccess(AccessVo accessvo) throws MySalesException{
		LOGGER.entry();
		try {
		Session session=sessionfac.getCurrentSession();
		session.delete(accessvo);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("deleteAccess has failed:" + ex.getMessage());
			}
			LOGGER.info("deleteAccess has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return accessvo;
	}

	@Override
	public List<AccessVo> searchAccessValue(AccessVo accessvo)throws MySalesException {
		LOGGER.entry();
		List<AccessVo>accesslistVo=new ArrayList<AccessVo>();
		try {
			Session session = sessionfac.getCurrentSession();
			Criteria criteria = session.createCriteria(AccessVo.class);
			if(null!=accessvo && null !=accessvo.getAccessName() && !accessvo.getAccessName().isEmpty()) {
				criteria.add(Restrictions.ilike("accessName", accessvo.getAccessName().trim(), MatchMode.ANYWHERE));
			}
			accesslistVo=criteria.list();
			if(null!=accesslistVo && !accesslistVo.isEmpty() && accesslistVo.size()>0) {
				return accesslistVo;
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("searchAccessValue has failed:" + ex.getMessage());
			}
			LOGGER.info("searchAccessValue has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return accesslistVo;
	}

	@Override
	public boolean checkAccessName(String accessName) throws MySalesException {
		LOGGER.entry();
		AccessVo accessVo=null;
		try {
			Session session=sessionfac.getCurrentSession();
			Criteria criteria = session.createCriteria(AccessVo.class);
			criteria.add(Restrictions.eq("accessName",accessName));
			accessVo= (AccessVo) criteria.uniqueResult();

			if(null!=accessVo) {
				return true;
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkAccessName has failed:" + ex.getMessage());
			}
			LOGGER.info("checkAccessName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return false;
	}

	@Override
	public long countOfAccess(AccessVo accessVo) {
		long countOfAccess=0;
		Session session=sessionfac.getCurrentSession();	
		try {
			Criteria criteria=session.createCriteria(AccessVo.class);
			if( null!=accessVo.getAccessName() && !accessVo.getAccessName().isEmpty()) {
				criteria.add(Restrictions.ilike("accessName", accessVo.getAccessName().trim(), MatchMode.ANYWHERE));
			}
			criteria.add(Restrictions.eq("isDeleted", false));
			criteria.setProjection(Projections.rowCount());
			countOfAccess=(long)criteria.uniqueResult();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return countOfAccess;
	}

	@Override
	public List<AccessVo> listOfAccessByPagination(AccessVo accessVo) {
		
		List<AccessVo> accessVOList=new ArrayList<>();
		Session session=sessionfac.getCurrentSession();	
		try {
			Criteria criteria=session.createCriteria(AccessVo.class);
			if( null!=accessVo.getAccessName() && !accessVo.getAccessName().isEmpty()) {
				criteria.add(Restrictions.ilike("accessName", accessVo.getAccessName().trim(), MatchMode.ANYWHERE));
			}
			criteria.add(Restrictions.eq("isDeleted", false));
			criteria.setFirstResult(accessVo.getRecordIndex());
			criteria.setMaxResults(accessVo.getMaxRecord());
			accessVOList=criteria.list();
			if(null!= accessVOList) {
				return accessVOList;
			}
			
		}catch (Exception e) {
			System.out.println(e);
		}		
		return accessVOList;
	}

	@Override
	public long countOfAccessBySearch(AccessVo accessVo) {
		LOGGER.entry();
		long countOfAccess=0;
		Session session=sessionfac.getCurrentSession();
		try {
			Criteria criteria=session.createCriteria(AccessVo.class);
			criteria.add(Restrictions.eq("isDeleted", false));
			if( null!=accessVo.getAccessName() && !accessVo.getAccessName().isEmpty()) {
				criteria.add(Restrictions.ilike("accessName", accessVo.getAccessName().trim(), MatchMode.ANYWHERE));
			}
				criteria.setProjection(Projections.rowCount());
				countOfAccess=(long)criteria.uniqueResult();
				
				if(0< countOfAccess) {
					return countOfAccess;
				}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}	
		return countOfAccess;
	}

	@Override
	public AccessVo getAccessId(AccessVo accessVO) {
		LOGGER.entry();
		AccessVo accessVo = new AccessVo();
		Session session = sessionfac.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(AccessVo.class);
			criteria.add(Restrictions.eq("accessId",accessVO.getAccessId()));
			accessVo=(AccessVo) criteria.uniqueResult();
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return accessVo;
	}


}
