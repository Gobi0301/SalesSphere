package com.scube.crm.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.crm.bo.ProjectBO;
import com.scube.crm.dao.ProjectDao;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.ProductTypesVO;
import com.scube.crm.vo.ProjectTrackingStatusVO;
import com.scube.crm.vo.ProjectVO;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

	
	@Autowired
	ProjectDao projectDao;
	
	static final MySalesLogger LOGGER = MySalesLogger.getLogger(ProjectServiceImpl.class);
	
	@Override
	public ProjectBO createProject(ProjectBO projectserviceBO) throws MySalesException {
		LOGGER.entry();
		int id=0;
		try {
		 ProjectVO projectVO =new ProjectVO();
		 projectVO.setProjectName(projectserviceBO.getProjectName());
		 projectVO.setProjectType(projectserviceBO.getProjectType());
		 projectVO.setProjectStatus(projectserviceBO.getProjectStatus());
		 projectVO.setApproval(projectserviceBO.getApproval());
		 projectVO.setProjectLocation(projectserviceBO.getProjectLocation());
		 projectVO.setProjectAreaInSqfts(projectserviceBO.getProjectAreaInSqfts());
		 projectVO.setUnit(projectserviceBO.getUnit());
		 projectVO.setStartDate(projectserviceBO.getStartDate());
		 projectVO.setEndDate(projectserviceBO.getEndDate());
		 projectVO.setAmenities(projectserviceBO.getAmenities());
		 projectVO.setNearByLocalities(projectserviceBO.getNearByLocalities());
		projectVO.setIsDeleted(false);
		projectVO.setCompanyId(projectserviceBO.getCompanyId());
		 id= projectDao.createProject(projectVO);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("create Project has failed:" + ex.getMessage());
			}
			LOGGER.info("create Project has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		
		return projectserviceBO;
		
	}

	@Override
	public List<ProjectBO> selectAllProjects(ProjectBO projectBO)throws MySalesException {
		LOGGER.entry();
			
			List<ProjectBO> listbo= new ArrayList<ProjectBO>();
			List<ProjectVO> listvo= new ArrayList<ProjectVO>();
			ProjectVO projectVO = new ProjectVO();
			projectVO.setProjectName(projectBO.getProjectName());
			projectVO.setRecordIndex(projectBO.getRecordIndex());
			projectVO.setMaxRecord(projectBO.getMaxRecord());
			if(null!=projectBO.getCompanyId()&& 0< projectBO.getCompanyId()) {
			projectVO.setCompanyId(projectBO.getCompanyId()); // Company
			}
			listvo=projectDao.selectAllProjects(projectVO);
//
//			lead.setRecordIndex(projectBO.getRecordIndex());
//			lead.setMaxRecord(projectBO.getMaxRecord());
			try {
				int sNo=projectBO.getRecordIndex();
			for(ProjectVO projectvo:listvo) {
				ProjectBO projectbo = new ProjectBO();
			
			
			projectbo.setsNo(++sNo);
				
			projectbo.setProjectId(projectvo.getProjectId());	
			projectbo.setProjectName(projectvo.getProjectName());
			projectbo.setProjectType(projectvo.getProjectType());
			projectbo.setProjectStatus(projectvo.getProjectStatus());
			projectbo.setApproval(projectvo.getApproval());
			projectbo.setProjectLocation(projectvo.getProjectLocation());
			projectbo.setProjectAreaInSqfts(projectvo.getProjectAreaInSqfts());
			projectbo.setUnit(projectvo.getUnit());
			projectbo.setStartDate(projectvo.getStartDate());
			projectbo.setEndDate(projectvo.getEndDate());
			projectbo.setAmenities(projectvo.getAmenities());
			projectbo.setNearByLocalities(projectvo.getNearByLocalities());
			projectbo.setIsDeleted(false);
//			++sNo;
			listbo.add(projectbo);
			}
			}catch (Exception ex) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("View project has failed:" + ex.getMessage());
				}
				LOGGER.info("View Project has failed:" + ex.getMessage());
			}finally {
				LOGGER.exit();
			}
			return listbo;
		}

	@Override
	public ProjectBO getProjectById(long projectId) throws MySalesException {
		LOGGER.entry();
		ProjectBO projectBo=new ProjectBO();
		try {
			ProjectVO projectVo=new ProjectVO();
			projectVo.setProjectId(projectId);
			projectVo=projectDao.getProjectById(projectVo);
		if(null!=projectVo) {
			projectBo.setProjectId(projectVo.getProjectId());
			projectBo.setProjectName(projectVo.getProjectName());
			projectBo.setProjectType(projectVo.getProjectType());
			projectBo.setProjectStatus(projectVo.getProjectStatus());
			projectBo.setApproval(projectVo.getApproval());
			projectBo.setProjectLocation(projectVo.getProjectLocation());
			projectBo.setProjectAreaInSqfts(projectVo.getProjectAreaInSqfts());
			projectBo.setUnit(projectVo.getUnit());
			projectBo.setStartDate(projectVo.getStartDate());
			projectBo.setEndDate(projectVo.getEndDate());
			projectBo.setAmenities(projectVo.getAmenities());
			projectBo.setNearByLocalities(projectVo.getNearByLocalities());
			projectBo.setIsDeleted(false);
			projectBo.setCompanyId(projectVo.getCompanyId());

		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Project has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit project has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
	}
		return projectBo;
	}

	@Override
	public ProjectBO updateProject(ProjectBO projectBO) throws MySalesException {
		LOGGER.entry();
		try {
		ProjectVO projectVo=new ProjectVO();
		    projectVo.setProjectId(projectBO.getProjectId());
			projectVo.setProjectName(projectBO.getProjectName());
			 projectVo.setProjectType(projectBO.getProjectType());
			 projectVo.setProjectStatus(projectBO.getProjectStatus());
			 projectVo.setApproval(projectBO.getApproval());
			 projectVo.setProjectLocation(projectBO.getProjectLocation());
			 projectVo.setProjectAreaInSqfts(projectBO.getProjectAreaInSqfts());
			 projectVo.setUnit(projectBO.getUnit());
			 projectVo.setStartDate(projectBO.getStartDate());
			 projectVo.setEndDate(projectBO.getEndDate());
			 projectVo.setAmenities(projectBO.getAmenities());
			 projectVo.setNearByLocalities(projectBO.getNearByLocalities());
			 projectVo.setIsDeleted(false);
			projectVo.setCompanyId(projectBO.getCompanyId());
			projectDao.updateProject(projectVo);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update project has failed:" + ex.getMessage());
			}
			LOGGER.info("Update project has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return projectBO;
	}

	@Override
	public boolean deleteProject(ProjectBO projectBO) throws MySalesException {
		LOGGER.entry();
		Boolean status= false;
		ProjectVO projectVo=new ProjectVO();
		try {
			projectVo.setProjectId(projectBO.getProjectId());
			projectVo.setIsDeleted(true);
			status= projectDao.deleteProject(projectVo);
			
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Delete Project has failed:" + ex.getMessage());
			}
			LOGGER.info("Delete Project has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return status;
	}

	@Override
	public long projectCount(ProjectBO projectBO) throws MySalesException {
		long count = 0;
		try {
			ProjectVO projectVo = new ProjectVO();
			if(null != projectBO.getCompanyId()&& 0< projectBO.getCompanyId()) {
			projectVo.setCompanyId(projectBO.getCompanyId());
			}
			projectVo.setProjectName(projectBO.getProjectName());
			if(null!=projectBO.getProjectName() && !projectBO.getProjectName().isEmpty()) {
				projectBO.setProjectName(projectBO.getProjectName());
			}
			count = projectDao.projectCount(projectVo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}

	@Override
	public ProjectBO viewProject(ProjectBO projectBO) {
		LOGGER.entry();
		try {
		ProjectVO vo = new ProjectVO();
         vo.setProjectId(projectBO.getProjectId());
		vo = projectDao.viewProject(vo);
		if (null != vo) {
			projectBO.setProjectId(vo.getProjectId());
			projectBO.setEntityid((int) projectBO.getProjectId());
			projectBO.setProjectName(vo.getProjectName());
			projectBO.setProjectType(vo.getProjectType());
			projectBO.setProjectStatus(vo.getProjectStatus());
			projectBO.setApproval(vo.getApproval());
			projectBO.setProjectLocation(vo.getProjectLocation());
			projectBO.setProjectAreaInSqfts(vo.getProjectAreaInSqfts());
			projectBO.setUnit(vo.getUnit());
			projectBO.setStartDate(vo.getStartDate());
			projectBO.setEndDate(vo.getEndDate());
			projectBO.setAmenities(vo.getAmenities());
			projectBO.setNearByLocalities(vo.getNearByLocalities());
			projectBO.setIsDeleted(false);
			projectBO.setCompanyId(vo.getCompanyId());

		}
		if(null!=projectBO) {
			//retrieve procurement tracking details
			List<ProjectTrackingStatusVO> procurementactivityList= projectDao.retrieveTracking(projectBO.getEntityid());
			projectBO.setProjectupdateList(procurementactivityList);
			}

}catch (Exception ex) {
	if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("viewProject has been failed: " + ex.getMessage());
	}
	LOGGER.info("viewProject has failed: " + ex.getMessage());
}
finally {
	LOGGER.exit();
}
		return projectBO;
		
		 

	}

	@Override
	public ProjectBO saveTracking(ProjectBO bo) {
	 
		ProjectTrackingStatusVO vo=new ProjectTrackingStatusVO();
		
		if (0 != bo.getProjectId()) {
			vo.setEntityid(bo.getProjectId());
		}
		vo.setAmenities(bo.getAmenities());
		vo.setNearByLocalities(bo.getNearByLocalities());
		vo=projectDao.saveTracking(vo);
		
	 
	 
	return bo;	
}

	@Override
	public ProjectBO getProjectObjectByName(ProjectBO projectBO) {
		ProjectVO projectVO = new ProjectVO();
		
		if (null !=projectBO.getProjectName() ) {
			projectVO.setProjectName(projectBO.getProjectName());
		}

		projectVO = projectDao.getProjectObjectByName(projectVO);
		if (null != projectVO) {
			projectBO.setProjectId(projectVO.getProjectId());
			projectBO.setProjectName(projectVO.getProjectName());
		}
		return projectBO;
	}
	
	}


