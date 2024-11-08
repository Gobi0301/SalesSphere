package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.vo.SkillsVO;
import com.scube.crm.vo.User;

public interface SkillsDao {

	long create(SkillsVO skillsVO);

	long retrieveCount(SkillsVO skillsVO);

	List<SkillsVO> findAll(SkillsVO skillsVO);

	SkillsVO getfindById(SkillsVO skillsVo);

	SkillsVO update(SkillsVO skillsVO);

	Boolean delete(SkillsVO skillsVo);

	boolean checkskillCode(String skillcode, long companyId);

	boolean checkskillName(String skillname, long companyId);

	
}
