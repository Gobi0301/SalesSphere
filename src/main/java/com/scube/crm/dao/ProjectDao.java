package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.ProjectTrackingStatusVO;
import com.scube.crm.vo.ProjectVO;

public interface ProjectDao {
	int createProject(ProjectVO projectVO) throws MySalesException;

	List<ProjectVO> selectAllProjects(ProjectVO projectVO)throws MySalesException;

	ProjectVO getProjectById(ProjectVO projectVo)throws MySalesException;

	ProjectVO updateProject(ProjectVO projectVo)throws MySalesException;

	Boolean deleteProject(ProjectVO projectVo)throws MySalesException;

	long projectCount(ProjectVO projectVo)throws MySalesException;

	ProjectVO viewProject(ProjectVO vo);

	ProjectTrackingStatusVO saveTracking(ProjectTrackingStatusVO vo);

	List<ProjectTrackingStatusVO> retrieveTracking(long projectId);

	ProjectVO getProjectObjectByName(ProjectVO projectVO);
		

	
}
