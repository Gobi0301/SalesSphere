package com.scube.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.PrivilegesBO;
import com.scube.crm.bo.SkillsBO;
import com.scube.crm.bo.SlaBO;
import com.scube.crm.dao.SkillsDao;
import com.scube.crm.dao.SlaDao;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.PrivilegesVO;
import com.scube.crm.vo.SkillsVO;
import com.scube.crm.vo.SlaVO;
import com.scube.crm.vo.User;

@Service
@Transactional
public class SkillsServiceImpl implements SkillsService {

	@Autowired
	SkillsDao skillsDao;

	static final MySalesLogger LOGGER = MySalesLogger.getLogger(SkillsServiceImpl.class);

	@Override
	public SkillsBO save(SkillsBO skillsBO) {
		LOGGER.entry();
		SkillsVO skillsVO = new SkillsVO();
		try {
			long id = 0;
			BeanUtils.copyProperties(skillsBO, skillsVO);
			skillsVO.setActive(true);
			skillsVO.setDelete(false);

			id = skillsDao.create(skillsVO);

		} catch (Exception ex) {

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("create Skills has failed:" + ex.getMessage());
			}
			LOGGER.info("create Skills has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return skillsBO;

	}

	@Override
	public long retrieveCount(SkillsBO skillsBO) {
		long count = 0;
		try {
			SkillsVO skillsVO = new SkillsVO();
			if (null != skillsBO.getSkillsCode()&& !skillsBO.getSkillsCode().isEmpty()) {
				skillsVO.setSkillsCode( skillsBO.getSkillsCode());
			}
			if(null != skillsBO.getCompanyId()&& 0< skillsBO.getCompanyId()) {
			skillsVO.setCompanyId(skillsBO.getCompanyId());
			}
			count = skillsDao.retrieveCount(skillsVO);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}

	@Override
	public List<SkillsBO> findAll(SkillsBO skillsBO) {
		LOGGER.entry();

		List<SkillsBO> listskillsbo = new ArrayList<SkillsBO>();
		List<SkillsVO> listvo = new ArrayList<SkillsVO>();
		SkillsVO skillsVO = new SkillsVO();
		skillsVO.setSkillsCode(skillsBO.getSkillsCode());
		skillsVO.setRecordIndex(skillsBO.getRecordIndex());
		skillsVO.setMaxRecord(skillsBO.getMaxRecord());
		if(null != skillsBO.getCompanyId()&& 0< skillsBO.getCompanyId()) {
		skillsVO.setCompanyId(skillsBO.getCompanyId());
		}
		listvo = skillsDao.findAll(skillsVO);

		try {
			int sNo = skillsBO.getRecordIndex();
			for (SkillsVO skillsvo : listvo) {
				SkillsBO skillsBo = new SkillsBO();
				skillsBo.setsNo(++sNo);
				skillsBo.setSkillsId(skillsvo.getSkillsId());
				skillsBo.setDescriptions(skillsvo.getDescriptions());
				skillsBo.setSkillsCode(skillsvo.getSkillsCode());
				listskillsbo.add(skillsBo);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("View Skills has failed:" + ex.getMessage());
			}
		} finally {
			LOGGER.exit();
		}
		return listskillsbo;
	}

	@Override
	public SkillsBO findById(SkillsBO skillsBO) {
		LOGGER.entry();

		try {
			SkillsVO skillsVo = new SkillsVO();
			skillsVo.setSkillsId(skillsBO.getSkillsId());
			skillsVo = skillsDao.getfindById(skillsVo);
			if (null != skillsVo) {
				skillsBO.setSkillsId(skillsVo.getSkillsId());
				skillsBO.setSkillsCode(skillsVo.getSkillsCode());
				skillsBO.setDescriptions(skillsVo.getDescriptions());
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Skills has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Skills has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return skillsBO;
	}

	@Override
	public SkillsBO update(SkillsBO skillsBO) {
		LOGGER.entry();
		SkillsVO skillsVO = new SkillsVO();
		try {
			long id = 0;
			BeanUtils.copyProperties(skillsBO, skillsVO);
			skillsVO.setActive(true);
			skillsVO.setDelete(false);
			skillsVO.setCompanyId(skillsBO.getCompanyId());
			skillsDao.update(skillsVO);

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update Skills has failed:" + ex.getMessage());
			}
			LOGGER.info("Update Skills has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return skillsBO;
	}

	@Override
	public boolean delete(SkillsBO skillsBO) {
		LOGGER.entry();
		Boolean status = false;
		SkillsVO skillsVo = new SkillsVO();
		try {
			skillsVo.setSkillsId(skillsBO.getSkillsId());
			skillsVo.setDelete(true);
			status = skillsDao.delete(skillsVo);

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Skills has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Skills has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return status;
	}

	@Override
	public boolean checkskillCode(String skillcode ,long companyId) {
		LOGGER.entry();
		boolean checkSkillCode = false;
		try {
			checkSkillCode = skillsDao.checkskillCode(skillcode,companyId);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckSkillCode has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckSkillCode has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkSkillCode;
	}

	@Override
	public boolean checkskillName(String skillname,long companyId) {
		LOGGER.entry();
		boolean checkSkillName = false;
		try {
			checkSkillName = skillsDao.checkskillName(skillname,companyId);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CheckSkillName has failed:" + ex.getMessage());
			}
			LOGGER.info("CheckSkillName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkSkillName;
	}

}
