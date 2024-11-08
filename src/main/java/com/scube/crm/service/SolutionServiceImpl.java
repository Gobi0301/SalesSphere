package com.scube.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.AdminUserBO;
import com.scube.crm.bo.InventoryBO;
import com.scube.crm.bo.SolutionBO;
import com.scube.crm.dao.SolutionDao;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.SolutionVO;
import com.scube.crm.vo.User;

@Service
@Transactional
public class SolutionServiceImpl implements SolutionService {
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(SolutionServiceImpl.class);
	@Autowired
	private SolutionDao solutionDao;

	@Override
	public SolutionBO createSolution(SolutionBO solution) {
		LOGGER.entry();
		try {
		SolutionVO solutionVo = new SolutionVO();
		solutionVo.setSolutionTitle(solution.getSolutionTitle());
		solutionVo.setCategory(solution.getCategory());
		solutionVo.setStatus(solution.getStatus());
		solutionVo.setDelete(false);
		solutionVo.setQuestion(solution.getQuestion());
		solutionVo.setAnswer(solution.getAnswer());
		solutionVo.setComments(solution.getComments());
		solutionVo.setDescription(solution.getDescription());
		solutionVo.setCompanyId(solution.getCompanyId());
		InventoryVO inventoryVO = new InventoryVO();
		String pro = solution.getInventoryBo().getServiceName();
		int id = 0;
		id = Integer.parseInt(pro);
		inventoryVO.setServiceId(id);
		solutionVo.setInventoryvo(inventoryVO);

		User user = new User();
		String name = solution.getAdminUserBo().getName();
		int userId = 0;
		userId = Integer.parseInt(name);
		user.setId(userId);
		solutionVo.setUservo(user);

		solution = solutionDao.createSolution(solutionVo);
		if (null != solutionVo) {
			BeanUtils.copyProperties(solutionVo, solution);
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("createSolution has been failed: " + ex.getMessage());
			}
			LOGGER.info("createSolution has failed: " + ex.getMessage());
		}
		finally {
			LOGGER.exit();
		}
		return solution;

	}

	@Override
	public List<SolutionBO> listOfSolution(SolutionBO bo) {
        LOGGER.entry(); 
		List<SolutionBO> boList = new ArrayList<SolutionBO>();
		List<SolutionVO> voList = new ArrayList<SolutionVO>();
		SolutionVO solutionVo = new SolutionVO();
		try {
		if (null != bo) {

			if (bo.getSolutionTitle() != null) {
				solutionVo.setSolutionTitle(bo.getSolutionTitle());
			}
			if (null != bo.getInventoryBo()) {
				InventoryVO inv = new InventoryVO();
				inv.setServiceId(bo.getInventoryBo().getServiceId());
				solutionVo.setInventoryvo(inv);
			}
			User user = new User();

			if (null != bo.getAdminUserBo()) {
				user.setId(bo.getAdminUserBo().getId());
				solutionVo.setUservo(user);
			}
			solutionVo.setRecordIndex(bo.getRecordIndex());
			solutionVo.setMaxRecord(bo.getMaxRecord());
			if(null != bo.getCompanyId()&& 0< bo.getCompanyId()) {
            solutionVo.setCompanyId(bo.getCompanyId());
			}
		}
		voList = solutionDao.lisOfSolution(solutionVo);

		for (SolutionVO solutionVO : voList) {
			SolutionBO bo1 = new SolutionBO();
			bo1.setSolutionId(solutionVO.getSolutionId());
			bo1.setSolutionTitle(solutionVO.getSolutionTitle());
			InventoryBO inventory = new InventoryBO();
			inventory.setServiceName(solutionVO.getInventoryvo().getServiceName());
			inventory.setServiceId(solutionVO.getInventoryvo().getServiceId());
			bo1.setInventoryBo(inventory);
			AdminUserBO admin = new AdminUserBO();
			admin.setName(solutionVO.getUservo().getName());
			admin.setId(solutionVO.getUservo().getId());
			bo1.setAdminUserBo(admin);
			boList.add(bo1);

		}
        }catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("listOfSolution has been failed: " + ex.getMessage());
			}
			LOGGER.info("listOfSolution has failed: " + ex.getMessage());
		}
		finally {
	         LOGGER.exit();
		}
		return boList;
	}

	@Override
	public SolutionBO editSolution(SolutionBO solution) {
		LOGGER.entry();
		try {
		SolutionVO solutionVo = new SolutionVO();
		solutionVo.setSolutionId(solution.getSolutionId());
		solutionVo = solutionDao.editSolution(solutionVo);
		if (null != solutionVo) {
			solution.setSolutionId(solutionVo.getSolutionId());
			solution.setSolutionTitle(solutionVo.getSolutionTitle());
			solution.setCategory(solutionVo.getCategory());
			solution.setStatus(solutionVo.getStatus());
			solution.setQuestion(solutionVo.getQuestion());
			solution.setAnswer(solutionVo.getAnswer());
			solution.setDescription(solutionVo.getDescription());
			solution.setComments(solutionVo.getComments());
			solution.setCompanyId(solutionVo.getCompanyId());
			if (null != solutionVo.getUservo()) {
				AdminUserBO adminuserbo = new AdminUserBO();
				adminuserbo.setUserId(solutionVo.getUservo().getId());
				adminuserbo.setName(solutionVo.getUservo().getName());
				solution.setAdminUserBo(adminuserbo);
			}

			if (null != solutionVo.getInventoryvo()) {
				InventoryBO productServiceBO = new InventoryBO();
				productServiceBO.setServiceId(solutionVo.getInventoryvo().getServiceId());
				productServiceBO.setServiceName(solutionVo.getInventoryvo().getServiceName());
				solution.setInventoryBo(productServiceBO);
             }
		}
		 }catch (Exception ex) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("editSolution has been failed: " + ex.getMessage());
				}
				LOGGER.info("editSolution has failed: " + ex.getMessage());
			}
			finally {
				LOGGER.exit();
			}
		return solution;
	}

	@Override
	public SolutionBO updateSolution(SolutionBO sbo) {
		LOGGER.entry();
		SolutionVO solutionVo = new SolutionVO();
		try {
			solutionVo.setSolutionId(sbo.getSolutionId());
			solutionVo.setSolutionTitle(sbo.getSolutionTitle());
			solutionVo.setCategory(sbo.getCategory());
			solutionVo.setStatus(sbo.getStatus());
			solutionVo.setDelete(false);
			solutionVo.setQuestion(sbo.getQuestion());
			solutionVo.setAnswer(sbo.getAnswer());
			solutionVo.setComments(sbo.getComments());
			solutionVo.setDescription(sbo.getDescription());
			solutionVo.setCompanyId(sbo.getCompanyId());
			InventoryVO inventoryVO = new InventoryVO();
			String pro = sbo.getInventoryBo().getServiceName();
			int id = 0;
			id = Integer.parseInt(pro);
			inventoryVO.setServiceId(id);
			solutionVo.setInventoryvo(inventoryVO);

			User user = new User();
			String name = sbo.getAdminUserBo().getName();
			int userid = 0;
			userid = Integer.parseInt(name);
			user.setId(userid);
			solutionVo.setUservo(user);

			sbo = solutionDao.updateSolution(solutionVo);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("updateSolution has been failed: " + ex.getMessage());
			}
			LOGGER.info("updateSolution has failed: " + ex.getMessage());
		}
		finally {
			LOGGER.exit();
		}
		return sbo;
	}

	@Override
	public boolean deleteSolution(SolutionBO solutionBo) {
		LOGGER.entry();
		SolutionVO svo = new SolutionVO();
		try {
		svo.setSolutionId(solutionBo.getSolutionId());
		svo.setCompanyId(solutionBo.getCompanyId());
		svo.setDelete(true);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("deleteSolution has been failed: " + ex.getMessage());
			}
			LOGGER.info("deleteSolution has failed: " + ex.getMessage());
		}
		finally {
			LOGGER.exit();
		}
		return solutionDao.deleteSolution(svo);
	}

	@Override
	public SolutionBO viewSoltion(int solutionId) {
		LOGGER.entry();
		SolutionBO bo = new SolutionBO();
		SolutionVO vo = new SolutionVO();
try {
		vo = solutionDao.viewSoltion(solutionId);
		if (null != vo) {
			bo.setSolutionId(vo.getSolutionId());
			bo.setSolutionTitle(vo.getSolutionTitle());
			bo.setCategory(vo.getCategory());
			bo.setQuestion(vo.getQuestion());
			bo.setAnswer(vo.getAnswer());
			bo.setStatus(vo.getStatus());
			bo.setDescription(vo.getDescription());
			bo.setComments(vo.getComments());

			if (null != vo.getInventoryvo()) {
				InventoryBO invBo = new InventoryBO();
				invBo.setServiceName(vo.getInventoryvo().getServiceName());
				invBo.setServiceId(vo.getInventoryvo().getServiceId());
				bo.setInventoryBo(invBo);

				if (null != vo.getUservo()) {
					AdminUserBO userBo = new AdminUserBO();
					userBo.setName(vo.getUservo().getName());
					userBo.setUserId(vo.getUservo().getId());
					bo.setAdminUserBo(userBo);
				}
			}

		}
}catch (Exception ex) {
	if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("viewSoltion has been failed: " + ex.getMessage());
	}
	LOGGER.info("viewSoltion has failed: " + ex.getMessage());
}
finally {
	LOGGER.exit();
}
		return bo;

	}

	@Override
	public long Solcount(SolutionBO bo) throws MySalesException {
		LOGGER.entry();
		long count = 0;
		try {
			SolutionVO solutionVo = new SolutionVO();
			if(null != bo.getCompanyId()&& 0< bo.getCompanyId()) {
			solutionVo.setCompanyId(bo.getCompanyId());
			}
			solutionVo.setSolutionTitle(bo.getSolutionTitle());
			count = solutionDao.Solcount(solutionVo);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Solcount has been failed: " + ex.getMessage());
			}
			LOGGER.info("Solcount has failed: " + ex.getMessage());
		}
		finally {
			LOGGER.exit();
		}

		return count;
	}
	


}
