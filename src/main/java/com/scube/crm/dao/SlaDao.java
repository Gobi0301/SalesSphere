package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.vo.SlaVO;

public interface SlaDao {

	long create(SlaVO slaVO);

	long retrieveCount(SlaVO slaVO);

	List<SlaVO> findAll(SlaVO slaVO);

	SlaVO getfindById(SlaVO slaVo);

	SlaVO update(SlaVO slaVO);

	Boolean delete(SlaVO slaVo);

	boolean checkSlaCode(String slaCode);

}
