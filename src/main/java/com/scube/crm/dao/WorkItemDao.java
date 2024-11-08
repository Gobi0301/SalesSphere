package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.WorkItemVO;

public interface WorkItemDao {

	long create(WorkItemVO workItemVO)throws MySalesException;

	long retrieveCount(WorkItemVO workItemVO)throws MySalesException;

	List<WorkItemVO> findAll(WorkItemVO workItemVO)throws MySalesException;

	WorkItemVO getfindById(WorkItemVO workItemVO);

	WorkItemVO update(WorkItemVO workItemVO);

	Boolean delete(WorkItemVO workItemVO);

	public boolean checkWorkitemCode(String workItemCode,long id)throws MySalesException;

	List<WorkItemVO> findAllWorkitem(WorkItemVO vo);


}
