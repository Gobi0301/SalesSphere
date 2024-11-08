package com.scube.crm.service;


import java.util.List;

import com.scube.crm.bo.ProjectBO;
import com.scube.crm.exception.MySalesException;

public interface ProjectService {

	ProjectBO createProject(ProjectBO projectserviceBO) throws MySalesException;

	List<ProjectBO> selectAllProjects(ProjectBO projectBO)throws MySalesException;

	ProjectBO getProjectById(long projectId)throws MySalesException;

	ProjectBO updateProject(ProjectBO projectBO)throws MySalesException;

	boolean deleteProject(ProjectBO projectBO)throws MySalesException;

	long projectCount(ProjectBO projectBO)throws MySalesException;

	ProjectBO viewProject(ProjectBO projectBO);

	ProjectBO saveTracking(ProjectBO bo);

	ProjectBO getProjectObjectByName(ProjectBO projectBO);
}
