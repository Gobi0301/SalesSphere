package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.SlaBO;

public interface SlaService {

	SlaBO save(SlaBO slaBO);

	long retrieveCount(SlaBO slaBO);

	List<SlaBO> findAll(SlaBO slaBO);

	SlaBO findById(SlaBO slaBO);

	SlaBO update(SlaBO slaBO);

	boolean delete(SlaBO slaBO);

	boolean checkSlaCodeCheck(String slaCode);

}
