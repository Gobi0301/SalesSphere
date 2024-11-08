package com.scube.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.PlotBO;
import com.scube.crm.dao.PlotDao;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.PlotVO;

@Service
@Transactional
public class PlotServiceImpl implements PlotService {
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(PlotServiceImpl.class);
	@Autowired
	PlotDao plotDao;
	
	@Override
	public PlotBO createPlot(PlotBO plotBO) {
		LOGGER.entry();
		try {
		
		PlotVO plotVo = new PlotVO();
		
			plotVo.setPlotNumbers(plotBO.getPlotNumbers());
			plotVo.setPlotSquareFeet(plotBO.getPlotSquareFeet());
			plotVo.setLength(plotBO.getLength());
			plotVo.setWidth(plotBO.getWidth());
			plotVo.setStatus(plotBO.getStatus());
			plotVo.setDelete(plotBO.getisDelete());
			plotVo.setCompanyId(plotBO.getCompanyId());
			plotVo.setCreatedBy(plotBO.getCreatedBy());
			plotBO = plotDao.createPlot(plotVo);
			
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("create plot has failed:" + ex.getMessage());
			}
			LOGGER.info("create plot has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return plotBO;
		
	}

	@Override
	public long PlotCount(PlotBO plotBO) {
		
		long count = 0;
		try {
			PlotVO plotVo = new PlotVO();
			if (null != plotBO.getPlotNumbers() &&!plotBO.getPlotNumbers().isEmpty()) {
				plotVo.setPlotNumbers( plotBO.getPlotNumbers());
			}
			if(null != plotBO.getCompanyId()&& 0< plotBO.getCompanyId()) {
			plotVo.setCompanyId( plotBO.getCompanyId());
			}
			count = plotDao.PlotCount(plotVo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}

	@Override
	public List<PlotBO> listPlot(PlotBO plotBO) {
		LOGGER.entry();
		PlotVO plotVO = new PlotVO();
		List<PlotVO> voList = new ArrayList<>();
		List<PlotBO> boList = new ArrayList<>();
		try {
			plotVO.setPlotId(plotBO.getPlotId());
			plotVO.setRecordIndex(plotBO.getRecordIndex());
			plotVO.setMaxRecord(plotBO.getMaxRecord());
			plotVO.setPlotNumbers(plotBO.getPlotNumbers());
			if(null != plotBO.getCompanyId()&& 0< plotBO.getCompanyId()) {
			plotVO.setCompanyId(plotBO.getCompanyId());
			}
			voList = plotDao.getlistPlot(plotVO);
			int sNo=plotBO.getRecordIndex();
			if (null != voList && voList.size() > 0 && !voList.isEmpty()) {
				for (PlotVO VO : voList) {
					PlotBO BO = new PlotBO();
					
					BO.setsNo(++sNo);
					BO.setPlotId(VO.getPlotId());
					BO.setPlotNumbers(VO.getPlotNumbers());
					BO.setPlotSquareFeet(VO.getPlotSquareFeet());
					BO.setLength(VO.getLength());
					BO.setWidth(VO.getWidth());
					BO.setStatus(VO.getStatus());
					

					boList.add(BO);

				}
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("List plot has failed:" + ex.getMessage());
			}
			LOGGER.info("List plot has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}

		return boList;
	}

	@Override
	public PlotBO selectPlot(PlotBO plotBo) {
		LOGGER.entry();
		try {
			PlotVO plotvo = new PlotVO();
			plotvo.setPlotId(plotBo.getPlotId());
			plotvo = plotDao.selectPlot(plotvo);
			if (null != plotvo) {
				
				plotBo.setPlotNumbers(plotvo.getPlotNumbers());
				plotBo.setLength(plotvo.getLength());
				plotBo.setPlotSquareFeet(plotvo.getPlotSquareFeet());
				plotBo.setWidth(plotvo.getWidth());
				plotBo.setStatus(plotvo.getStatus());
				return plotBo;
			}
				
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Supplier has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Supplier has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return null;
	}

	@Override
	public PlotBO PlotUpdate(PlotBO plotBO) {
		LOGGER.entry();
		PlotVO plotVO = new PlotVO();
		try {
			if (null != plotBO) {
				plotVO.setPlotId(plotBO.getPlotId());
				plotVO.setPlotNumbers(plotBO.getPlotNumbers());
				plotVO.setPlotSquareFeet(plotBO.getPlotSquareFeet());
				plotVO.setLength(plotBO.getLength());
				plotVO.setWidth(plotBO.getWidth());
				plotVO.setStatus(plotBO.getStatus());
				plotVO.setCompanyId(plotBO.getCompanyId());
				
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("update of plot has failed:" + ex.getMessage());
			}
			LOGGER.info("update of plot has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		plotBO = plotDao.PlotUpdate(plotVO);
		

		return plotBO;
	}

	@Override
	public boolean deletePlot(PlotBO plotBo) {
		LOGGER.entry();
		Boolean status= false;
		PlotVO plotvo=new PlotVO();
		try {
			plotvo.setPlotId(plotBo.getPlotId());
			plotvo.setDelete(true);
			status= plotDao.deleteplot(plotvo);
			
			if(status=true) {
				
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Supplier has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Supplier has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return status;
	}

	@Override
	public boolean checkPlotNumbers(String plotNumbers, long companyId) {
		LOGGER.entry();
		boolean checkPlotNumber = false;
		try {
			checkPlotNumber = plotDao.checkPlotNumbers(plotNumbers,companyId);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkPlotNumbers has failed:" + ex.getMessage());
			}
			LOGGER.info("checkPlotNumbers has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkPlotNumber;
	}

	
	
	
	}



