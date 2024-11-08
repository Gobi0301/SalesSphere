package com.scube.crm.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.bo.ContactBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.AccountVO;
import com.scube.crm.vo.ContactVO;
@Repository
public class ContactDAOImpl implements ContactDAO{
	
	@Autowired
	private SessionFactory sessionFactory;


	protected Session getSession() {

		return sessionFactory.getCurrentSession();

	}

	static final MySalesLogger LOGGER = MySalesLogger.getLogger(ContactDAOImpl.class);

	@Override
	public long createContact(ContactVO contactVO) throws MySalesException {
		LOGGER.entry();
		long count=0;

		try {

			Session session = getSession();
			count=(long) session.save(contactVO);

		} catch (Exception he) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add contact has failed:" + he.getMessage());
			}
			LOGGER.info("Add contact has failed:" + he.getMessage());
			throw new MySalesException("Add contact has failed:" + he.getMessage());
		}
		finally {
			LOGGER.entry();
		}
		return count;



	}

	@Override
	public long contactCount(ContactBO contact) throws MySalesException {
		// TODO Auto-generated method stub
		LOGGER.entry();
		long id=0;
		try {
			Session session= sessionFactory.getCurrentSession();
			Criteria criteria=session.createCriteria(ContactVO.class);
			criteria.add(Restrictions.eq("isDeleted", false));
			criteria.setProjection(Projections.rowCount());
			id=(long) criteria.uniqueResult();

		} catch (Exception he) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("view contact has failed:" + he.getMessage());
			}
			LOGGER.info("view contact has failed:" + he.getMessage());
			throw new MySalesException("view contact has failed:" + he.getMessage());
		}
		return id;
	}

	@Override
	public List<ContactVO> retrieveContact(ContactBO contact) throws MySalesException {
		// TODO Auto-generated method stub
		LOGGER.entry();
		List<ContactVO> contactList= new ArrayList<ContactVO>();
		try {
			Session session= sessionFactory.getCurrentSession();
			Criteria criteria=session.createCriteria(ContactVO.class);

			criteria.setFirstResult(contact.getRecordIndex());
			criteria.setMaxResults(contact.getMaxRecord());
			criteria.add(Restrictions.eq("isDeleted",false));
			contactList=criteria.list();


		} catch (Exception he) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("view contact has failed:" + he.getMessage());
			}
			LOGGER.info("view contact has failed:" + he.getMessage());
			throw new MySalesException("view contact has failed:" + he.getMessage());
		}
		finally {
			LOGGER.exit();
		}

		return contactList;
	}

	@Override
	public long contactssCount(ContactBO contactbo) throws MySalesException {
		// TODO Auto-generated method stub
		LOGGER.entry();
		long id=0;
		try {
			Session session= sessionFactory.getCurrentSession();
			Criteria criteria=session.createCriteria(ContactVO.class);
			criteria.add(Restrictions.eq("isDeleted", false));
			if (null!=contactbo && null != contactbo.getFirstname() && !contactbo.getFirstname().isEmpty()) {
				criteria.add(Restrictions.ilike("firstname", contactbo.getFirstname(), MatchMode.ANYWHERE));
				
			}
			if (null!=contactbo &&null != contactbo.getEmail() && !contactbo.getEmail().isEmpty()) {
				criteria.add(Restrictions.ilike("email", contactbo.getEmail(), MatchMode.ANYWHERE));
			}			
			else if(null!=contactbo &&null!=contactbo.getOpportunityBO() &&0!=contactbo.getOpportunityBO().getOpportunityId()) {
				criteria.add(Restrictions.eq("opportunity.opportunityId",contactbo.getOpportunityBO().getOpportunityId()));
			 }
			
			criteria.setProjection(Projections.rowCount());
			id=(long) criteria.uniqueResult();
 		} catch (Exception he) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("view count has failed:" + he.getMessage());
			}
			LOGGER.info("view count has failed:" + he.getMessage());
			throw new MySalesException("view count has failed:" + he.getMessage());

		}
		return id;
	}

	@Override
	public List<ContactVO> searchContactsList(ContactVO contactdos) throws MySalesException {
		// TODO Auto-generated method stub
		LOGGER.entry();
		List<ContactVO> contactvo = new ArrayList<ContactVO>();

		try {
			Criteria cr = getSession().createCriteria(ContactVO.class);
			cr.add(Restrictions.eq("isDeleted",false));
			cr.setFirstResult(contactdos.getRecordIndex());
			cr.setMaxResults(contactdos.getMaxRecord());
			if (null != contactdos.getFirstname() && !contactdos.getFirstname().isEmpty()) {
				cr.add(Restrictions.ilike("firstname", contactdos.getFirstname().trim(), MatchMode.ANYWHERE));
			}
			if (null != contactdos.getEmail() && !contactdos.getEmail().isEmpty()) {
				cr.add(Restrictions.ilike("email", contactdos.getEmail().trim(), MatchMode.ANYWHERE));
			}
			 if(null != contactdos.getOpportunity() && null!=contactdos.getOpportunity().getOpportunityId()) {
				  cr.add(Restrictions.eq("opportunity.opportunityId", contactdos.getOpportunity().getOpportunityId()));
			 }
			contactvo = cr.list();
		} catch (Exception he) {
			he.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("search has failed:"+ he);
			}
			LOGGER.info("search has failed:" + he.getMessage());
			throw new MySalesException("search has failed:"+he);
		}finally{
			LOGGER.exit();

		}

		return contactvo;
	}

	@Override
	public ContactVO findById(Long contactId) throws MySalesException {
		// TODO Auto-generated method stub
		LOGGER.entry();
		ContactVO contactVO=new ContactVO ();

		try{
			Criteria cr= getSession().createCriteria(ContactVO.class);
			cr.add(Restrictions.eq("isDeleted", false));
			cr.add(Restrictions.eq("contactId", contactId));
			contactVO=(ContactVO) cr.uniqueResult();
		}catch(Exception e){
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("view has failed: Exception \t"+e);
			}
			throw new MySalesException("view has failed:"+e);
		}
		finally{
			LOGGER.exit();
		}

		return contactVO;
	}

	@Override
	public ContactVO retrievecontact(Long conId) throws MySalesException {
		// TODO Auto-generated method stub
		LOGGER.entry();
		ContactVO contactVO=new ContactVO ();

		try{
			Criteria cr= getSession().createCriteria(ContactVO.class);
			cr.add(Restrictions.eq("isDeleted", false));
			cr.add(Restrictions.eq("contactId", conId));
			contactVO=(ContactVO) cr.uniqueResult();
		}catch(Exception e){
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("view has failed: Exception \t"+e);
			}
			throw new MySalesException("view has failed:"+e);
		}
		finally{
			LOGGER.exit();
		}

		return contactVO;
	}

	@Override
	public boolean updateContact(ContactVO contactVO) throws MySalesException {
		// TODO Auto-generated method stub
		LOGGER.entry();
		boolean status = true;
		
		try {
			Session session = getSession();
			session.saveOrUpdate(contactVO);


		} catch (Exception e) {
			e.printStackTrace();

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("update has failed: Exception \t"+e);
			}
			throw new MySalesException("update has failed: Exception \t"+e);
		} finally {

			LOGGER.exit();
		}
		return status;
	}

	@Override
	public boolean softDeleted(Long ids) throws MySalesException {
		// TODO Auto-generated method stub
		LOGGER.entry();
		int result = 0;
		try {
			Query query = getSession().createQuery("update ContactVO S set S.isDeleted=:isDeleted where S.contactId=:contactId");
			query.setParameter("isDeleted", true);
			query.setParameter("contactId", ids);
			result = query.executeUpdate();
			if (result > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("delete has failed: Exception \t"+e);
			}
			throw new MySalesException("delete has failed: Exception \t"+e);
		} finally {

			LOGGER.exit();
		}
		return false;
	}


	@Override
	public Map<Integer, String> retrieveAccounttList() {
		// TODO Auto-generated method stub
		LOGGER.entry();
		Map<Integer,String> map= new HashMap<>();
		try{
			Session session= sessionFactory.getCurrentSession();
			Criteria criteria=session.createCriteria(AccountVO.class);
			criteria.add(Restrictions.eq("isDeleted", false));

			ProjectionList list=Projections.projectionList();
			list.add(Projections.property("accountId"));
			list.add(Projections.property("accountName"));
			criteria.setProjection(list);

			List<Object[]> listObject=criteria.list();

			if(null!=listObject&&!listObject.isEmpty()) {
				for(Object[] object: listObject) {
					Integer id= (Integer) object[0];
					String name= (String) object[1];
					map.put(id, name);

				}
			}

		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrieve contact has failed:" + e);
			}
			LOGGER.debug("Retrieve contact has failed:" + e);
		} finally {
			LOGGER.exit();
		}
		return map ;
	}


}

