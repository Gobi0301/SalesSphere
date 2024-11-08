package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.WorkItemSLABO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.bo.SlaBO;

public interface WorkItemSLAService {

	WorkItemSLABO save(WorkItemSLABO manageBO);

	long retrieveCount(WorkItemSLABO manageBO);

	List<WorkItemSLABO> findAll(WorkItemSLABO manageBO);

	WorkItemSLABO findById(WorkItemSLABO manageBO);

	WorkItemSLABO update(WorkItemSLABO manageBO);

	boolean delete(WorkItemSLABO manageBO);

	List<WorkItemSLABO>  findSLAByWorkItemId(long workItemId);
	
	public boolean checkWISLACode(String slacode);

}
