package com.scube.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scube.crm.bo.AccessBo;
import com.scube.crm.bo.PrivilegesBO;
import com.scube.crm.bo.RoleBO;
import com.scube.crm.dao.AccessDao;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.AccessVo;
import com.scube.crm.vo.PrivilegesVO;
import com.scube.crm.vo.RolesVO;

@Service
@Transactional
public class AccessServiceImpl implements AccessService {
	
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(AccessServiceImpl.class);

	@Autowired
	private AccessDao accessdao;

	@Override
	public AccessBo createSaveAccess(AccessBo accessBo) throws MySalesException{
		LOGGER.entry();
		try {
		AccessVo acccessVo=new AccessVo();
		
		acccessVo.setAccessName(accessBo.getAccessName());
		
		acccessVo=accessdao.accessCreateValue(acccessVo);
		if(0 <acccessVo.getAccessId() ) {
			accessBo.setAccessId(acccessVo.getAccessId());
			
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("createAccessValue has failed:" + ex.getMessage());
			}
			LOGGER.info("createAccessValue has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return accessBo;
	}

	

	@Override
	public List<AccessBo> viewAccess() throws MySalesException{
		LOGGER.entry();
		  List<AccessBo> listAccess=new ArrayList<AccessBo>();
		  List<AccessVo> listAccessvo=new ArrayList<AccessVo>();
		try {
			listAccessvo=accessdao.viewAccessDao();
		 
			int count = 0;
			for (AccessVo access : listAccessvo) {
				AccessBo accessBo=new AccessBo();
				int data = ++count;
				accessBo.setsNo(data);
				accessBo.setAccessId(access.getAccessId());
				accessBo.setAccessName(access.getAccessName());
				listAccess.add(accessBo);
			 

		}
		 
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("createAccessValue has failed:" + ex.getMessage());
			}
			LOGGER.info("createAccessValue has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return listAccess;
	}



	@Override
	public AccessBo updateAccessValue(AccessBo accessBo) throws MySalesException{
		
		AccessVo accessVo=new AccessVo();
		
		accessVo.setAccessId(accessBo.getAccessId());
		accessVo.setAccessName(accessBo.getAccessName());
		accessVo=accessdao.updateDao(accessVo);
		if(0 <accessVo.getAccessId() ) {
			accessVo.setAccessId(accessBo.getAccessId());
		}
		return accessBo;
	}



	@Override
	public int deleteAccess(AccessBo accessBo) throws MySalesException{
		LOGGER.entry();
		int count=0;
		try {
		AccessVo accessvo=new AccessVo();
		accessvo.setAccessId(accessBo.getAccessId());
		accessvo=accessdao.deleteAccess(accessvo);
		
		if(0 <accessvo.getAccessId()) {
			count= (int) accessvo.getAccessId();
		}
		}catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Access deletd has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("Access delete has failed:" + ne.getMessage() + ne);
		}
		return count;
	}



	@Override
	public List<AccessBo> getAccessList(AccessBo accessbo) throws MySalesException{
		LOGGER.entry();
		List<AccessBo> listBo=new ArrayList<AccessBo>();
		try {
		AccessVo accessvo=new AccessVo();
		List<AccessVo> listVo=new ArrayList<AccessVo>();
		accessvo.setAccessId(accessbo.getAccessId());
		accessvo.setAccessName(accessbo.getAccessName());
		listVo=accessdao.searchAccessValue(accessvo);
		
		if(null!=listVo && listVo.size()>0 && !listVo.isEmpty()) {
			int count = 0;
			for (AccessVo accessVo : listVo) {
				
				AccessBo access=new AccessBo();
				int sNo = ++count;
				access.setsNo(sNo);
				access.setAccessId(accessVo.getAccessId());
				access.setAccessName(accessVo.getAccessName());
				listBo.add(access);
			}
		}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getAccessList has failed:" + ex.getMessage());
			}
			LOGGER.info("getAccessList has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return listBo;
	}

	@Override
	public boolean checkAccessName(String accessName) throws MySalesException {
		LOGGER.entry();
		boolean checkAccessName=false;
		try {
		checkAccessName=accessdao.checkAccessName(accessName);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkAccessName has failed:" + ex.getMessage());
			}
			LOGGER.info("checkAccessName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return checkAccessName; 
	}



	@Override
	public long countOfAccess(AccessBo accessBO) {
		long countOfAccess=0;
		AccessVo accessVo = new AccessVo();
		accessVo.setAccessName(accessBO.getAccessName());
		accessVo.setDeleted(false);
		countOfAccess=accessdao.countOfAccess(accessVo);
		
		return countOfAccess;
	}



	@Override
	public List<AccessBo> listOfAccessByPagination(AccessBo accessBO) {
		
		AccessVo accessVo = new AccessVo();
		List<AccessVo> accessVoList = new ArrayList<>();
		List<AccessBo> accessBoList = new ArrayList<>();
		try {
			accessVo.setRecordIndex(accessBO.getRecordIndex());
			accessVo.setMaxRecord(accessBO.getMaxRecord());
			accessVo.setDeleted(false);
			accessVo.setAccessName(accessBO.getAccessName());
			accessVoList=accessdao.listOfAccessByPagination(accessVo);
			if(null!=accessVoList && !accessVoList.isEmpty() && 0<accessVoList.size()) {
				int sNo=accessBO.getRecordIndex();
				for (AccessVo AccessVo2 : accessVoList) {
					AccessBo accessBO2 = new AccessBo();
					accessBO2.setAccessId(AccessVo2.getAccessId());
					accessBO2.setAccessName(AccessVo2.getAccessName());
					accessBO2.setsNo(++sNo);
					accessBoList.add(accessBO2);
				}		
			}
			
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("checkAccessName has failed:" + ex.getMessage());
			}
			LOGGER.info("checkAccessName has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		
		return accessBoList;
	}



	@Override
	public long countOfAccessBySearch(AccessBo bo) {
		LOGGER.entry();
		long countOfAccess=0;
		try {
			AccessVo accessVo = new AccessVo();
			if(null!= bo) {
				accessVo.setAccessName(bo.getAccessName());
				accessVo.setDeleted(false);
			}
			countOfAccess = accessdao.countOfAccessBySearch(accessVo);
			if(0< countOfAccess) {
				return countOfAccess;
			}
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search Access has failed:" + ex.getMessage());
			}
			LOGGER.info("Search Access has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return countOfAccess;
	}



	@Override
	public AccessBo getAccessId(AccessBo bo) {
		AccessBo accessBO = new AccessBo();
		try {
		AccessVo accessVO = new AccessVo();
		accessVO.setAccessId(bo.getAccessId());
		accessVO=accessdao.getAccessId(accessVO);
		accessBO.setAccessName(accessVO.getAccessName());
		accessBO.setAccessId(accessVO.getAccessId());
		accessBO.setCreated(accessVO.getCreated());
		accessBO.setModified(accessVO.getModified());
		accessBO.setDeleted(false);
		}catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getRoleid has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("getRoleid has failed:" + ne.getMessage() + ne);
		}
		return accessBO;
	}

	
	

}
