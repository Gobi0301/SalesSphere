package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.TaskManagementBO;
import com.scube.crm.vo.TaskTrackingStatusVO;

public interface TaskManagementService {

	TaskManagementBO save(TaskManagementBO taskBO);

	long retrieveCount(TaskManagementBO taskBO);

	List<TaskManagementBO> findAll(TaskManagementBO taskBO);

	TaskManagementBO findById(TaskManagementBO taskBO);

	TaskManagementBO update(TaskManagementBO taskBO);

	boolean delete(TaskManagementBO taskBO);

	TaskManagementBO saveTracking(TaskManagementBO bo);

	long retrieveCount();

	TaskTrackingStatusVO taskTrackingStatus(long taskId);

	TaskManagementBO saveNewTask(TaskManagementBO taskBO);


	void saveAll(List<TaskManagementBO> tasks);

	 

}
