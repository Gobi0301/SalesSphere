package com.scube.crm.service;




import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.PrivilegesBO;
import com.scube.crm.bo.RoleBO;
import com.scube.crm.dao.RoleTypeDao;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.PrivilegesVO;
import com.scube.crm.vo.RolesVO;



@Service
@Transactional
public class RoleTypeServiceImpl implements RoleTypeService{
	
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(RoleTypeServiceImpl.class);
	@Autowired
	private RoleTypeDao roletypeDao;


	@Override
	public RoleBO getroletype(RoleBO roleBOObj) throws MySalesException{
		LOGGER.entry();
		try {
		RolesVO roleVOobj=new RolesVO();

		if(null!=roleBOObj.getRoleName()) {
			roleVOobj.setRoleName(roleBOObj.getRoleName());
		}
		roleVOobj.setDelete(false);
		roleVOobj.setActive(true);
		roleVOobj.setCreated(roleBOObj.getCreated());
		roleVOobj.setModified(roleBOObj.getModified());
		if(roleBOObj.getCompanyId()!=null) {
			roleVOobj.setCompanyId(roleBOObj.getCompanyId());
		}
		
		roleVOobj=roletypeDao.getroletype(roleVOobj);

		if(0<roleVOobj.getRoleId()) {

			roleBOObj.setRoleId(roleVOobj.getRoleId());
		}
		}catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Role creation has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("Role creation  has failed:" + ne.getMessage() + ne);
		}
		return roleBOObj;
	}


	@Override
	public List<RoleBO> viewrole() throws MySalesException{
		LOGGER.entry();
		List<RoleBO> viewroleobj=new ArrayList<>();
		try {
		viewroleobj=roletypeDao.viewrole();
		if(null!=viewroleobj && viewroleobj.size()>0 && !viewroleobj.isEmpty()) {
			System.out.println("hello");
		}
		}catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Role view has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("Role view  has failed:" + ne.getMessage() + ne);
		}
		return viewroleobj;
	}

	@Override
	public RoleBO posteditrole(RoleBO roleBOobj)throws MySalesException {
		LOGGER.entry();
		try {

		RolesVO roleVOobj=new RolesVO();

		roleVOobj.setRoleId(roleBOobj.getRoleId());
        roleVOobj.setRoleName(roleBOobj.getRoleName());
        roleVOobj.setCreated(roleBOobj.getCreated());
        roleVOobj.setModified(roleBOobj.getModified());
        roleVOobj.setActive(true); 

		roleVOobj=roletypeDao.posteditrole(roleVOobj);

		if(null!=roleVOobj) {

			roleBOobj.setRoleId(roleVOobj.getRoleId());
		}
		}catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Role edit post has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("Role edit post  has failed:" + ne.getMessage() + ne);
		}
		return roleBOobj;
	}

	@Override
	public int deleteroletype(RoleBO rolebo)throws MySalesException {
		LOGGER.entry();
		int count=0;
		try {

		RolesVO roleVOobj=new RolesVO();
		roleVOobj.setRoleId(rolebo.getRoleId());
		roleVOobj=roletypeDao.deletepost(roleVOobj);

		if(null!=roleVOobj) {

			count=(int) roleVOobj.getRoleId();

		}
		}catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Role deletd has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("Role delete has failed:" + ne.getMessage() + ne);
		}
		return count;
	}

	@Override
	public RoleBO getRoleid(RoleBO borole)throws MySalesException {
		LOGGER.entry();
		RoleBO roleBO = new RoleBO();
		try {
		RolesVO roletypevo = new RolesVO();
		roletypevo.setRoleId(borole.getRoleId());
		roletypevo=roletypeDao.getroleid(roletypevo);
		roleBO.setRoleName(roletypevo.getRoleName());
		roleBO.setRoleId(roletypevo.getRoleId());
		roleBO.setCreated(roletypevo.getCreated());
		roleBO.setModified(roletypevo.getModified());
		roleBO.setActive(true);
		}catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getRoleid has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("getRoleid has failed:" + ne.getMessage() + ne);
		}
		return roleBO;

	}

	@Override
	public List<RoleBO> getroleList(List<RoleBO> roleblist) throws MySalesException {
		List<RoleBO> rolelist = new ArrayList<RoleBO>();
		List<RolesVO> roleVOlist=new ArrayList<>();
		for(RoleBO rolbe :roleblist ){
			RolesVO rolevo = new RolesVO();
			rolevo.setRoleId(rolbe.getRoleId());
			roleVOlist=roletypeDao.getRolelist(rolevo);      
			RoleBO bo = new RoleBO();
			bo.setRoleId(rolevo.getRoleId());
			for (RolesVO rolesVO : roleVOlist) {
				bo.setRoleName(rolesVO.getRoleName());
				bo.setRoleId(rolesVO.getRoleId());
				rolelist.add(bo);
			}
		}
		return rolelist;
	}


	@Override
	public RoleBO getrolebyid(RoleBO rolebo)throws MySalesException {
		LOGGER.entry();
		RoleBO role = new RoleBO();
		try {
		List<RolesVO> rolesVOlist=new ArrayList<>();
		RolesVO rolevo = new RolesVO();
		rolevo.setRoleId(rolebo.getRoleId());
		rolesVOlist=roletypeDao.getRolelist(rolevo);
		List<Long> privilegeIdList=roletypeDao.getPrivelegeIdList(rolevo);
		role.setPrivilegeIds(privilegeIdList);
		role.setRoleId(rolevo.getRoleId());
		for (RolesVO roleVO : rolesVOlist) {
			if(null!=roleVO.getRoleName()) {
				role.setRoleName(roleVO.getRoleName());
			}
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getrolebyid has failed:" + ex.getMessage());
			}
			LOGGER.info("getrolebyid has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return role;
	}


	@Override
	public RolesVO createRolePrivileges(RolesVO rolevo) {

		/*		session.save(rolevo);
		 */		return null;
	}



	@Override
	public List<RoleBO> getroleLists(List<RoleBO> rolebo) throws MySalesException{
		LOGGER.entry();
		List<RoleBO> viewroleobj=new ArrayList<>();
		try {
		List<RolesVO> rolesvo = new ArrayList<RolesVO>();
		rolesvo=roletypeDao.viewrole(rolesvo);
		if(null!=rolesvo&&rolesvo.size()>0){
			for(RolesVO rolevo : rolesvo){
				List<PrivilegesBO> listbo = new ArrayList<PrivilegesBO>() ;   
				for(PrivilegesVO vo :rolevo.getListPrivilegesvo()){
					PrivilegesBO privolegesbo = new PrivilegesBO();
					privolegesbo.setPrivilegeId(vo.getPrivilegeId());
					privolegesbo.setPrivilegename(vo.getPrivilegesName());
					listbo.add(privolegesbo);
				}
				RoleBO rolesbo = new RoleBO();
				rolesbo.setRoleId(rolevo.getRoleId());
				rolesbo.setRoleName(rolevo.getRoleName());
				rolesbo.setPrivilegesbolist(listbo);
				viewroleobj.add(rolesbo);
			}
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getroleLists has failed:" + ex.getMessage());
			}
			LOGGER.info("getroleLists has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return viewroleobj;
	}


	@Override
	public List<RoleBO> searchRoleName(RoleBO roleBO) throws MySalesException{
		LOGGER.entry();
		List<RolesVO> roleVOList=new ArrayList<>();
		List<RoleBO> roleBOList=new ArrayList<>();
		try {
		RolesVO roleVO=new RolesVO();

		if(null!=roleBO.getRoleName()) {   

			roleVO.setRoleName(roleBO.getRoleName());
		}

		roleVOList=roletypeDao.searchRoleName(roleVO);

		if(null!=roleVOList&&!roleVOList.isEmpty()&&0<roleVOList.size()) {

			int count=0;

			for (RolesVO rolesVO : roleVOList) {

				RoleBO roleBOobj=new RoleBO();
				int sNo=++count;
				roleBOobj.setsNo(sNo);
				roleBOobj.setRoleName(rolesVO.getRoleName());
				roleBOobj.setRoleId(rolesVO.getRoleId());
				roleBOList.add(roleBOobj);
			}

		}
		}catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Role Search has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("Role Search has failed:" + ne.getMessage() + ne);
		}
		return roleBOList;
	}

	@Override
	public PrivilegesBO getPrivilegebyid(PrivilegesBO privilegesBO)throws MySalesException {
		LOGGER.entry();
		PrivilegesBO privilegesBo= new PrivilegesBO();
		try {
		PrivilegesVO privilegesVO = new PrivilegesVO();
		privilegesVO.setPrivilegeId(privilegesBO.getPrivilegeId());
		privilegesVO=roletypeDao.getPrivilegelist(privilegesVO);
		List<Long> privilegeIdList=roletypeDao.getPrivelegeIdList(privilegesVO);
		privilegesBo.setAccessIds(privilegeIdList);
		privilegesBo.setPrivilegeId(privilegesVO.getPrivilegeId());
		privilegesBo.setPrivilegename(privilegesVO.getPrivilegesName());
		}catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getPrivilegebyid has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("getPrivilegebyid has failed:" + ne.getMessage() + ne);
		}
		return privilegesBo;
	}


	@Override
	public boolean checkRoleName(String roleName) throws MySalesException {
     
		boolean checkRoleName=false;
		checkRoleName=roletypeDao.checkRoleName(roleName);
		return checkRoleName;
	}


	@Override
	public RoleBO isRoletypeExists(RoleBO roleBOObj) {
		LOGGER.entry();
		try {
		RolesVO roleVOobj=new RolesVO();

		if(null!=roleBOObj.getRoleName()) {
			roleVOobj.setRoleName(roleBOObj.getRoleName());
		}

		roleVOobj=roletypeDao.isRoletypeExists(roleVOobj);

		if(0<roleVOobj.getRoleId()) {

			roleBOObj.setRoleId(roleVOobj.getRoleId());
		}
		}catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Role creation has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("Role creation  has failed:" + ne.getMessage() + ne);
		}
		return roleBOObj;
	}
	
	@Override
	public long countOfRole(RoleBO roleBO) {
		long countOfRole=0;
		RolesVO rolesVO = new RolesVO();
		rolesVO.setActive(true);
		if (null != roleBO.getCompanyId()) { // company based retrieve condition
			rolesVO.setCompanyId(roleBO.getCompanyId());
		}
		if(null!=roleBO.getRoleName()&&!roleBO.getRoleName().isEmpty()) {
			rolesVO.setRoleName(roleBO.getRoleName());
		}
		
		countOfRole=roletypeDao.countOfRole(rolesVO);
		
		return countOfRole;
	}

	@Override
	public List<RoleBO> listOfRoleByPagination(RoleBO roleBO) {
		RolesVO rolesVO = new RolesVO();
		List<RolesVO> rolesVOList=new ArrayList<>();
		List<RoleBO> roleBOList=new ArrayList<>();
		
		rolesVO.setRecordIndex(roleBO.getRecordIndex());
		rolesVO.setMaxRecord(roleBO.getMaxRecord());
		rolesVO.setActive(true);
		if(null != roleBO.getCompanyId()&& 0< roleBO.getCompanyId() ) {
			rolesVO.setCompanyId(roleBO.getCompanyId());
			}
		rolesVO.setRoleName(roleBO.getRoleName());
		rolesVOList=roletypeDao.listOfRoleByPagination(rolesVO);
		if(null!=rolesVOList&&!rolesVOList.isEmpty()&&0<rolesVOList.size()) {
			int sNo=roleBO.getRecordIndex();
			for (RolesVO rolesVO2 : rolesVOList) {
				RoleBO roleBO2 = new RoleBO();
				roleBO2.setRoleId(rolesVO2.getRoleId());
				roleBO2.setRoleName(rolesVO2.getRoleName());
				roleBO2.setsNo(++sNo);
				roleBOList.add(roleBO2);
			}
			
		}
		
		
		return roleBOList;
	}



	@Override
	public long countOfRoleBySearch(RoleBO bo) {
		LOGGER.entry();
		long countOfRole=0;
		try {
			RolesVO rolesVO = new RolesVO();
			if(null!= bo) {
				rolesVO.setRoleName(bo.getRoleName());
				if(null!= bo.getCompanyId() && 0< bo.getCompanyId()) {
					rolesVO.setCompanyId(bo.getCompanyId());
				}
				rolesVO.setActive(true);
			}
			countOfRole = roletypeDao.countOfRoleBySearch(rolesVO);
			if(0< countOfRole) {
				return countOfRole;
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search Role has failed:" + ex.getMessage());
			}
			LOGGER.info("Search Role has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return countOfRole;
	}
	@Override
	public long roleCount(RoleBO rolebo) throws MySalesException {
		// TODO Auto-generated method stub
		long count = 0;
		try {
			RolesVO roleVo = new RolesVO();
			roleVo.setRoleName(rolebo.getRoleName());
            if(null != rolebo.getRoleName() && !rolebo.getRoleName().isEmpty()) {
            	roleVo.setRoleName(rolebo.getRoleName());
            }
           count = roletypeDao.roleCount(roleVo);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}


	@Override
	public List<RoleBO> getRoleList(RoleBO roleBo) throws MySalesException {
		// TODO Auto-generated method stub
		List<RoleBO> roleboList = new ArrayList<>();
	
		try {
			List<RolesVO> rolevoList = new ArrayList<>();
			RolesVO rolevo = new RolesVO();
			/* rolevo.setRoleName(roleBo.getRoleName()); */
			//rolevo.setDelete(false);
			rolevo.setActive(true);
			rolevo.setMaxRecord(roleBo.getMaxRecord());
			rolevo.setRecordIndex(roleBo.getRecordIndex());
			rolevoList = roletypeDao.getRoleList(rolevo);
			
			if(null != rolevoList && rolevoList.size() > 0 && !rolevoList.isEmpty()) {
				int sNo = roleBo.getRecordIndex();
				for(RolesVO roleVo:rolevoList) {
					RoleBO rolebo = new RoleBO();
					rolebo.setsNo(++sNo);
					rolebo.setRoleId(roleVo.getRoleId());
					rolebo.setRoleName(roleVo.getRoleName());
					rolebo.setActive(true);
					//rolebo.setDelete(false);
					roleboList.add(rolebo);
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return roleboList;
	}

}
