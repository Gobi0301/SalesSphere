package com.scube.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.SkillsBO;
import com.scube.crm.bo.WorkItemBO;
import com.scube.crm.bo.WorkItemSkillsBO;
import com.scube.crm.dao.WorkItemDao;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.SupplierVO;
import com.scube.crm.vo.WorkItemVO;


@Service
@Transactional
public class WorkItemServiceImpl implements WorkItemService {

	@Autowired
	WorkItemDao workItemDao;

	static final MySalesLogger LOGGER = MySalesLogger.getLogger(WorkItemServiceImpl.class);
	@Override
	public WorkItemBO save(WorkItemBO workItemBO)throws MySalesException {
		LOGGER.entry();
		WorkItemVO workItemVO = new WorkItemVO();
		try {
		long id = 0;
		workItemVO.setWorkItemType(workItemBO.getWorkItemType());
		workItemVO.setWorkItemCode(workItemBO.getWorkItemCode());
		workItemVO.setCreatedBy(workItemBO.getCreatedBy());
		workItemVO.setWorkItem(workItemBO.getWorkItem());
		workItemVO.setCompanyId(workItemBO.getCompanyId());
		workItemVO.setCreatedBy(workItemBO.getCreatedBy());
		workItemVO.setActive(true);
		workItemVO.setDelete(false);

		id = workItemDao.create(workItemVO);


		}
		 catch (Exception ex) {
		 
		if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("create workItem has failed:" + ex.getMessage());
		}
		LOGGER.info("create workItem has failed:" + ex.getMessage());
		} finally {
		LOGGER.exit();
		}

		return workItemBO;
	}

	@Override
	public long retrieveCount(WorkItemBO workItemBO)throws MySalesException{
		long count = 0;
		try {
			WorkItemVO workItemVO = new WorkItemVO();
			if(null != workItemBO.getCompanyId()&& 0< workItemBO.getCompanyId()) {
			workItemVO.setCompanyId(workItemBO.getCompanyId());
			}
			if(null!=workItemBO.getWorkItemCode()&& !workItemBO.getWorkItemCode().isEmpty()) {
         workItemVO.setWorkItemCode(workItemBO.getWorkItemCode());
			}
		count = workItemDao.retrieveCount(workItemVO);
		} catch (Exception ex) {
		ex.printStackTrace();
		}
		return count;
	}

	@Override
	public List<WorkItemBO> findAll(WorkItemBO workItemBO) throws MySalesException {
		LOGGER.entry();

		List<WorkItemBO> listWorkItemBO = new ArrayList<WorkItemBO>();
		List<WorkItemVO> listWorkItemVO = new ArrayList<WorkItemVO>();
		WorkItemVO workItemVO = new WorkItemVO();
		workItemVO.setWorkItemCode(workItemBO.getWorkItemCode());
		workItemVO.setWorkItemType(workItemBO.getWorkItemType());
		workItemVO.setRecordIndex(workItemBO.getRecordIndex());
		workItemVO.setMaxRecord(workItemBO.getMaxRecord());
		workItemVO.setWorkItemId(workItemBO.getWorkItemId());
		if(null != workItemBO.getCompanyId()&& 0< workItemBO.getCompanyId() ) {
		workItemVO.setCompanyId(workItemBO.getCompanyId());
		}
		listWorkItemVO = workItemDao.findAll(workItemVO);

		try {
		int sNo = workItemBO.getRecordIndex();
		for (WorkItemVO WorkItemVO : listWorkItemVO) {
			WorkItemBO workItem= new WorkItemBO();
			workItem.setsNo(++sNo);
			workItem.setWorkItemId(WorkItemVO.getWorkItemId());
			workItem.setWorkItem(WorkItemVO.getWorkItem());
			workItem.setWorkItemCode(WorkItemVO.getWorkItemCode());
			workItem.setWorkItemType(WorkItemVO.getWorkItemType());
			listWorkItemBO.add(workItem);
		}
		} catch (Exception ex) {
		if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("View listWorkItem has failed:" + ex.getMessage());
		}
		} finally {
		LOGGER.exit();
		}
		return listWorkItemBO;
	}

	@Override
	public WorkItemBO findById(WorkItemBO workItemBO) {
		LOGGER.entry();
		
		try {
		WorkItemVO workItemVO = new WorkItemVO();
		workItemVO.setWorkItemId(workItemBO.getWorkItemId());
		workItemVO = workItemDao.getfindById(workItemVO);
		if (null != workItemVO) {
			workItemBO.setWorkItemId(workItemVO.getWorkItemId());
			workItemBO.setWorkItemCode(workItemVO.getWorkItemCode());
			workItemBO.setWorkItemType(workItemVO.getWorkItemType());
			workItemBO.setWorkItem(workItemVO.getWorkItem());
		}
		
		} catch (Exception ex) {
		if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Edit workItem has failed:" + ex.getMessage());
		}
		LOGGER.info("Edit workItem has failed:" + ex.getMessage());
		} finally {
		LOGGER.exit();
		}
		return workItemBO;
	}

	@Override
	public WorkItemBO update(WorkItemBO workItemBO) {
		LOGGER.entry();
		WorkItemVO workItemVO = new WorkItemVO();
		try {
		long id = 0;
		BeanUtils.copyProperties(workItemBO, workItemVO);
		workItemVO.setActive(true);
		workItemVO.setDelete(false);
		workItemVO.setCompanyId(workItemBO.getCompanyId());
		
		workItemDao.update(workItemVO);
		
		} catch (Exception ex) {
		if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Update workItem has failed:" + ex.getMessage());
		}
		LOGGER.info("Update workItem has failed:" + ex.getMessage());
		} finally {
		LOGGER.exit();
		}
		return workItemBO;
	}

	@Override
	public boolean delete(WorkItemBO workItemBO) {
		LOGGER.entry();
		Boolean status = false;
		WorkItemVO workItemVO = new WorkItemVO();
		try {
			workItemVO.setWorkItemId(workItemBO.getWorkItemId());
			workItemVO.setDelete(true);
		status = workItemDao.delete(workItemVO);
		
		} catch (Exception ex) {
		if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Delete workItem has failed:" + ex.getMessage());
		}
		LOGGER.info("Delete workItem has failed:" + ex.getMessage());
		} finally {
		LOGGER.exit();
		}
		return status;
	}

	@Override
	public boolean checkWorkitemCode(String workItemCode,long id) {
		LOGGER.entry();
		boolean checkWorkitemCode=false;
		try {
			checkWorkitemCode=workItemDao.checkWorkitemCode(workItemCode,id);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkWorkitemCode has failed:" + ex.getMessage());
			}
			LOGGER.info("checkWorkitemCode has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkWorkitemCode;
	}

	@Override
	public List<WorkItemBO> findAllWorkitem(WorkItemBO workItemBO) {
		
		List<WorkItemBO> workItemBOs = new ArrayList<WorkItemBO>(); 
		List<WorkItemVO> workItemVOs = new ArrayList<WorkItemVO>();
		WorkItemVO vo = new WorkItemVO();
		try {	
			if(null!= workItemBO.getCompanyId() && 0< workItemBO.getCompanyId()) {
				vo.setCompanyId(workItemBO.getCompanyId());
			}
			workItemVOs = workItemDao.findAllWorkitem(vo);	
			if(null!= workItemVOs && !workItemVOs.isEmpty()) {
				for(WorkItemVO vo1 : workItemVOs) {
					WorkItemBO bo1 = new WorkItemBO();
					
					bo1.setWorkItem(vo1.getWorkItem());
					bo1.setWorkItemId(vo1.getWorkItemId());
					
					workItemBOs.add(bo1);
				}	
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return workItemBOs;
	}

	 

	


}
