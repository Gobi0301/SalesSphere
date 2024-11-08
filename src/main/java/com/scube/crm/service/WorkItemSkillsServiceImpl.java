package com.scube.crm.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.WorkItemSLABO;
import com.scube.crm.bo.WorkItemSkillsBO;
import com.scube.crm.bo.SkillsBO;
import com.scube.crm.bo.SlaBO;
import com.scube.crm.bo.WorkItemBO;
import com.scube.crm.dao.AdminDAO;
import com.scube.crm.dao.Manage_WI_Skills_Dao;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.WorkItemSLAVO;
import com.scube.crm.vo.WorkItemSkillsVO;
import com.scube.crm.vo.SkillsVO;
import com.scube.crm.vo.User;
import com.scube.crm.vo.WorkItemVO;

@Service
@Transactional
public class WorkItemSkillsServiceImpl implements WorkItemSkillsService{


	static final MySalesLogger LOGGER = MySalesLogger.getLogger(TaskManagementServiceImpl.class);

	@Autowired
	Manage_WI_Skills_Dao Manage_WI_Skills_Dao;
	
	
	  @Autowired
	   WorkItemService workItemService;
	  
	  @Autowired
	   SkillsService skillsService;

	@Override
	public WorkItemSkillsBO save(WorkItemSkillsBO manageBO) {
		LOGGER.entry();
		try {
			long id = 0;
			WorkItemSkillsVO manageVO=new WorkItemSkillsVO();
			manageVO.setActive(true);
			manageVO.setDelete(false);
			WorkItemVO workItemVO=new WorkItemVO();
			String workItemId=manageBO.getWorkitemBO().getWorkItem();
			if(null!=workItemId) {
				id = Long.parseLong(workItemId);
				workItemVO.setWorkItemId(id);
				manageVO.setWorkitemVO(workItemVO);
			}
			if(null!=manageBO && null!=manageBO.getSkillsBO() && !( manageBO.getSkillsBO().getDescriptions().isEmpty())){
			
				List<String> skillsStringList=new ArrayList<String>(Arrays.asList(manageBO.getSkillsBO().getDescriptions().split(",")));


				for (String string : skillsStringList) {
					SkillsVO skillsVO=new SkillsVO();
					Long skillsId=Long.parseLong(string);
					skillsVO.setSkillsId(skillsId);
					manageVO.getSkillsListVO().add(skillsVO);
				}
				if(0<manageBO.getCompanyId()) {
					manageVO.setCompanyId(manageBO.getCompanyId());
				}
				Manage_WI_Skills_Dao.create(manageVO);

			}
			
		}
		catch (Exception ex) {

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("create Manage_WI_Skills has failed:" + ex.getMessage());
			}
			LOGGER.info("create Manage_WI_Skills has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return manageBO;

	}


	@Override
	public long retrieveCount(WorkItemSkillsBO manageBO) {
		long count = 0;
		try {
			WorkItemSkillsVO manageVo = new WorkItemSkillsVO();
			if(null!=manageBO.getCompanyId()&& 0< manageBO.getCompanyId()) {
				manageVo.setCompanyId(manageBO.getCompanyId());
			}
			if(null!=manageBO.getWorkitemBO()){
				WorkItemVO Workitem =new WorkItemVO();
				Workitem.setWorkItemId(manageBO.getWorkitemBO().getWorkItemId());
				Workitem.setWorkItem(manageBO.getWorkitemBO().getWorkItem());
				manageVo.setWorkitemVO(Workitem);
			}
			count = Manage_WI_Skills_Dao.retrieveCount(manageVo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}

	@Override
	public List<WorkItemSkillsBO> findAll(WorkItemSkillsBO manageBO) {
		LOGGER.entry();

		List<WorkItemSkillsBO> listbo = new ArrayList<WorkItemSkillsBO>();
		List<WorkItemSkillsVO> listvo = new ArrayList<WorkItemSkillsVO>();
		
		
		
		WorkItemSkillsVO manageVO = new WorkItemSkillsVO();
		manageVO.setRecordIndex(manageBO.getRecordIndex());
		manageVO.setMaxRecord(manageBO.getMaxRecord());
		if(null!=manageBO.getWorkitemBO()){
			WorkItemVO Workitem =new WorkItemVO();
			Workitem.setWorkItemId(manageBO.getWorkitemBO().getWorkItemId());
			Workitem.setWorkItem(manageBO.getWorkitemBO().getWorkItem());
			manageVO.setWorkitemVO(Workitem);
		}
		if(null != manageBO.getCompanyId()&& 0< manageBO.getCompanyId()) {
		manageVO.setCompanyId(manageBO.getCompanyId());
		}
		listvo = Manage_WI_Skills_Dao.findAll(manageVO);

		try {
				int sNo = manageBO.getRecordIndex();
			for (WorkItemSkillsVO managevo : listvo) {
				WorkItemSkillsBO managebo = new WorkItemSkillsBO();
				WorkItemBO workItemBO = new WorkItemBO();				
				managebo.setsNO(++sNo);
				managebo.setManageId(managevo.getWorkItemSkillId());
				List<SkillsBO> skillsListBO = new ArrayList<SkillsBO>();				
				
				for ( SkillsVO skillsVO : managevo.getSkillsListVO()) {
					SkillsBO skillsBO = new SkillsBO();
					skillsBO.setDescriptions(skillsVO.getDescriptions());
					skillsBO.setSkillsId(skillsVO.getSkillsId());
					skillsListBO.add(skillsBO);
				}
				managebo.setSkillsListBO(skillsListBO);
				if(null!=managevo.getWorkitemVO()
						&&null!=managevo.getWorkitemVO().getWorkItem()) {
					workItemBO.setWorkItem(managevo.getWorkitemVO().getWorkItem());
				}
				managebo.setWorkitemBO(workItemBO);
				listbo.add(managebo);
				 
				}
			
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("View Task has failed:" + ex.getMessage());
			}
		} finally {
			LOGGER.exit();
		}
		return listbo;
	}


	@Override
	public WorkItemSkillsBO findById(WorkItemSkillsBO manageBO) {
		LOGGER.entry();
		try {
			WorkItemSkillsVO manageVO=new WorkItemSkillsVO();
			manageVO.setWorkItemSkillId(manageBO.getManageId());
			manageVO.setCompanyId(manageBO.getCompanyId());
			manageVO=Manage_WI_Skills_Dao.getFindById(manageVO);
			
			List<Long> skillsIdList=Manage_WI_Skills_Dao.getSkillIdList(manageVO);
			manageBO.setSkillsIds(skillsIdList);
			if(null!=manageVO){
				manageBO.setManageId(manageVO.getWorkItemSkillId());
				WorkItemBO workItemBO = new WorkItemBO();
				if(null!=manageVO.getWorkitemVO()) {
				workItemBO.setWorkItem(manageVO.getWorkitemVO().getWorkItem());
				workItemBO.setWorkItemId(manageVO.getWorkitemVO().getWorkItemId());
				manageBO.setWorkitemBO(workItemBO);
				}
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Procurement has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Procurement has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return manageBO;
	}

	@Override
	public WorkItemSkillsBO update(WorkItemSkillsBO manageBO) {
		LOGGER.entry();
		WorkItemSkillsVO manageVO = new WorkItemSkillsVO();
		try {
			long id = 0;
			
			manageVO.setActive(true);
			manageVO.setDelete(false);
			
			List<SkillsVO> skillsVOlist = new ArrayList<>();
			if (null != manageBO) {
					for(SkillsBO skillsBO : manageBO.getSkillsListBO()) {
						 SkillsVO skillsVO = new SkillsVO();
						 skillsVO.setSkillsId(skillsBO.getSkillsId());
						 manageVO.getSkillsListVO().add(skillsVO);
					}
					}
				
			

			WorkItemVO workItemVO=new WorkItemVO();
			long  workItemId=manageBO.getWorkitemBO().getWorkItemId();
			if(0<=workItemId) {
				workItemVO.setWorkItemId(workItemId);
				manageVO.setWorkitemVO(workItemVO);
			}
			manageVO.setWorkItemSkillId(manageBO.getManageId());
			manageVO.setCompanyId(manageBO.getCompanyId());
			Manage_WI_Skills_Dao.update(manageVO);

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update Manage_WI_Skills has failed:" + ex.getMessage());
			}
			LOGGER.info("Update Manage_WI_Skills has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return manageBO;
	}

	@Override
	public boolean delete(WorkItemSkillsBO manageBO) {
		LOGGER.entry();
		Boolean status = false;
		WorkItemSkillsVO manageVo = new WorkItemSkillsVO();
		try {
			manageVo.setWorkItemSkillId(manageBO.getManageId());
			manageVo.setDelete(true);
			status = Manage_WI_Skills_Dao.delete(manageVo);

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Manage_WI_Skills has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Manage_WI_Skills has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return status;
	}
	@Override
	public List<WorkItemSkillsBO> findSkillsByWorkItem(long workItemId) {
		List<WorkItemSkillsBO> listbo = new ArrayList<WorkItemSkillsBO>();
		List<WorkItemSkillsVO> skillList =Manage_WI_Skills_Dao.findSLAByWorkItemId(workItemId);
		
		for(WorkItemSkillsVO workItem:skillList) {	
			WorkItemSkillsBO workItemSkillBO=new WorkItemSkillsBO();
			List<SkillsBO> skillsListBO = new ArrayList<SkillsBO>();
			for ( SkillsVO skillsVO : workItem.getSkillsListVO()) {
				SkillsBO skillsBO = new SkillsBO();
				skillsBO.setDescriptions(skillsVO.getDescriptions());
				skillsBO.setSkillsId(skillsVO.getSkillsId());
				skillsListBO.add(skillsBO);
			}
			
			//skill.setDescriptions(workItem.getSkillsListVO().getDescriptions());
			//skill.setSkillsId(workItem.getSkillsListVO().getSkillsId());
			workItemSkillBO.setSkillsListBO(skillsListBO);
			listbo.add(workItemSkillBO);			
		}
	 return listbo;
		 
	}


	@Override
	public List<WorkItemSkillsBO> findAllList(WorkItemSkillsBO manageBO) {
		List<WorkItemSkillsBO> workItemSkillsBOs = new ArrayList<WorkItemSkillsBO>();
		List<WorkItemSkillsVO> workItemSkillsVOs = new ArrayList<WorkItemSkillsVO>();
		WorkItemSkillsVO manageVO = new WorkItemSkillsVO();
		try {
//			if(null!=manageBO.getWorkitemBO()){
//				WorkItemVO WorkitemVO =new WorkItemVO();
//				WorkitemVO.setWorkItemId(manageBO.getWorkitemBO().getWorkItemId());
//				WorkitemVO.setWorkItem(manageBO.getWorkitemBO().getWorkItem());
//				manageVO.setWorkitemVO(WorkitemVO);
//			}
		if(null != manageBO.getCompanyId()&& 0< manageBO.getCompanyId()) {
			manageVO.setCompanyId(manageBO.getCompanyId());
			}
		workItemSkillsVOs = Manage_WI_Skills_Dao.findAllList(manageVO);
		if(null!=  workItemSkillsVOs && !workItemSkillsVOs.isEmpty()){
			for(WorkItemSkillsVO vo : workItemSkillsVOs) {
			WorkItemSkillsBO bo = new WorkItemSkillsBO();
	
			String workitem	=vo.getWorkitemVO().getWorkItem();
			long workitemId	=vo.getWorkitemVO().getWorkItemId();
			WorkItemBO workItemBO =new WorkItemBO();
			workItemBO.setWorkItem(workitem);
			workItemBO.setWorkItemId(workitemId);
			bo.setWorkitemBO(workItemBO);
			
			workItemSkillsBOs.add(bo);
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return workItemSkillsBOs;
	}
}

