package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.bo.ApproveProcurementBO;
import com.scube.crm.bo.ProcurementBO;
import com.scube.crm.bo.RejectProcurementBO;
import com.scube.crm.vo.ActivityVO;
import com.scube.crm.vo.ApproveProcurementVO;
import com.scube.crm.vo.ProcurementVO;
import com.scube.crm.vo.RejectProcurementVO;

public interface ProcurementDao {

	ProcurementBO createProcurement(ProcurementVO procurementVO);

	List<ProcurementVO> getListProcurement(ProcurementVO procurementVO);

	ProcurementVO getProcurementValues(ProcurementVO procurementVO);

	boolean procurementUpdateValues(ProcurementVO procurementVO);





	 

	int deleteProject(ProcurementVO vo);

	ApproveProcurementVO createApprove(ApproveProcurementVO approveVO);

	 

	ProcurementVO selectParticularView(ProcurementVO vo);
	

	 

	ProcurementVO getProfile(long id);

	ActivityVO saveTracking(ActivityVO vo);

	List<ActivityVO> retrieveTracking(long procurementId, Long companyId);

	RejectProcurementBO createReject(RejectProcurementVO rejectVO);

	List<ProcurementVO> searchByValue(ProcurementVO vo);

	long ProcurementCount(ProcurementVO procurementVO);

	List<ApproveProcurementVO> getapprovedprocurement(long companyId);

	List<RejectProcurementVO> getrejectedprocurement(long companyId);

	 

	 

	 



}
