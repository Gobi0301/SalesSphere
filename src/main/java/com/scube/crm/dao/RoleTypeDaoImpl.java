package com.scube.crm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.scube.crm.bo.RoleBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.PrivilegesVO;
import com.scube.crm.vo.RolesVO;

@Repository
public class RoleTypeDaoImpl implements RoleTypeDao {
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(RoleTypeDaoImpl.class);
	@Autowired
	private SessionFactory sessionfac;
	
	protected Session getSession() {
		return sessionfac.getCurrentSession();
	}

	@Override
	public RolesVO getroletype(RolesVO roleVOobj) throws MySalesException{
		LOGGER.entry();
		try {
			Session session=sessionfac.getCurrentSession();
			session.save(roleVOobj);	
		}
		catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Role creation has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("Role creation  has failed:" + ne.getMessage() + ne);
		}
		return roleVOobj;
	}

	@Override
	public List<RoleBO> viewrole()throws MySalesException {
		LOGGER.entry();
		RoleBO roleBOobj=null;
		int count=1;
		List<RoleBO> viewBOobj=new ArrayList<>();
		List<RolesVO> viewVOobj=new ArrayList<>();

		Session session=sessionfac.getCurrentSession();

		try {
			Criteria crit=session.createCriteria(RolesVO.class);
			viewVOobj=crit.list();

			for (RolesVO roleTypeVO : viewVOobj) {
				int data;
				data=count++;
				roleBOobj =new RoleBO();
				roleBOobj.setRoleId(roleTypeVO.getRoleId());
				roleBOobj.setRoleName(roleTypeVO.getRoleName());
				roleBOobj.setsNo(data);
				if(roleTypeVO.getRoleName()!=null) {
					viewBOobj.add(roleBOobj);
				}
			}
		}
		catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Role view has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("Role view  has failed:" + ne.getMessage() + ne);
		}
		return viewBOobj;
	}
	/*
	 * @Override public RoleTypeVO geteditrole(long id) { // TODO Auto-generated
	 * method stub
	 * 
	 * RoleTypeVO roleVOobj=new RoleTypeVO(); Session
	 * session=sessionfac.getCurrentSession(); Criteria
	 * crit=session.createCriteria(RoleTypeVO.class);
	 * crit.add(Restrictions.eq("roleId",id )); roleVOobj=(RoleTypeVO)
	 * crit.uniqueResult(); return roleVOobj; }
	 */

	@Override
	public RolesVO posteditrole(RolesVO roleVOobj)throws MySalesException {
		LOGGER.entry();
		try {
			Session session=sessionfac.getCurrentSession();
			session.saveOrUpdate(roleVOobj);
		}
		catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Role edit post failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("Role edit post failed:" + ne.getMessage() + ne);
		}

		return roleVOobj;
	}

	@Override
	public RolesVO deletepost(RolesVO roleVOobj) throws MySalesException{
		LOGGER.entry();
		try {
			Session session=sessionfac.getCurrentSession();
			session.delete(roleVOobj);
		}catch (Exception e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Role delete failed:" + e.getMessage() + e);
			}
			LOGGER.info("Role delete failed:" + e.getMessage() + e);
		}finally {
			LOGGER.exit();
		}
		return roleVOobj;
	}

	@Override
	public RolesVO getroleid(RolesVO roletypevo) throws MySalesException {
		LOGGER.entry();
		RolesVO roleTypeVO = new RolesVO();
		Session session = sessionfac.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(RolesVO.class);
			criteria.add(Restrictions.eq("roleId",roletypevo.getRoleId()));
			roleTypeVO=(RolesVO) criteria.uniqueResult();
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return roleTypeVO;
	}

	@Override
	public List<RolesVO> getRolelist(RolesVO rolevo)throws MySalesException {
		LOGGER.entry();
		RolesVO roleTypeVO = new RolesVO();
		List<RolesVO> listRoleVO=new ArrayList<>();
		Session session = sessionfac.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(RolesVO.class);
			criteria.add(Restrictions.eq("roleId",rolevo.getRoleId()));
			roleTypeVO=(RolesVO) criteria.uniqueResult();
			
			if(null!=roleTypeVO) {
				listRoleVO.add(roleTypeVO);
			}
		}
		catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getRolelist has failed:" + ex.getMessage());
			}
			LOGGER.info("getRolelist has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return listRoleVO;
	}

	@Override
	public RolesVO createRolePrivileges(RolesVO rolevo)throws MySalesException {
		LOGGER.entry();
		try {
		Session session = sessionfac.getCurrentSession();
		session.saveOrUpdate(rolevo);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("createRolePrivileges has failed:" + ex.getMessage());
			}
			LOGGER.info("createRolePrivileges has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return rolevo;
	}

	@Override
	public List<RolesVO> viewrole(List<RolesVO> rolesvo)throws MySalesException {
		LOGGER.entry();
		List<RolesVO> viewVOobj=new ArrayList<>();

		Session session=sessionfac.getCurrentSession();

		try {
			Criteria crit=session.createCriteria(RolesVO.class);
			crit.add(Restrictions.eq("isActive",true));
			viewVOobj=crit.list();
		}
		catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("viewrole has failed:" + ex.getMessage());
			}
			LOGGER.info("viewrole has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return viewVOobj;
	}

	@Override
	public RolesVO deleteRolePrivileges(long roleId,long privilegeId) throws MySalesException {
		LOGGER.entry();
		RolesVO roleVO=new RolesVO();
		int count=0;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mysaleslive","root","admin");
			Statement stmt=(Statement) con.createStatement();
			String deleteSql="delete from privileges_access where roleId="+roleId+" and privilegeId="+privilegeId+"";
			count=stmt.executeUpdate(deleteSql);
			
			if(0<count) {
				System.out.println("roleId and privilegeId has been deleted successfully");
			}
		}
		catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("deleterolePrivilege has failed:" + ex.getMessage());
			}
			LOGGER.info("deleterolePrivilege has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return roleVO;
	}

	@Override
	public List<RolesVO> searchRoleName(RolesVO roleVO) throws MySalesException{
		LOGGER.entry();
		List<RolesVO> roleVOList=new ArrayList<>();

		try {
			Session session=sessionfac.getCurrentSession();
			Criteria creteria=session.createCriteria(RolesVO.class);
			creteria.add(Restrictions.ilike("roleName", roleVO.getRoleName().trim(), MatchMode.ANYWHERE));
			roleVOList=creteria.list();			
		}
		catch (final NullPointerException ne) {
			ne.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Role Search has failed:" + ne.getMessage() + ne);
			}
			LOGGER.info("Role Search has failed:" + ne.getMessage() + ne);
		}
		return roleVOList;

	}

	@Override
	public List<Long> getPrivelegeIdList(RolesVO rolevo)throws MySalesException {
		LOGGER.entry();
		List<Long> priviledgeIdList=new ArrayList<Long>();
		Session session = sessionfac.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(RolesVO.class);
			criteria.createCriteria("listPrivilegesvo","ref");
			criteria.setProjection(Projections.groupProperty("ref.privilegeId"));
			criteria.add(Restrictions.eq("roleId",rolevo.getRoleId()));
			priviledgeIdList=(List<Long>) criteria.list();
		}
		catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getPrivelegeIdList has failed:" + ex.getMessage());
			}
			LOGGER.info("getPrivelegeIdList has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return priviledgeIdList;
	}

	@Override
	public PrivilegesVO getPrivilegelist(PrivilegesVO privilegesVO) throws MySalesException{
		PrivilegesVO privilegesVo = new PrivilegesVO();
		Session session = sessionfac.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(PrivilegesVO.class);
			criteria.add(Restrictions.eq("privilegeId",privilegesVO.getPrivilegeId()));
			privilegesVo= (PrivilegesVO) criteria.uniqueResult();
		}
		catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getPrivilegelist has failed:" + ex.getMessage());
			}
			LOGGER.info("getPrivilegelist has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return privilegesVo;
	}
	@Override
	public List<Long> getPrivelegeIdList(PrivilegesVO privilegesVO)throws MySalesException {
		List<Long> accessIdIdList=new ArrayList<Long>();
		Session session = sessionfac.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(PrivilegesVO.class);
			criteria.createCriteria("accessList","ref");
			criteria.setProjection(Projections.groupProperty("ref.accessId"));
			criteria.add(Restrictions.eq("privilegeId",privilegesVO.getPrivilegeId()));
			accessIdIdList=(List<Long>) criteria.list();
		}
		catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getPrivelegeIdList has failed:" + ex.getMessage());
			}
			LOGGER.info("getPrivelegeIdList has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return accessIdIdList;
	}

	@Override
	public boolean checkRoleName(String roleName) throws MySalesException {

		try {
			Session session=sessionfac.getCurrentSession();
			Criteria criteria = session.createCriteria(RolesVO.class);
			criteria.add(Restrictions.eq("roleName",roleName));
			List rolesVOList = criteria.list();
		
		if(null!=rolesVOList && 0<rolesVOList.size()) {
			return true;
		}
		}catch (Exception e) {
			System.out.println(e);
		}

		return false;
	}

	@Override
	public RolesVO isRoletypeExists(RolesVO roleVOobj) {
		RolesVO rolesVO=null;
		try {
			Session session=sessionfac.getCurrentSession();
			Criteria criteria = session.createCriteria(RolesVO.class);
			criteria.add(Restrictions.eq("roleName",roleVOobj.getRoleName()));
			rolesVO= (RolesVO) criteria.uniqueResult();
		
		if(null!=rolesVO) {
			return rolesVO;
		}
		}catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}
	
	@Override
	public long countOfRole(RolesVO rolesVO) {
		
		long countOfRole=0;
		Session session=sessionfac.getCurrentSession();	
		try {
			Criteria criteria=session.createCriteria(RolesVO.class);
			criteria.add(Restrictions.eq("isActive", true));
			criteria.setProjection(Projections.rowCount());
			/*
			 * if (null != rolesVO && 0 < rolesVO.getCompanyId()) {
			 * criteria.add(companyValidation(rolesVO.getCompanyId())); }
			 */
			if(null!=rolesVO.getRoleName() && !rolesVO.getRoleName().isEmpty()) {
				criteria.add(Restrictions.ilike("roleName", rolesVO.getRoleName().trim(), MatchMode.ANYWHERE));
			}
			countOfRole=(long)criteria.uniqueResult();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return countOfRole;
	}

	@Override
	public List<RolesVO> listOfRoleByPagination(RolesVO rolesVO) {
		
		List<RolesVO> rolesVOList=new ArrayList<>();
		Session session=sessionfac.getCurrentSession();	
		try {
			Criteria criteria=session.createCriteria(RolesVO.class);
			if( null!=rolesVO.getRoleName() && !rolesVO.getRoleName().isEmpty()) {
				criteria.add(Restrictions.ilike("roleName", rolesVO.getRoleName().trim(), MatchMode.ANYWHERE));
			}
			criteria.add(Restrictions.eq("isActive", true));
			criteria.setFirstResult(rolesVO.getRecordIndex());
			criteria.setMaxResults(rolesVO.getMaxRecord());
			rolesVOList=criteria.list();
			if(null!= rolesVOList) {
				return rolesVOList;
			}
			
		}catch (Exception e) {
			System.out.println(e);
		}		
		return rolesVOList;
	}

	@Override
	public long countOfRoleBySearch(RolesVO rolesVO) {
		LOGGER.entry();
		long countOfRole=0;
		Session session=sessionfac.getCurrentSession();
		try {
			Criteria criteria=session.createCriteria(RolesVO.class);
			criteria.add(Restrictions.eq("isActive", true));
			if( null!=rolesVO.getRoleName() && !rolesVO.getRoleName().isEmpty()) {
				criteria.add(Restrictions.ilike("roleName", rolesVO.getRoleName().trim(), MatchMode.ANYWHERE));
			}
				criteria.setProjection(Projections.rowCount());
				countOfRole=(long)criteria.uniqueResult();
				
				if(0< countOfRole) {
					return countOfRole;
				}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}	
		return countOfRole;
	}
	
	@Override
	public long roleCount(RolesVO roleVo) throws MySalesException {
		// TODO Auto-generated method stub
		long count = 0;
		try {
			 Criteria cr = getSession().createCriteria(RolesVO.class);
			 cr.add(Restrictions.eq("isActive", true));
			 if(null != roleVo.getRoleName() && !roleVo.getRoleName().isEmpty()) {
				 cr.add(Restrictions.ilike("roleName", roleVo.getRoleName().trim(),MatchMode.ANYWHERE));
				 
			 }
			 cr.setProjection(Projections.rowCount());
			 count = (long)cr.uniqueResult();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}

	@Override
	public List<RolesVO> getRoleList(RolesVO roleVo) throws MySalesException {
		// TODO Auto-generated method stub
		List<RolesVO> rolevoList = new ArrayList<>();
		Session session = sessionfac.getCurrentSession();
		try {
			Criteria cr = session.createCriteria(RolesVO.class);
			if(null != roleVo.getRoleName() && !roleVo.getRoleName().isEmpty()) {
				cr.add(Restrictions.ilike("roleName", roleVo.getRoleName().trim(),MatchMode.ANYWHERE));
			}
			cr.add(Restrictions.eq("isActive", true));
			cr.setFirstResult(roleVo.getRecordIndex());
			cr.setMaxResults(roleVo.getMaxRecord());
			
			rolevoList = cr.list();
			if(null != rolevoList && !rolevoList.isEmpty() && rolevoList.size() > 0) {
				return rolevoList;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rolevoList;
	}
	
}
