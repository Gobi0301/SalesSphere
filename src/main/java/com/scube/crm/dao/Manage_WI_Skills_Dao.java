package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.vo.WorkItemSkillsVO;

public interface Manage_WI_Skills_Dao {

	long create(WorkItemSkillsVO manageVO);

	long retrieveCount(WorkItemSkillsVO manageVo);

	List<WorkItemSkillsVO> findAll(WorkItemSkillsVO manageVO);

	WorkItemSkillsVO getFindById(WorkItemSkillsVO manageVO);

	Boolean delete(WorkItemSkillsVO manageVo);

	WorkItemSkillsVO update(WorkItemSkillsVO manageVO);

	List<Long> getSkillIdList(WorkItemSkillsVO manageVO);

	List<WorkItemSkillsVO> findSLAByWorkItemId(long workItemId);

	List<WorkItemSkillsVO> findAllList(WorkItemSkillsVO manageVO);

}
