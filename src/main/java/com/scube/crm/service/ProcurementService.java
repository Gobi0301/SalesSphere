package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.ApproveProcurementBO;
import com.scube.crm.bo.ProcurementBO;
import com.scube.crm.bo.RejectProcurementBO;

public interface ProcurementService {

	ProcurementBO insertProcurement(ProcurementBO bo);

	List<ProcurementBO> getListProcurement(ProcurementBO bo);

	ProcurementBO getProcurementValues(ProcurementBO bo);

	boolean procurementValueUpdate(ProcurementBO bo);

	int deleteProcurementValues(int procurementId);

	ApproveProcurementBO approveProcurement(ApproveProcurementBO approveBO);

	 

	ProcurementBO selectParticularView(ProcurementBO bo2);
	
	ProcurementBO getProfile(long id);
	
	ProcurementBO saveTracking(ProcurementBO bo);

	RejectProcurementBO createReject(RejectProcurementBO bo1);

	List<ProcurementBO> searchByValue(ProcurementBO procurementBO);

	long procurment(ProcurementBO procurementBO2);

	List<ApproveProcurementBO> getapprovedprocurementlist(long companyId);

	List<RejectProcurementBO> getrejectedprocurementlist(long companyId);

	 
	  

	 
	 
	 

	 

}
