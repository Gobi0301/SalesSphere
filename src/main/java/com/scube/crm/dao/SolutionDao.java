package com.scube.crm.dao;

import java.util.List;

import com.scube.crm.bo.SolutionBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.vo.SolutionVO;

public interface SolutionDao {

	SolutionBO createSolution(SolutionVO solutionVo);

	List<SolutionVO> lisOfSolution(SolutionVO solutionVo);

	SolutionVO editSolution(SolutionVO solutionVo);
	
	SolutionBO updateSolution(SolutionVO solutionVo);
	
	boolean deleteSolution(SolutionVO svo);

	SolutionVO viewSoltion(int solutionId);

	public long Solcount(SolutionVO solutionVo)throws MySalesException;

	

	

	
	
	

}
