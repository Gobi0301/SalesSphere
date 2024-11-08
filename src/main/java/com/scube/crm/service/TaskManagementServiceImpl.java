package com.scube.crm.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.LeadsBO;
import com.scube.crm.bo.ProjectBO;
import com.scube.crm.bo.SkillsBO;
import com.scube.crm.bo.SlaBO;
import com.scube.crm.bo.TaskManagementBO;
import com.scube.crm.bo.WorkItemBO;
import com.scube.crm.bo.WorkItemSLABO;
import com.scube.crm.bo.WorkItemSkillsBO;
import com.scube.crm.dao.TaskManagementDao;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.Leads;
import com.scube.crm.vo.ProjectVO;
import com.scube.crm.vo.TaskManagementVO;
import com.scube.crm.vo.TaskTrackingStatusVO;
import com.scube.crm.vo.User;
import com.scube.crm.vo.WorkItemVO;
 

@Service
@Transactional
public class TaskManagementServiceImpl implements TaskManagementService {

	static final MySalesLogger LOGGER = MySalesLogger.getLogger(TaskManagementServiceImpl.class);

	@Autowired
	TaskManagementDao taskDao;
	
	@Autowired
	WorkItemService workItemService;
	
	@Autowired
	WorkItemSkillsService workItemSkillsService;
	
	@Autowired
	private AdminService adminService;
	
	 @Autowired
	  WorkItemSLAService workItemSLAService;

	@Override
	public TaskManagementBO save(TaskManagementBO taskBO) {
		LOGGER.entry();
		TaskManagementVO taskVO = new TaskManagementVO();
		try {
			long id = 0;
			BeanUtils.copyProperties(taskBO, taskVO);
			taskVO.setDate(taskBO.getDate());
			taskVO.setActive(true);
			taskVO.setisDelete(false);
 
//				User user =new User();
//				String userid=taskBO.getAdminUserBO().getName();
//				if(null!=userid) {
//					id = Long.parseLong(userid);
//					user.setId(id);
//					taskVO.setTaskOwner(user);
//				}
							
				//Step 1 : Find list of task for the workItem
				WorkItemBO workItemBO = new WorkItemBO();
				workItemBO.setWorkItemType(taskBO.getEntityName());
				List<WorkItemBO> workitems=workItemService.findAll(workItemBO);
				long workItemId=0;
				for(WorkItemBO workItem:workitems) {	
					
					
					workItemId=workItem.getWorkItemId();
					
				
				//Step 2: Find SLA for each workItem
					List<WorkItemSLABO>  slalist=workItemSLAService.findSLAByWorkItemId(workItem.getWorkItemId());
						
					    for (WorkItemSLABO workItemSLABO : slalist) {
							 String slaName = workItemSLABO.getSlaBO().getSlaName();
							 LocalDate dueDate = LocalDate.now().plusDays(Integer.parseInt(slaName));
							  taskVO.setDueDate(dueDate.toString()); 
							}
				
				//Step 3:Find Skills for workitem
				List<WorkItemSkillsBO> skills= workItemSkillsService.findSkillsByWorkItem(workItem.getWorkItemId());
				StringBuilder employeeSkills=new StringBuilder();
				String skillASString = null;
				long skillsIds=0;
				if(null!=skills && skills.size()>0) {					

					for(WorkItemSkillsBO skill:skills) {						
						//employeeSkills.append(skill.getSkillsBO().getSkillsId());
						if(skill.getSkillsBO().getSkillsId()<0  ) {
						 skillsIds=skill.getSkillsBO().getSkillsId();
						skillASString = employeeSkills.toString();
						employeeSkills.append(skill.getSkillsBO().getSkillsId()+",");
						}
					}
				}				
				

				//Step 4 Find Employee with Skills
				List<AdminUserBO> employees=adminService.findEmployeesBySkills(skillsIds);
				StringBuilder employeesList=new StringBuilder();
				if (null != employees && employees.size() > 0) {
				    for (AdminUserBO employee : employees) {
				        employeesList.append(employee.getId()).append(",");
				    }
				}

				// Convert the StringBuilder to a List<String>
				List<String> employeeIds = Arrays.asList(employeesList.toString().split(","));
				employeeIds.removeIf(String::isEmpty); 
				List<Long> empList=new ArrayList<Long>();
				for(String str:employeeIds) {
					empList.add(Long.valueOf(str));
					
				}
				
				// Step 5: Find workload for the employee
				User taskOwner=taskDao.findOpenTaskForEmployees(empList);
				if(null!=taskOwner) {
					
					taskVO.setTaskOwner(taskOwner);
					
				}	else {
					//taskVO.setTaskOwner(employees.get(0));
					taskVO.setTaskOwner(new User());
				}
				WorkItemVO workItemVo=new WorkItemVO();
					taskVO.setStatus("Assigned");
					taskVO.setPriority("Normal");
					taskVO.setRelatedTo(taskBO.getRelatedTo());
					workItemVo.setWorkItemId(workItemId);
					taskVO.setWorkItemVO(workItemVo);
				
	
			 id = taskDao.create(taskVO);
				
				
				}
		} 
		  catch (Exception ex) {
 
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("create Task has failed:" + ex.getMessage());
			}
			LOGGER.info("create Task has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return taskBO;

	}

	@Override
	public long retrieveCount(TaskManagementBO taskBO) {
		long count = 0;
		Leads lead=new Leads();
		try {
			TaskManagementVO taskVo = new TaskManagementVO();
			if(null != taskBO.getCompanyId()&& 0< taskBO.getCompanyId()) {
			taskVo.setCompanyId(taskBO.getCompanyId());
			}
			if(null!=taskBO.getLeadName()&& !taskBO.getLeadName().isEmpty()) {
				lead.setFirstName(taskBO.getLeadName());
				taskVo.setLeadsVO(lead);
			}
			count = taskDao.retrieveCount(taskVo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}

	@Override
	public List<TaskManagementBO> findAll(TaskManagementBO taskBO) {
		LOGGER.entry();

		List<TaskManagementBO> listbo = new ArrayList<TaskManagementBO>();
		List<TaskManagementVO> listvo = new ArrayList<TaskManagementVO>();
		TaskManagementVO taskVO = new TaskManagementVO();
		Leads lead=new Leads();
		taskVO.setRecordIndex(taskBO.getRecordIndex());
		taskVO.setMaxRecord(taskBO.getMaxRecord());
		if(null!=taskBO.getLeadName()&& !taskBO.getLeadName().isEmpty()) {
			lead.setFirstName(taskBO.getLeadName());
			taskVO.setLeadsVO(lead);
		}
		if(null != taskBO.getCompanyId()&& 0< taskBO.getCompanyId()) {
		taskVO.setCompanyId(taskBO.getCompanyId());
		}
		listvo = taskDao.findAll(taskVO);

		try {
			int sNo = taskBO.getRecordIndex();
			for (TaskManagementVO taskvo : listvo) {
				TaskManagementBO taskbo = new TaskManagementBO();
				
				AdminUserBO adminuserBO=new AdminUserBO();
				WorkItemBO workItemBO=new WorkItemBO();
				ProjectBO projectBO =new ProjectBO();
				LeadsBO leadsBO = new LeadsBO();
				taskbo.setsNo(++sNo);
				taskbo.setTaskId(taskvo.getTaskId());
				taskbo.setSubject(taskvo.getSubject());
				taskbo.setStatus(taskvo.getStatus());
				
			if(null != taskvo.getTaskOwner()&& null != taskvo.getTaskOwner().getName() ) {
				adminuserBO.setName(taskvo.getTaskOwner().getName());
				taskBO.setAdminUserBO(adminuserBO);
			}
			if(null != taskvo.getWorkItemVO()&& 0< taskvo.getWorkItemVO().getWorkItemId() ) {
				workItemBO.setWorkItemId(taskvo.getWorkItemVO().getWorkItemId());
				workItemBO.setWorkItem(taskvo.getWorkItemVO().getWorkItem());
			     taskbo.setWorkItemBO(workItemBO);
			}
			if(null != taskvo.getProjectVo()&& 0< taskvo.getProjectVo().getProjectId()) {
			     projectBO.setProjectId(taskvo.getProjectVo().getProjectId());
			     projectBO.setProjectName(taskvo.getProjectVo().getProjectName());
			     taskbo.setProjectBo(projectBO);
			}
			     if(null!=taskvo&&0<taskvo.getLeadsVO().getLeadsId()) {
			    	 leadsBO.setLeadsId(taskvo.getLeadsVO().getLeadsId());
				     leadsBO.setFirstName(taskvo.getLeadsVO().getFirstName());
				     taskbo.setLeadsBO(leadsBO); 
			     }
			     
			     
				taskbo.setClosedTime(taskvo.getClosedTime());
				taskbo.setDescription(taskvo.getDescription());
				taskbo.setRelatedTo(taskvo.getRelatedTo());
				taskbo.setPriority(taskvo.getPriority());
				taskbo.setDate(taskvo.getDate());
				taskbo.setDuedate(taskvo.getDueDate());
				
				
				
				if(null!=taskvo.getTaskOwner()
						&&null!=taskvo.getTaskOwner().getName()) {
					adminuserBO.setName(taskvo.getTaskOwner().getName());
				}
				taskbo.setAdminUserBO(adminuserBO);

//				if(null!=taskvo.getWorkItemVO()
//						&&null!=taskvo.getWorkItemVO().getWorkItem()) {
//					workItemBO.setWorkItem(taskvo.getWorkItemVO().getWorkItem());
//				}
//				taskbo.setWorkItemBO(workItemBO);
				
 
				listbo.add(taskbo);
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
	public TaskManagementBO findById(TaskManagementBO taskBO) {
		LOGGER.entry();

		try {
			TaskManagementVO taskVo = new TaskManagementVO();
			taskVo.setTaskId(taskBO.getTaskId());
			taskVo = taskDao.getfindById(taskVo);
			if (null != taskVo) {
				taskBO.setTaskId(taskVo.getTaskId());
				taskBO.setEntityId((int) taskVo.getTaskId());
				taskBO.setSubject(taskVo.getSubject());
				taskBO.setStatus(taskVo.getStatus());
				AdminUserBO adminuserBO=new AdminUserBO();
				WorkItemBO workItemBO=new WorkItemBO();
				ProjectBO projectBO=new ProjectBO();
				LeadsBO leadsBO=new LeadsBO();
				
				adminuserBO.setName(taskVo.getTaskOwner().getName());
				taskBO.setAdminUserBO(adminuserBO);
				
				if(null != taskVo.getProjectVo()&& 0 < taskVo.getProjectVo().getProjectId() ) {
				projectBO.setProjectId(taskVo.getProjectVo().getProjectId());
				projectBO.setProjectName(taskVo.getProjectVo().getProjectName());
				taskBO.setProjectBo(projectBO);
				}else {
					projectBO.setProjectName("N/A");
					taskBO.setProjectBo(projectBO);
				}
				
				leadsBO.setLeadsId(taskVo.getLeadsVO().getLeadsId());
				leadsBO.setFirstName(taskVo.getLeadsVO().getFirstName());
				taskBO.setLeadsBO(leadsBO);
				
				workItemBO.setWorkItemId(taskVo.getWorkItemVO().getWorkItemId());
				workItemBO.setWorkItem(taskVo.getWorkItemVO().getWorkItem());
				taskBO.setWorkItemBO(workItemBO);
				
				
				taskBO.setClosedTime(taskVo.getClosedTime());
				taskBO.setDescription(taskVo.getDescription());
				taskBO.setDuedate(taskVo.getDueDate());
				taskBO.setRelatedTo(taskVo.getRelatedTo());
				taskBO.setPriority(taskVo.getPriority());
				taskBO.setDate(taskVo.getDate());
				
				AdminUserBO adminUserBO=new AdminUserBO();
				adminUserBO.setName(taskVo.getTaskOwner().getName());
				adminUserBO.setAdminId(taskVo.getTaskOwner().getId());
				taskBO.setAdminUserBO(adminUserBO);
				
			}
			if (null != taskBO) {
				// retrieve Task tracking details
				List<TaskTrackingStatusVO> taskactivityList = taskDao.retrieveTracking(taskBO.getEntityId());
				taskBO.setTaskupdateList(taskactivityList);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Task has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Task has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return taskBO;
	}

	@Override
	public TaskManagementBO update(TaskManagementBO taskBO) {
		LOGGER.entry();
		TaskManagementVO taskVO = new TaskManagementVO();
		try {
			long id = 0;
			BeanUtils.copyProperties(taskBO, taskVO);
			taskVO.setDueDate(taskBO.getDuedate());
			taskVO.setDate(taskBO.getDate());
			taskVO.setActive(true);
			taskVO.setisDelete(false);
			
				
				User user =new User();
				String userid=taskBO.getAdminUserBO().getName();
				if(null!=userid) {
					id = Long.parseLong(userid);
					user.setId(id);
					taskVO.setTaskOwner(user);
					
					WorkItemVO workItemVO=new WorkItemVO();
					String workItemId=taskBO.getWorkItemBO().getWorkItem();
					if(null!=workItemId) {
						id = Long.parseLong(workItemId);
						workItemVO.setWorkItemId(id);
						taskVO.setWorkItemVO(workItemVO);
					}
					ProjectVO projectVO=new ProjectVO();
					long projectId=Long.parseLong(taskBO.getProjectBo().getProjectName());
					if(0!=projectId) {
						projectVO.setProjectId(projectId);
						taskVO.setProjectVo(projectVO);
					}
					
					Leads leadsVO = new Leads();
					long leadsId=Long.parseLong(taskBO.getLeadsBO().getFirstName());
					if(0!=leadsId) {
						leadsVO.setLeadsId(leadsId);
						taskVO.setLeadsVO(leadsVO);
						//For common table reterive purpose we set the entityId and entityName..
						taskVO.setEntityId(leadsId);
						taskVO.setEntityName("Leads");
					}
					if(null!=taskBO.getCompanyId()) {
						taskVO.setCompanyId(taskBO.getCompanyId());
					}
			
			taskDao.update(taskVO);
			}
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update Task has failed:" + ex.getMessage());
			}
			LOGGER.info("Update Task has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return taskBO;
	}

	@Override
	public boolean delete(TaskManagementBO taskBO) {
		LOGGER.entry();
		Boolean status = false;
		TaskManagementVO taskVo = new TaskManagementVO();
		try {
			taskVo.setTaskId(taskBO.getTaskId());
			taskVo.setisDelete(true);
			status = taskDao.delete(taskVo);

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Task has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Task has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return status;
	}

	@Override
	public TaskManagementBO saveTracking(TaskManagementBO bo) {
		TaskTrackingStatusVO vo = new TaskTrackingStatusVO();
		vo.setReport(bo.getReport());
		vo.setDescriptions(bo.getDescriptions());
		vo.setDate(bo.getDate());
		if (0 != bo.getTaskId()) {
			vo.setEntityid(bo.getTaskId());
		}
		if (null != bo.getUploadfile()) {
			vo.setUploadfile(bo.getUploadfile());

		}
		vo.setEntitytype("TaskManagement");
		vo = taskDao.saveTracking(vo);

		return bo;
	}

	@Override
	public long retrieveCount() {

		return taskDao.retriveCount();
	}

	@Override
	public TaskTrackingStatusVO taskTrackingStatus(long taskId) {
		TaskTrackingStatusVO activityVO = taskDao.taskTrackingStatus(taskId);
		if (null != activityVO) {

			return activityVO;
		}
		return null;
	}

	@Override
	public TaskManagementBO saveNewTask(TaskManagementBO taskBO) {
		TaskManagementVO taskVO=new TaskManagementVO();
		WorkItemVO workItemVo=new WorkItemVO();
		ProjectVO projectVO=new ProjectVO();
		User adminUserVo=new User();
		Leads leads = new Leads();
		
		taskVO.setStatus(taskBO.getStatus());
	    taskVO.setPriority(taskBO.getPriority());
	    taskVO.setDescription(taskBO.getDescription());
	    taskVO.setDueDate(taskBO.getDuedate());
	    if(0 !=taskBO.getProjectBo().getProjectId()) {
	    projectVO.setProjectId(taskBO.getProjectBo().getProjectId());
	    taskVO.setProjectVo(projectVO);
	    }
	    if(0 !=taskBO.getWorkItemBO().getWorkItemId()) {
		    workItemVo.setWorkItemId(taskBO.getWorkItemBO().getWorkItemId());
		    taskVO.setWorkItemVO(workItemVo);
		    }
	    if(0 !=taskBO.getAdminUserBO().getUserId()) {
	    adminUserVo.setId(taskBO.getAdminUserBO().getUserId());
	    taskVO.setTaskOwner(adminUserVo);
	    }
	    if(0 !=taskBO.getLeadsBO().getLeadsId()) {
	    	leads.setLeadsId(taskBO.getLeadsBO().getLeadsId());
		    taskVO.setLeadsVO(leads);
		    }
	    if(0 !=taskBO.getLeadsBO().getLeadsId()) {
	    	taskVO.setEntityId(taskBO.getLeadsBO().getLeadsId());
	    }
	    taskVO.setEntityName("Leads");
	    if(null!=taskBO.getCompanyId()) {
	    	taskVO.setCompanyId(taskBO.getCompanyId());
	    }
	    long id=taskDao.saveNewTask(taskVO);
		if(id>0) {
			TaskManagementBO taskManagementBo=new TaskManagementBO();
			taskManagementBo.setTaskId(id);
			return taskManagementBo;
		}
		
		
		
		return null;
	}

	@Override
	public void saveAll(List<TaskManagementBO> tasks) {
		// TODO Auto-generated method stub
		
	}

}
