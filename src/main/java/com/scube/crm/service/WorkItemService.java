package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.SkillsBO;
import com.scube.crm.bo.WorkItemBO;
import com.scube.crm.bo.WorkItemSkillsBO;
import com.scube.crm.exception.MySalesException;

public interface WorkItemService {
	public WorkItemBO save(WorkItemBO workItemBO)throws MySalesException;

	public long retrieveCount(WorkItemBO workItemBO)throws MySalesException;

	public List<WorkItemBO> findAll(WorkItemBO workItemBO)throws MySalesException;

	public WorkItemBO findById(WorkItemBO workItemBO);

	public WorkItemBO update(WorkItemBO workItemBO);

	public boolean delete(WorkItemBO workItemBO);

	public boolean checkWorkitemCode(String workItemCode, long id);

	public List<WorkItemBO> findAllWorkitem(WorkItemBO workItemBO);

	 
	

}
