package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.CaseManagementBO;
import com.scube.crm.bo.ClientBO;

public interface CaseManagementService {

	CaseManagementBO createCase(CaseManagementBO caseBO);

	List<CaseManagementBO> getListCase(CaseManagementBO caseBO);

	CaseManagementBO getCaseValue(CaseManagementBO caseBO);

	boolean caseValueUpdate(CaseManagementBO caseBO);

	int deleteCaseValues(int caseId);

	ClientBO getProduct(long id);

	long searchcount(CaseManagementBO caseBO); //For search purpose..

	List<CaseManagementBO> searchCase(CaseManagementBO caseBO);
	
	

	/* CaseManagementBO getProfile(long id); */

}
