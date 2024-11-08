package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.bo.TaskManagementBO;
import com.scube.crm.vo.TaskManagementVO;
import com.scube.crm.vo.TaskTrackingStatusVO;
import com.scube.crm.vo.User;

public interface TaskManagementDao {

	long create(TaskManagementVO taskVO);

	long retrieveCount(TaskManagementVO taskVo);

	List<TaskManagementVO> findAll(TaskManagementVO taskVO);

	TaskManagementVO getfindById(TaskManagementVO taskVo);

	TaskManagementVO update(TaskManagementVO taskVO);

	Boolean delete(TaskManagementVO taskVo);

	TaskTrackingStatusVO saveTracking(TaskTrackingStatusVO vo);

	List<TaskTrackingStatusVO> retrieveTracking(long taskId);

	long retriveCount();

	TaskTrackingStatusVO taskTrackingStatus(long taskId);

	User findOpenTaskForEmployees(List<Long> employeeIds);

	long saveNewTask(TaskManagementVO taskVO);

	List<TaskManagementVO> findTaskById(TaskManagementVO taskVO);

	

	 
}
