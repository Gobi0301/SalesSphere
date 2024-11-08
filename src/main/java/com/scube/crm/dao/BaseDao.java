package com.scube.crm.dao;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class BaseDao {
	public Criterion companyValidation(Long companyId) {   //not mapping based company condition
		Criterion c=Restrictions.eq("companyId",companyId);
		
		return c;		
	}
	
	
	public Criterion companyMappingValidation(Long companyId) {     // mapping based company condition
		Criterion c=Restrictions.eq("company.companyId",companyId);
		
		return c;		
	}
	
	
	
}
