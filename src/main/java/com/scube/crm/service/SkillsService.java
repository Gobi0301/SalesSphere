package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.SkillsBO;

public interface SkillsService {

	SkillsBO save(SkillsBO skillsBO);

	long retrieveCount(SkillsBO skillsBO);

	List<SkillsBO> findAll(SkillsBO skillsBO);

	SkillsBO findById(SkillsBO skillsBO);

	SkillsBO update(SkillsBO skillsBO);

	boolean delete(SkillsBO skillsBO);

	boolean checkskillCode(String skillcode, long companyId);

	boolean checkskillName(String skillname, long companyId);

	

	
}
