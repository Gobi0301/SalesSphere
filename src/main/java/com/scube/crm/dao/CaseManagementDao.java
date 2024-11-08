package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.bo.CaseManagementBO;
import com.scube.crm.vo.CaseManagementVO;
import com.scube.crm.vo.Customer;


public interface CaseManagementDao {

	CaseManagementBO createCase(CaseManagementVO caseVO);

	List<CaseManagementVO> getListCase(CaseManagementVO caseVO);

	CaseManagementVO getCaseValues(CaseManagementVO caseVO);

	boolean caseUpdateValues(CaseManagementVO caseVO);

	int deleteCase(CaseManagementVO vo);

	Customer getProfile(long id);

	long searchcount(CaseManagementVO vo);

	List<CaseManagementVO> searchCase(CaseManagementVO caseVO);

	/* SalesOrderVO getProfile(long id); */

}
