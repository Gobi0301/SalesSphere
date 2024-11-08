package com.scube.crm.dao;

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

import com.scube.crm.bo.ProjectBO;
import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.InventoryVO;
import com.scube.crm.vo.ProjectTrackingStatusVO;
import com.scube.crm.vo.ProjectVO;

@Repository
public class ProjectDaoImpl extends BaseDao implements ProjectDao {
	
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(ProjectDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	@Override
	public int createProject(ProjectVO projectVO) throws MySalesException {
		
		LOGGER.entry();
		
		int id=0;
		Session session=sessionFactory.getCurrentSession();
		try {
		if(null!=projectVO) {
		 id=(int) session.save(projectVO);
		}
		 
		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}
		return id;
	}


	@Override
	public List<ProjectVO> selectAllProjects(ProjectVO projectVO)throws MySalesException {
		List<ProjectVO> list =new ArrayList<ProjectVO>();
		LOGGER.entry();
		try {
			Session session=sessionFactory.getCurrentSession();
			Criteria criteria=session.createCriteria(ProjectVO.class);
			criteria.add(Restrictions.eq("isDeleted", false));
			if(null!=projectVO.getCompanyId()&& 0< projectVO.getCompanyId() ){
			criteria.add(companyValidation(projectVO.getCompanyId()));
			}
			criteria.setFirstResult(projectVO.getRecordIndex());
			criteria.setMaxResults(projectVO.getMaxRecord());
			if(null != projectVO.getProjectName() && !projectVO.getProjectName().isEmpty()) {
				criteria.add(Restrictions.ilike("projectName", projectVO.getProjectName().trim(), MatchMode.ANYWHERE));
			}
			list = criteria.list();
		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}
		
		return list;
	}


	@Override
	public ProjectVO getProjectById(ProjectVO projectVo)throws MySalesException {
		ProjectVO projectVO=new ProjectVO();
		try {	
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(ProjectVO.class);
			criteria.add(Restrictions.eq("projectId", projectVo.getProjectId()));
			projectVO = (ProjectVO) criteria.uniqueResult();
					
	} catch (Exception ex) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Edit Project has failed:" + ex.getMessage());
		}
		LOGGER.info("Edit Project has failed:" + ex.getMessage());
	}finally {
		LOGGER.exit();
	}
		
		return projectVO;
	}


	@Override
	public ProjectVO updateProject(ProjectVO projectVo)throws MySalesException{
		LOGGER.entry();
		ProjectBO projectBo=new ProjectBO();
		try {
			Session session = sessionFactory.getCurrentSession();
			if (null!=projectVo) {
				
				session.update(projectVo);
				projectBo.setProjectId(projectVo.getProjectId());
				if(null!=projectVo) {
					System.out.println("success");
				}
			}
			
		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update project has failed:" + ex.getMessage());
			}
			LOGGER.info("Update project has failed:" + ex.getMessage());
		}finally {
			LOGGER.exit();
		}
		return projectVo;
		
	}


	@Override
	public Boolean deleteProject(ProjectVO projectVo) throws MySalesException {
		LOGGER.entry();
		try {
			Session session=sessionFactory.getCurrentSession();
			ProjectVO vo=(ProjectVO) session.get(ProjectVO.class, projectVo.getProjectId());
			vo.setIsDeleted(true);
			if(0<vo.getProjectId()){
				session.saveOrUpdate(vo);
				return true;
			}

		}catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}

		return false;
	}


	@Override
	public long projectCount(ProjectVO projectVo) throws MySalesException {
		LOGGER.entry();
		long count = 0;
		try {

			Criteria cr = getSession().createCriteria(ProjectVO.class);
			cr.add(Restrictions.eq("isDeleted", false));
			if(null != projectVo.getCompanyId()&& 0< projectVo.getCompanyId()) {
			cr.add(companyValidation(projectVo.getCompanyId()));
			}
			/*
			 * if(null!=projectVo && null!=projectVo.getProjectName()) {
			 * cr.add(Restrictions.eq("projectName",projectVo.getProjectName())); }
			 */
			if( null!=projectVo.getProjectName() && !projectVo.getProjectName().isEmpty()) {
				cr.add(Restrictions.ilike("projectName", projectVo.getProjectName().trim(), MatchMode.ANYWHERE));
			}
			cr.setProjection(Projections.rowCount());
			count = (long) cr.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		}finally {
			LOGGER.exit();
		}
		return count;
	}
	@Override
	public ProjectVO viewProject(ProjectVO vo) {
			LOGGER.entry();
			
	     try {
	            Session session = sessionFactory.getCurrentSession();
				Criteria criteria = session.createCriteria(ProjectVO.class);
				criteria.add(Restrictions.eq("projectId", vo.getProjectId()));
				vo = (ProjectVO) criteria.uniqueResult();

	     } catch (Exception ex) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("viewProject has failed:" + ex.getMessage());
				}
				LOGGER.info("viewProject has failed:" + ex.getMessage());
			} finally {
				LOGGER.exit();
			}


			return vo;

	}
	@Override
	public ProjectTrackingStatusVO saveTracking(ProjectTrackingStatusVO vo) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			long projectActivityid = (long) session.save(vo);
			vo.setProjectActivityid(projectActivityid);
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}
	@Override
	public List<ProjectTrackingStatusVO> retrieveTracking(long projectId) {
		LOGGER.entry();
		List<ProjectTrackingStatusVO> projecttactivityList = null;
		try {
			Criteria criteria = getSession().createCriteria(ProjectVO.class);
			criteria.add(Restrictions.eq("entityid", projectId)); 
			projecttactivityList = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return projecttactivityList;
	}
	@Override
	public ProjectVO getProjectObjectByName(ProjectVO projectVO) {

			 Session session=sessionFactory.getCurrentSession();
			 try {
				 Criteria criteria=session.createCriteria(ProjectVO.class);
				 if(null!=projectVO.getProjectName()) {
					 criteria.add(Restrictions.eq("projectName", projectVO.getProjectName()));
				 }
				 projectVO=(ProjectVO)criteria.uniqueResult();
			 }
			 catch (Exception e) {
				 // TODO: handle exception
				 System.out.println(e);
			 }

			 return projectVO;
		 }
	}


