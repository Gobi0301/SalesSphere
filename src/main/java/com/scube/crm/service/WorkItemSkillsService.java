package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.WorkItemSkillsBO;

public interface WorkItemSkillsService {

	WorkItemSkillsBO save(WorkItemSkillsBO manageBO);

	long retrieveCount(WorkItemSkillsBO manageBO);

	List<WorkItemSkillsBO> findAll(WorkItemSkillsBO manageBO);

	WorkItemSkillsBO findById(WorkItemSkillsBO manageBO);

	WorkItemSkillsBO update(WorkItemSkillsBO manageBO);

	boolean delete(WorkItemSkillsBO manageBO);

	List<WorkItemSkillsBO> findSkillsByWorkItem(long workItemId);

	List<WorkItemSkillsBO> findAllList(WorkItemSkillsBO manageBO);



}
