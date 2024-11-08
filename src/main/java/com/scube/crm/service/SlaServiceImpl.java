package com.scube.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.SlaBO;
import com.scube.crm.dao.SlaDao;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.SlaVO;

@Service
@Transactional
public class SlaServiceImpl implements SlaService {

	@Autowired
	SlaDao slaDao;

	static final MySalesLogger LOGGER = MySalesLogger.getLogger(SlaServiceImpl.class);

	@Override
	public SlaBO save(SlaBO slaBO) {
		LOGGER.entry();
		SlaVO slaVO = new SlaVO();
		try {
			long id = 0;
			BeanUtils.copyProperties(slaBO, slaVO);
			slaVO.setDate(slaBO.getDate());
			slaVO.setDescriptions(slaBO.getDescriptions());
			slaVO.setCompanyId(slaBO.getCompanyId());
			slaVO.setActive(true);
			slaVO.setDelete(false);

			id = slaDao.create(slaVO);

		} catch (Exception ex) {

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("create Sla has failed:" + ex.getMessage());
			}
			LOGGER.info("create Sla has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return slaBO;

	}

	@Override
	public long retrieveCount(SlaBO slaBO) {
		long count = 0;
		try {
			SlaVO slaVO = new SlaVO();
			if(null != slaBO.getCompanyId() && 0< slaBO.getCompanyId()) {
			slaVO.setCompanyId(slaBO.getCompanyId());
			}
			slaVO.setSlaCode(slaBO.getSlaCode());
			if(null!=slaBO.getSlaCode()&& !slaBO.getSlaCode().isEmpty()) {
				slaVO.setSlaCode(slaBO.getSlaCode());
					}
			count = slaDao.retrieveCount(slaVO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}

	@Override
	public List<SlaBO> findAll(SlaBO slaBO) {
		LOGGER.entry();

		List<SlaBO> listslabo = new ArrayList<SlaBO>();
		List<SlaVO> listvo = new ArrayList<SlaVO>();
		SlaVO slaVO = new SlaVO();
		slaVO.setSlaCode(slaBO.getSlaCode());
		slaVO.setRecordIndex(slaBO.getRecordIndex());
		slaVO.setMaxRecord(slaBO.getMaxRecord());
		if(null != slaBO.getCompanyId()&& 0< slaBO.getCompanyId()) {
		slaVO.setCompanyId(slaBO.getCompanyId());
		}
		listvo = slaDao.findAll(slaVO);

		try {
			int sNo = slaBO.getRecordIndex();
			for (SlaVO slavo : listvo) {
				SlaBO slaBo = new SlaBO();
				slaBo.setsNo(++sNo);
				slaBo.setSlaId(slavo.getSlaId());
				slaBo.setSlaName(slavo.getSlaName());
				slaBo.setSlaCode(slavo.getSlaCode());
				slaBo.setDate(slavo.getDate());
				slaBo.setDescriptions(slavo.getDescriptions());
				listslabo.add(slaBo);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("View Task has failed:" + ex.getMessage());
			}
		} finally {
			LOGGER.exit();
		}
		return listslabo;
	}

	@Override
	public SlaBO findById(SlaBO slaBO) {
		LOGGER.entry();

		try {
			SlaVO slaVo = new SlaVO();
			slaVo.setSlaId(slaBO.getSlaId());
			slaVo = slaDao.getfindById(slaVo);
			if (null != slaVo) {
				slaBO.setSlaId(slaVo.getSlaId());
				slaBO.setSlaCode(slaVo.getSlaCode());
				slaBO.setSlaName(slaVo.getSlaName());
				slaBO.setDate(slaVo.getDate());
				slaBO.setDescriptions(slaVo.getDescriptions());
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Task has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Task has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return slaBO;
	}

	@Override
	public SlaBO update(SlaBO slaBO) {
		LOGGER.entry();
		SlaVO slaVO = new SlaVO();
		try {
			long id = 0;
			BeanUtils.copyProperties(slaBO, slaVO);
			slaVO.setDate(slaBO.getDate());
			slaVO.setDescriptions(slaBO.getDescriptions());
			slaVO.setActive(true);
			slaVO.setDelete(false);
			slaVO.setCompanyId(slaBO.getCompanyId());
			slaDao.update(slaVO);

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update Sla has failed:" + ex.getMessage());
			}
			LOGGER.info("Update Sla has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return slaBO;
	}

	@Override
	public boolean delete(SlaBO slaBO) {
		LOGGER.entry();
		Boolean status = false;
		SlaVO slaVo = new SlaVO();
		try {
			slaVo.setSlaId(slaBO.getSlaId());
			slaVo.setDelete(true);
			status = slaDao.delete(slaVo);

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Sla has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Sla has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return status;
	}

	@Override
	public boolean checkSlaCodeCheck(String slaCode) {
		LOGGER.entry();
		boolean checkSlaCode = false;
		try {
			checkSlaCode = slaDao.checkSlaCode(slaCode);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkSlaCode has failed:" + ex.getMessage());
			}
			LOGGER.info("checkSlaCode has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkSlaCode;
	}
}
