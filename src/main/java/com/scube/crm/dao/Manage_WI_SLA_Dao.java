package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.bo.WorkItemSLABO;
import com.scube.crm.vo.WorkItemSLAVO;

public interface Manage_WI_SLA_Dao {

	long create(WorkItemSLAVO manageVO);

	long retrieveCount(WorkItemSLAVO manageVo);

	List<WorkItemSLAVO> findAll(WorkItemSLAVO manageVO);

	WorkItemSLAVO getFindById(WorkItemSLAVO manageVO);

	WorkItemSLAVO update(WorkItemSLAVO manageVO);

	Boolean delete(WorkItemSLAVO manageVo);

	List<WorkItemSLAVO> findSLAByWorkItemId(long workItemSLAVO);

	public boolean checkWISLACode(String slacode);
}
