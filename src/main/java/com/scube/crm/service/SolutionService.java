package com.scube.crm.service;

import java.util.List;

import com.scube.crm.bo.SolutionBO;
import com.scube.crm.exception.MySalesException;

public interface SolutionService {

	SolutionBO createSolution(SolutionBO solution);

	List<SolutionBO> listOfSolution(SolutionBO bo);

	SolutionBO editSolution(SolutionBO solutionId);
	
	SolutionBO updateSolution(SolutionBO sbo);

	boolean deleteSolution(SolutionBO solutionBo);

	SolutionBO viewSoltion(int solutionId);

	public long Solcount(SolutionBO bo)throws MySalesException;

//	public List<SolutionBO> viewSolution(SolutionBO bo);

	

	

}
