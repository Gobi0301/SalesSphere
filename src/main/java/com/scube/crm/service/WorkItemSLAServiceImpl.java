package com.scube.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.WorkItemSLABO;
import com.scube.crm.bo.ProcurementBO;
import com.scube.crm.bo.SlaBO;
import com.scube.crm.bo.SupplierBO;
import com.scube.crm.bo.TaskManagementBO;
import com.scube.crm.bo.WorkItemBO;
import com.scube.crm.dao.Manage_WI_SLA_Dao;
import com.scube.crm.dao.TaskManagementDao;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.WorkItemSLAVO;
import com.scube.crm.vo.ProcurementVO;
import com.scube.crm.vo.SlaVO;
import com.scube.crm.vo.SupplierVO;
import com.scube.crm.vo.TaskManagementVO;
import com.scube.crm.vo.User;
import com.scube.crm.vo.WorkItemVO;

@Service
@Transactional
public class WorkItemSLAServiceImpl implements WorkItemSLAService{

	
	static final MySalesLogger LOGGER = MySalesLogger.getLogger(TaskManagementServiceImpl.class);

	@Autowired
	Manage_WI_SLA_Dao manage_WI_SLA_Dao;

	@Override
	public WorkItemSLABO save(WorkItemSLABO manageBO) {
		LOGGER.entry();
		try {
 		long id = 0;
		WorkItemSLAVO manageVO=new WorkItemSLAVO();
	//	manageVO.setSlaCode(manageBO.getSlaCode());
		manageVO.setwISLACode(manageBO.getwISLACode());
		manageVO.setActive(true);
		manageVO.setisDelete(false);
		WorkItemVO workItemVO=new WorkItemVO();
		String workItemId=manageBO.getWorkItemBO().getWorkItem();
		if(null!=workItemId) {
			id = Long.parseLong(workItemId);
			workItemVO.setWorkItemId(id);
			manageVO.setWorkItemVO(workItemVO);
		}
			SlaVO slaVO=new SlaVO();
			String slaId=manageBO.getSlaBO().getSlaName();
			if(null!=slaId) {
				id = Long.parseLong(slaId);
				slaVO.setSlaId(id);
				manageVO.setSlaVO(slaVO);
			}
			manageVO.setCompanyId(manageBO.getCompanyId());
			id=  manage_WI_SLA_Dao.create(manageVO);
		}
		catch (Exception ex) {
			 
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("create manage_WI_SLA has failed:" + ex.getMessage());
			}
			LOGGER.info("create manage_WI_SLA has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return manageBO;

	}

	@Override
	public long retrieveCount(WorkItemSLABO manageBO) {
		long count = 0;
		try {
			WorkItemSLAVO manageVo = new WorkItemSLAVO();
			if(null != manageBO.getCompanyId()&& 0< manageBO.getCompanyId()) {
			manageVo.setCompanyId(manageBO.getCompanyId());
			}
			manageVo.setwISLACode(manageBO.getwISLACode());
			if(null!=manageBO.getSlaCode()&& !manageBO.getSlaCode().isEmpty()) {
				manageBO.setSlaCode(manageBO.getSlaCode());
					}
			count = manage_WI_SLA_Dao.retrieveCount(manageVo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}

	@Override
	public List<WorkItemSLABO> findAll(WorkItemSLABO manageBO) {
		LOGGER.entry();

		List<WorkItemSLABO> listbo = new ArrayList<WorkItemSLABO>();
		List<WorkItemSLAVO> listvo = new ArrayList<WorkItemSLAVO>();
		WorkItemSLAVO manageVO = new WorkItemSLAVO();
		manageVO.setwISLACode(manageBO.getwISLACode()); 
		manageVO.setRecordIndex(manageBO.getRecordIndex());
		manageVO.setMaxRecord(manageBO.getMaxRecord());
		if(null != manageBO.getCompanyId()&& 0< manageBO.getCompanyId()) {
		manageVO.setCompanyId(manageBO.getCompanyId());
		}
		listvo = manage_WI_SLA_Dao.findAll(manageVO);

		try {
			int sNo = manageBO.getRecordIndex();
			for (WorkItemSLAVO managevo : listvo) {
				WorkItemSLABO managebo = new WorkItemSLABO();
				WorkItemBO workItemBO = new WorkItemBO();
				SlaBO slaBO = new SlaBO();
				managebo.setsNo(++sNo);
				managebo.setManageId(managevo.getManageId());
				managebo.setSlaCode(managevo.getSlaCode());
				if(null!=managevo.getWorkItemVO()
						&&null!=managevo.getSlaVO().getSlaName()) {
					slaBO.setSlaName(managevo.getSlaVO().getSlaName());
				}
				managebo.setSlaBO(slaBO);
				if(null!=managevo.getWorkItemVO()
						&&null!=managevo.getWorkItemVO().getWorkItem()) {
					workItemBO.setWorkItem(managevo.getWorkItemVO().getWorkItem());
				}
				managebo.setWorkItemBO(workItemBO);
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
	public WorkItemSLABO findById(WorkItemSLABO manageBO) {
		LOGGER.entry();
		try {
			WorkItemSLAVO manageVO=new WorkItemSLAVO();
			manageVO.setManageId(manageBO.getManageId()); 
		
			manageVO=manage_WI_SLA_Dao.getFindById(manageVO); 
		if(null!=manageVO){
			manageBO.setManageId(manageVO.getManageId());
			manageBO.setSlaCode(manageVO.getSlaCode());
			WorkItemBO workItemBO = new WorkItemBO();
			workItemBO.setWorkItem(manageVO.getWorkItemVO().getWorkItem());
			workItemBO.setWorkItemId(manageVO.getWorkItemVO().getWorkItemId());
			manageBO.setWorkItemBO(workItemBO);
			
			SlaBO slaBO=new SlaBO();
			slaBO.setSlaName(manageVO.getSlaVO().getSlaName());
			slaBO.setSlaId(manageVO.getSlaVO().getSlaId());
			manageBO.setSlaBO(slaBO);
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
	public WorkItemSLABO update(WorkItemSLABO manageBO) {
		LOGGER.entry();
		WorkItemSLAVO manageVO = new WorkItemSLAVO();
		try {
			long id = 0;
			BeanUtils.copyProperties(manageBO, manageVO);
			manageVO.setActive(true);
			manageVO.setisDelete(false);
			
			
			SlaVO slaVO=new SlaVO();
			String  slaId=manageBO.getSlaBO().getSlaName();
			if(null!=slaId) {
				 long id1 = Long.parseLong(slaId);
				 slaVO.setSlaId(id1);
				 manageVO.setSlaVO(slaVO);
			}	
			
			
			WorkItemVO workItemVO=new WorkItemVO();
			String  workItemId=manageBO.getWorkItemBO().getWorkItem();
			if(null!=workItemId) {
				 long id1 = Long.parseLong(workItemId);
				 workItemVO.setWorkItemId(id1);
				 manageVO.setWorkItemVO(workItemVO);
			}	
			manageVO.setCompanyId(manageBO.getCompanyId());
			manage_WI_SLA_Dao.update(manageVO);
			 
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update manage_WI_SLA has failed:" + ex.getMessage());
			}
			LOGGER.info("Update manage_WI_SLA has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return manageBO;
	}

	@Override
	public boolean delete(WorkItemSLABO manageBO) {
		LOGGER.entry();
		Boolean status = false;
		WorkItemSLAVO manageVo = new WorkItemSLAVO();
		try {
			manageVo.setManageId(manageBO.getManageId());
			manageVo.setisDelete(true);
			status = manage_WI_SLA_Dao.delete(manageVo);

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete manage_WI_SLA has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete manage_WI_SLA has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return status;
	}

	@Override
	public List<WorkItemSLABO> findSLAByWorkItemId(long workItemId) {
// 		WorkItemSLABO workItemSLABO=new WorkItemSLABO();
// 		workItemSLABO.setManageId(workItemId);
// 				
// 		WorkItemSLAVO workItemSLAVO=new WorkItemSLAVO();
// 		workItemSLAVO.setManageId(workItemSLABO.getManageId());
		List<WorkItemSLABO> listbo = new ArrayList<WorkItemSLABO>();
		List<WorkItemSLAVO> slaList =manage_WI_SLA_Dao.findSLAByWorkItemId(workItemId);
		
		for(WorkItemSLAVO workItem:slaList) {	
			WorkItemSLABO workItemSLABO=new WorkItemSLABO();
			SlaBO sla= new SlaBO();
			sla.setSlaName(workItem.getSlaVO().getSlaName());
			workItemSLABO.setSlaBO(sla);
			listbo.add(workItemSLABO);
			
		}
		
		
		
		 return listbo;
		 
	}

	@Override
	public boolean checkWISLACode(String slacode) {
		LOGGER.entry();
		boolean checkSlaCode = false;
		try {
			checkSlaCode = manage_WI_SLA_Dao.checkWISLACode(slacode);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkWISlaCode has failed:" + ex.getMessage());
			}
			LOGGER.info("checkWISlaCode has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkSlaCode;
	}
}
