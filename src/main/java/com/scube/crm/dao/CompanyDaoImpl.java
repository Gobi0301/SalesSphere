package com.scube.crm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.bo.CompanyBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.Company;

@Repository
public class CompanyDaoImpl implements CompanyDao {
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(CompanyDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	Session getSession() {
		return sessionFactory.getCurrentSession();

	}

	@Override
	public Company createCompany(Company company) throws MySalesException{
		// TODO Auto-generated method stub
		LOGGER.entry();
		Session session = sessionFactory.getCurrentSession();
		try {
			 
				long companyId = (long) session.save(company);
			    if(0<companyId) {
					company.setCompanyName(company.getCompanyName());
					company.setCompanyId(companyId);
					return company;
				}
				
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add company has failed:" + ex.getMessage());
			}
			LOGGER.info("Add Company has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return null;
	}

	@Override
	public List<Company> listCompany(Company company)throws MySalesException {
		LOGGER.entry();
		try {
		Session session = getSession();
		Criteria cr = session.createCriteria(Company.class);
		cr.add(Restrictions.eq("isDeleted", company.getIsDeleted()));
		if( null !=company && 0< company.getCompanyId()) {
		cr.add(Restrictions.eq("companyId", company.getCompanyId()));
		}
		cr.setFirstResult(company.getRecordIndex());
		cr.setMaxResults(company.getMaxRecord());
		List<Company> companyVoList = cr.list();
		if (null != companyVoList && companyVoList.size() > 0) {
			return companyVoList;
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("List company has failed:" + ex.getMessage());
			}
			LOGGER.info("List Company has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return null;
	}

	@Override
	public Company retriveCompanybyId(Company companyVO) throws MySalesException{
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = (Criteria) session.createCriteria(Company.class);
			criteria.add(Restrictions.eq("companyId", companyVO.getCompanyId()));
			companyVO = (Company) ((org.hibernate.Criteria) criteria).uniqueResult();
			if (null != companyVO) {
				return companyVO;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("retrive company failed:" + e.getMessage());
			}
			LOGGER.info("retrive company failed:" + e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return null;
	}

	@Override
	public Company getCompanyId(CompanyBO companyBO) throws MySalesException{
		LOGGER.entry();
		try {
		Company company = (Company) getSession().get(Company.class, companyBO.getCompanyId());
		return company;
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("deleted of company has failed:" + ex.getMessage());
			}
			LOGGER.info("deleted of company has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return null;
	}

	@Override
	public boolean updateCompany(Company company)throws MySalesException {
		LOGGER.entry();
		try {
			Session session = getSession();
			session.saveOrUpdate(company);
			getSession().flush();
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit-update Company has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit-update Company has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return true;
	}

	@Override
	public Boolean deleteCompany(Company company)throws MySalesException {
		LOGGER.entry();
		int result;
		try {
			String deleteQuery = "UPDATE Company E set E.isDeleted = :isDeleted WHERE E.companyId = :companyId";
			final Query query = sessionFactory.getCurrentSession().createQuery(deleteQuery);
			query.setParameter("isDeleted", company.getIsDeleted());
			query.setParameter("companyId", company.getCompanyId());
			result = query.executeUpdate();
			if (0 != result) {

				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Deleted Company has failed:" + ex.getMessage());
			}
			LOGGER.info("Deleted Company has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
	}
	

	@Override
    public long countOfCompanyBySearch(Company companyVO) {
        LOGGER.entry();
        long countOfCompany=0;
        Session session=sessionFactory.getCurrentSession();
        try {
            Criteria criteria=session.createCriteria(Company.class);
            criteria.add(Restrictions.eq("isDeleted", false));
            
            if(null != companyVO && 0< companyVO.getCompanyId() ) {
                criteria.add(Restrictions.eq("companyId", companyVO.getCompanyId())); //company condition
             }
            
//            if( null!=companyVO.getCompanyName() && !companyVO.getCompanyName().isEmpty()) {
//                criteria.add(Restrictions.ilike("companyName", companyVO.getCompanyName().trim(), MatchMode.ANYWHERE));
//            }
            
                criteria.setProjection(Projections.rowCount());
                countOfCompany=(long)criteria.uniqueResult();
                
                if(0< countOfCompany) {
                    return countOfCompany;
                }
            
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }    
        return countOfCompany;
    }

    @Override
    public List<Company> listOfCompanyByPagination(Company companyVO) {
        // TODO Auto-generated method stub
        List<Company> companyListVO = new ArrayList<>();
        Session session=sessionFactory.getCurrentSession();
        try {
            Criteria criteria=session.createCriteria(Company.class);
            criteria.add(Restrictions.eq("isDeleted", false));
            
//            if( null!=companyVO.getCompanyName() && !companyVO.getCompanyName().isEmpty()) {
//                criteria.add(Restrictions.ilike("companyName", companyVO.getCompanyName().trim(), MatchMode.ANYWHERE));
//            }
            
            if(null != companyVO && 0< companyVO.getCompanyId() ) {
                criteria.add(Restrictions.eq("companyId", companyVO.getCompanyId())); //company condition
             }
            
            if (null != companyVO && companyVO.getMaxRecord() > 0) {
                criteria.setFirstResult(companyVO.getRecordIndex());
                criteria.setMaxResults(companyVO.getMaxRecord());
            }
           
            companyListVO=criteria.list();
             if (null != companyListVO && !companyListVO.isEmpty() && companyListVO.size() > 0) {
                 return companyListVO;
             }
        }
        catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        return companyListVO;
    }

	@Override
	public long retriveCompanyCount(Company company) throws MySalesException{
		LOGGER.entry();
		try {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(Company.class);
		cr.add(Restrictions.eq("isDeleted", company.getIsDeleted()));
		if(null!=company &&0<=company.getCompanyId()) {
		cr.add(Restrictions.eq("companyId", company.getCompanyId()));
		}
		cr.setProjection(Projections.rowCount());
		long companyId = (long) cr.uniqueResult();
		if (0 < companyId) {
			return companyId;
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrive CompanyCount has failed:" + ex.getMessage());
			}
			LOGGER.info("Retrive CompanyCount has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return 0;
	}

	@Override
	public Company viewCompanyDetails(Company company)throws MySalesException {
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

	@Override
	public boolean checkPhonenumber(String phoneNumber) {
		LOGGER.entry();
		Company company=null;
		try {
			Criteria cr = getSession().createCriteria(Company.class);
			cr.add(Restrictions.eq("isDeleted", false));
			cr.add(Restrictions.eq("contactNo",phoneNumber));
			company = (Company) cr.uniqueResult();
			if(null!=company) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Company view detail has failed:" + ex.getMessage());
			}
			LOGGER.info("Company view detail has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
	}

	@Override
	public boolean checkmobileNumber(String mobilenumber) {
		LOGGER.entry();
		Company company=null;
		try {
			Criteria cr = getSession().createCriteria(Company.class);
			cr.add(Restrictions.eq("isDeleted", false));
			cr.add(Restrictions.eq("mobileNo",mobilenumber));
			company = (Company) cr.uniqueResult();
			if(null!=company) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckEmailAddress has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckEmailAddress has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
	}

	@Override
	public boolean checkcompanyemail(String companyemail) {
		LOGGER.entry();
		Company company=null;
		try {
			Criteria cr = getSession().createCriteria(Company.class);
			cr.add(Restrictions.eq("isDeleted", false));
			cr.add(Restrictions.eq("email",companyemail));
			company = (Company) cr.uniqueResult();
			if(null!=company) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckEmailAddress has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckEmailAddress has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
}

	@Override
	public boolean checkcompanywebsite(String companywebsite) {
		LOGGER.entry();
		try {
			Criteria cr = getSession().createCriteria(Company.class);
			cr.add(Restrictions.eq("isDeleted", false));
			cr.add(Restrictions.eq("website",companywebsite));
			
			List<Company> companyList = cr.list();
			if(null!=companyList && 0<companyList.size()) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckCompanyWebsite has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckCompanyWebsite has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
}

	@Override
	public boolean checkcompanygst(String companygst) {
		LOGGER.entry();
		try {
			Criteria cr = getSession().createCriteria(Company.class);
			cr.add(Restrictions.eq("isDeleted", false));
			cr.add(Restrictions.eq("companyGstNo",companygst));
			
			List<Company> companyList = cr.list();
			if(null!=companyList && 0<companyList.size()) {
				return true;
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckCompanyGst has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckCompanyGst has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
}

	@Override
	public long retriveCount(Company company) {
		LOGGER.entry();
		try {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(Company.class);
		cr.add(Restrictions.eq("isDeleted", company.getIsDeleted()));
		cr.setProjection(Projections.rowCount());
		long companyId = (long) cr.uniqueResult();
		if (0 < companyId) {
			return companyId;
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Retrive CompanyCount has failed:" + ex.getMessage());
			}
			LOGGER.info("Retrive CompanyCount has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return 0;
	}		
}