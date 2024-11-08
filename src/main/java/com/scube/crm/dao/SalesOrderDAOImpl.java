package com.scube.crm.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.bo.GstBO;
import com.scube.crm.bo.SalesOrderBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.utils.ErrorCodes;
import com.scube.crm.vo.AccountVO;
import com.scube.crm.vo.Campaign;
import com.scube.crm.vo.Company;
import com.scube.crm.vo.GstVO;
import com.scube.crm.vo.PaymentVO;
import com.scube.crm.vo.PriceBookVO;
import com.scube.crm.vo.SalesOrderVO;

@Repository
public class SalesOrderDAOImpl extends BaseDao implements SalesOrderDAO {
	static final MySalesLogger LOGGER = MySalesLogger.getLogger(SalesOrderDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	Session getSession() {
		return sessionFactory.getCurrentSession();

	}

	public List<SalesOrderVO> retriveSalesOrders(SalesOrderBO salesOrderBO) {

		Session session = getSession();
		Criteria cr = session.createCriteria(SalesOrderVO.class);
		if(null != salesOrderBO.getCompanyId()&& 0< salesOrderBO.getCompanyId()) {
		cr.add(companyValidation(salesOrderBO.getCompanyId())); // Companey
		}
		if(null != salesOrderBO.getSalesOrderNo()&& !salesOrderBO.getSalesOrderNo().isEmpty()) {
			cr.add(Restrictions.ilike("salesOrderNo", salesOrderBO.getSalesOrderNo().trim(),MatchMode.ANYWHERE));
		}
		cr.setFirstResult(salesOrderBO.getRecordIndex());
		cr.setMaxResults(salesOrderBO.getMaxRecord());
		List<SalesOrderVO> salesOrderVO = cr.list();
		if (null != salesOrderVO && !salesOrderVO.isEmpty() && salesOrderVO.size() > 0) {
			return salesOrderVO;
		}
		return salesOrderVO;

	}

	public SalesOrderVO getSalesOrder(long salesOrderId) throws MySalesException {
		Session session = getSession();
		SalesOrderVO salesOrderVO = null;
		try {
			salesOrderVO = (SalesOrderVO) session.get(SalesOrderVO.class, salesOrderId);

		} catch (Exception he) {
			he.printStackTrace();
			if (SalesOrderDAOImpl.LOGGER.isDebugEnabled()) {
				SalesOrderDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			SalesOrderDAOImpl.LOGGER.exit();
		}

		return salesOrderVO;

	}

	public List<SalesOrderVO> getSalesOrderList(SalesOrderBO salesOrderNo) {
		List<SalesOrderVO> salesList = new ArrayList<>();
		Session session = getSession();
		Criteria cr = session.createCriteria(SalesOrderVO.class);
		cr.add(Restrictions.eq("salesOrderId", salesOrderNo.getSalesOrderId()));
		if (null != salesOrderNo.getCompanyId() && 0 < salesOrderNo.getCompanyId()) {
			cr.add(Restrictions.eq("companyId", salesOrderNo.getCompanyId()));
		}
		salesList = cr.list();
		if (null != salesList && !salesList.isEmpty() && salesList.size() > 0) {
			return salesList;
		}
		return salesList;
	}

	public GstBO getGstById(long gstId) {
		Session session = getSession();
		Criteria cr = session.createCriteria(GstVO.class);
		cr.add(Restrictions.eq("gstId", gstId));
		GstBO gstBO = (GstBO) cr.uniqueResult();
		if (null != gstBO) {
			return gstBO;
		}
		return gstBO;

	}

	@Override
	public PaymentVO savePayment(PaymentVO paymentvo) {

		Session session = sessionFactory.getCurrentSession();
		session.save(paymentvo);

		return paymentvo;
	}

	@Override
	public long salesCount(SalesOrderVO salesOrderVO) throws Exception {
		SalesOrderDAOImpl.LOGGER.entry();
		long count = 0;
		try {

			Criteria cr = getSession().createCriteria(SalesOrderVO.class);
			if(null != salesOrderVO.getCompanyId()&& 0< salesOrderVO.getCompanyId() ) {
			cr.add(companyValidation(salesOrderVO.getCompanyId())); // Companey
			}
			if(null != salesOrderVO.getSalesOrderNo()&& !salesOrderVO.getSalesOrderNo().isEmpty()) {
				cr.add(Restrictions.ilike("salesOrderNo", salesOrderVO.getSalesOrderNo().trim(),MatchMode.ANYWHERE));
			}
			cr.setProjection(Projections.rowCount());
			count = (long) cr.uniqueResult();

		} catch (Exception he) {
			he.printStackTrace();
			if (SalesOrderDAOImpl.LOGGER.isDebugEnabled()) {
				SalesOrderDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			SalesOrderDAOImpl.LOGGER.exit();
		}

		return count;
	}

	@Override
	public long searchCount(SalesOrderVO vo, String date) throws Exception {
		SalesOrderDAOImpl.LOGGER.entry();
		long count = 0;
		try {

			Criteria cr = getSession().createCriteria(SalesOrderVO.class);
			cr.add(companyValidation(vo.getCompanyId())); // Company

			if (vo != null && vo.getSalesOrderNo() != null && !vo.getSalesOrderNo().isEmpty()) {
				cr.add(Restrictions.ilike("salesOrderNo", vo.getSalesOrderNo().trim(), MatchMode.ANYWHERE));
			}

			if (vo.getAccountVO() != null && vo.getAccountVO().getAccountId() > 0) {
				cr.add(Restrictions.eq("accountVO.accountId", vo.getAccountVO().getAccountId()));
			}

			if (vo != null && vo.getCreated() != null) {
//				cr.add(Restrictions.eq("created", vo.getCreated()));
				Date startDate = vo.getCreated(); // The start of the day
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(startDate);
				calendar.add(Calendar.DAY_OF_MONTH, 1); // Add one day to get the end of the day
				Date endDate = calendar.getTime();

				// Add the predicate for the date range
				cr.add(Restrictions.between("created", startDate, endDate));
			}

			cr.setProjection(Projections.rowCount());
			count = (long) cr.uniqueResult();
		} catch (Exception he) {
			he.printStackTrace();

			if (SalesOrderDAOImpl.LOGGER.isDebugEnabled()) {
				SalesOrderDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			SalesOrderDAOImpl.LOGGER.exit();
		}

		return count;
	}

	@Override
	public List<SalesOrderVO> search(SalesOrderVO vo, String date) throws Exception {
		SalesOrderDAOImpl.LOGGER.entry();
		List<SalesOrderVO> VOList = new ArrayList<SalesOrderVO>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(SalesOrderVO.class);
			criteria.add(companyValidation(vo.getCompanyId())); // ComapnyId base Search
			criteria.setFirstResult(vo.getRecordIndex());
			criteria.setMaxResults(vo.getMaxRecord());
			if (vo != null && vo.getSalesOrderNo() != null && !vo.getSalesOrderNo().isEmpty()) {
				criteria.add(Restrictions.ilike("salesOrderNo", vo.getSalesOrderNo().trim(), MatchMode.ANYWHERE));
			}
			if (vo.getAccountVO() != null && vo.getAccountVO().getAccountId() > 0) {
				criteria.add(Restrictions.eq("accountVO.accountId", vo.getAccountVO().getAccountId()));
			}

			if (vo != null && vo.getCreated() != null) {
				if (vo != null && vo.getCreated() != null) {
					// Assuming vo.getCreated() returns a java.util.Date object with time set to
					// 00:00:00
					Date startDate = vo.getCreated(); // The start of the day
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(startDate);
					calendar.add(Calendar.DAY_OF_MONTH, 1); // Add one day to get the end of the day
					Date endDate = calendar.getTime();

					// Add the predicate for the date range
					criteria.add(Restrictions.between("created", startDate, endDate));
				}
			}

			VOList = criteria.list();
		} catch (Exception he) {
			he.printStackTrace();
			if (SalesOrderDAOImpl.LOGGER.isDebugEnabled()) {
				SalesOrderDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			SalesOrderDAOImpl.LOGGER.exit();
		}

		return VOList;
	}

	@Override
	public AccountVO getProfile(int accId) throws Exception {
		SalesOrderDAOImpl.LOGGER.entry();
		AccountVO doProfile = new AccountVO();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(AccountVO.class);
			criteria.add(Restrictions.eq("accountId", accId));
			criteria.add(Restrictions.eq("isDeleted", false));
			doProfile = (AccountVO) criteria.uniqueResult();
		} catch (Exception he) {
			he.printStackTrace();
			if (SalesOrderDAOImpl.LOGGER.isDebugEnabled()) {
				SalesOrderDAOImpl.LOGGER.debug(ErrorCodes.ENTITY_CRE_FAIL + he);
			}
			throw new MySalesException(ErrorCodes.ENTITY_CRE_FAIL, ErrorCodes.ENTITY_CRE_FAIL_MSG);
		} finally {

			SalesOrderDAOImpl.LOGGER.exit();
		}

		return doProfile;
	}

	@Override
	public void saveInvoice(SalesOrderVO salesOrderVo) {
		Session session = sessionFactory.getCurrentSession();
		SalesOrderVO salesVo = (SalesOrderVO) session.get(SalesOrderVO.class, salesOrderVo.getSalesOrderId());
		if (null != salesVo) {
			salesVo.setInvoiceName(salesOrderVo.getInvoiceName());
			session.update(salesVo);
			System.out.println("Update Success!");
		}

	}

	@Override
	public SalesOrderVO getSalesOrderById(long salesId) {
		Session session = sessionFactory.getCurrentSession();
		SalesOrderVO salesVo = (SalesOrderVO) session.get(SalesOrderVO.class, salesId);
		if (null != salesVo) {
			return salesVo;
		}
		return null;
	}

	@Override
	public List<PriceBookVO> getPricebook(PriceBookVO priceBookVO) {
		List<PriceBookVO> vo = new ArrayList<PriceBookVO>();

		try {
			Session session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(PriceBookVO.class);
			long serviceId = priceBookVO.getProductservicevo().getServiceId();
			criteria.add(Restrictions.eq("productservicevo.serviceId", serviceId));
			vo = criteria.list();
			if (null != vo) {
				return vo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("FROM INFO: Exception \t" + e);

			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("FROM DEBUG : Exception \t" + e);
			}
		}
		return null;
	}

	@Override
	public boolean getPaymentStatus(SalesOrderBO salesOrders) {
		boolean paymentStatus = false;
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(PaymentVO.class);
			if (0< salesOrders.getSalesOrderId()) {
				criteria.add(Restrictions.eq("salesOrderId", salesOrders.getSalesOrderId()));
			}
			PaymentVO paymentVo = (PaymentVO) criteria.uniqueResult();
			if (null != paymentVo) {
				paymentStatus = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

		return paymentStatus;
	}

	@Override
	public Company viewCompanyDetails(Company company) {
		LOGGER.entry();
		try {
			Criteria cr = getSession().createCriteria(Company.class);
			cr.add(Restrictions.eq("isDeleted", false));
			cr.add(Restrictions.eq("companyId", company.getCompanyId()));
			company = (Company) cr.uniqueResult();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Company view detail has failed:" + ex.getMessage());
			}
			LOGGER.info("Company view detail has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return company;
	}

}
