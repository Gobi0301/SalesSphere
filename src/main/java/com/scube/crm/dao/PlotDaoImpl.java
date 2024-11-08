package com.scube.crm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.bo.PlotBO;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.PlotVO;


@Repository
public class PlotDaoImpl extends BaseDao  implements PlotDao{
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(PlotDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	Session getSession() {
		return sessionFactory.getCurrentSession();

	}
	@Override
	public PlotBO createPlot(PlotVO plotVo) {
		
		LOGGER.entry();
		PlotBO plotBO=new PlotBO();
		Session session = sessionFactory.getCurrentSession();
		try {
			if (null!=plotVo) {
				session.save(plotVo);

				plotBO.setPlotId(plotVo.getPlotId());

			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}
		return plotBO;
		 
}
	@Override
	public long PlotCount(PlotVO plotVo) {
		LOGGER.entry();
		long count = 0;
		try {

			Criteria cr = getSession().createCriteria(PlotVO.class);
			cr.add(Restrictions.eq("isDelete", false));
			if(null != plotVo.getPlotNumbers() && !plotVo.getPlotNumbers().isEmpty()) {
				cr.add(Restrictions.ilike("plotNumbers", plotVo.getPlotNumbers().trim(),MatchMode.ANYWHERE));
			}
			if(null != plotVo.getCompanyId() && 0< plotVo.getCompanyId()) {
				cr.add(Restrictions.eq("companyId", plotVo.getCompanyId()));
			}
			cr.setProjection(Projections.rowCount());
			count = (long) cr.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}
		return count;

	}
	@Override
	public List<PlotVO> getlistPlot(PlotVO plotVO) {
		LOGGER.entry();

		List<PlotVO> voList = new ArrayList<>();

		try {
			Criteria cr=getSession().createCriteria(PlotVO.class);
			cr.add(Restrictions.eq("isDelete", false));
			
			cr.setFirstResult(plotVO.getRecordIndex());
			cr.setMaxResults(plotVO.getMaxRecord());
			if(null != plotVO.getPlotNumbers() && !plotVO.getPlotNumbers().isEmpty()) {
				cr.add(Restrictions.ilike("plotNumbers", plotVO.getPlotNumbers().trim(), MatchMode.ANYWHERE));
			}
			if(null != plotVO.getCompanyId() && 0< plotVO.getCompanyId()) {
			 cr.add(companyValidation(plotVO.getCompanyId()));//company condition
			}
			cr.addOrder(Order.asc("plotId"));
			voList = cr.list();

		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}
		return voList;
	}
	@Override
	public PlotVO selectPlot(PlotVO plotvo) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(PlotVO.class);
			criteria.add(Restrictions.eq("plotId", plotvo.getPlotId()));
			//criteria.add(Restrictions.ilike("plotNumbers",plotvo.getPlotNumbers(),MatchMode.ANYWHERE));
			plotvo = (PlotVO) criteria.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}
		return plotvo;
	}
	@Override
	public PlotBO PlotUpdate(PlotVO plotVO) {
		LOGGER.entry();
		PlotBO plotBO=new PlotBO();
		try {
			Session session=sessionFactory.getCurrentSession();
			if(null!=plotVO) {
				session.update(plotVO);
				plotBO.setPlotId(plotVO.getPlotId());

			}
		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}

		return plotBO;
	}
	@Override
	public Boolean deleteplot(PlotVO plotvo) {
		LOGGER.entry();
		try {
			Session session=sessionFactory.getCurrentSession();
			PlotVO vo=(PlotVO) session.get(PlotVO.class, plotvo.getPlotId());
			vo.setDelete(true);
			if(0<vo.getPlotId()){
				session.saveOrUpdate(vo);
				return true;
			}

		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}

		return false;
	}
	@Override
	public boolean checkPlotNumbers(String plotNumbers,long companyId) {
		LOGGER.entry();
	PlotVO plotVO=null;
		try {
			Session session=sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(PlotVO.class);
			criteria.add(Restrictions.eq("plotNumbers",plotNumbers));
			criteria.add(Restrictions.eq("companyId",companyId));
			List plotVOList = criteria.list();
		
		if(null!=plotVOList && 0<plotVOList.size()) {
			return true;
		}else {
			return false;
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkPlotNumbers has failed:" + ex.getMessage());
			}
			LOGGER.info("checkPlotNumbers has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return false;
	}
	
	
}