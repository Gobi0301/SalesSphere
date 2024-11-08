package com.scube.crm.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.bo.TaskManagementBO;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.TaskManagementVO;
import com.scube.crm.vo.TaskTrackingStatusVO;
import com.scube.crm.vo.User;

@Repository
public class TaskManagementDaoImpl implements TaskManagementDao {

	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(TaskManagementDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public long create(TaskManagementVO taskVO) {
		LOGGER.entry();

		long id = 0;
		Session session = sessionFactory.getCurrentSession();
		try {
			if (null != taskVO) {
				id = (long) session.save(taskVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return id;
	}

	@Override
	public long retrieveCount(TaskManagementVO taskVo) {
		LOGGER.entry();
		long count = 0;
		try {

			Criteria cr = getSession().createCriteria(TaskManagementVO.class);
			// Join the leadsVO association
			cr.createAlias("leadsVO", "leads");
			cr.add(Restrictions.eq("isDelete", false));
			if(null!= taskVo.getCompanyId() && 0< taskVo.getCompanyId()) {
				cr.add(Restrictions.eq("companyId", taskVo.getCompanyId()));
			}
			if (null != taskVo.getLeadsVO() && null != taskVo.getLeadsVO().getFirstName()
					&& !taskVo.getLeadsVO().getFirstName().isEmpty()) {
				cr.add(
						Restrictions.ilike("leads.firstName", taskVo.getLeadsVO().getFirstName(), MatchMode.START));
			}
			cr.setProjection(Projections.rowCount());
			count = (long) cr.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return count;
	}

	@Override
	public List<TaskManagementVO> findAll(TaskManagementVO taskVO) {
		List<TaskManagementVO> list = new ArrayList<TaskManagementVO>();
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(TaskManagementVO.class);
			// Join the leadsVO association
			criteria.createAlias("leadsVO", "leads");
			/* criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); */
			criteria.add(Restrictions.eq("isDelete", false));
			if (0 < taskVO.getEntityId()) {
				criteria.add(Restrictions.eq("entityId", taskVO.getEntityId()));
			}
			criteria.setFirstResult(taskVO.getRecordIndex());
			criteria.setMaxResults(taskVO.getMaxRecord());
			if (null != taskVO.getLeadsVO() && null != taskVO.getLeadsVO().getFirstName()
					&& !taskVO.getLeadsVO().getFirstName().isEmpty()) {
				criteria.add(
						Restrictions.ilike("leads.firstName", taskVO.getLeadsVO().getFirstName().trim(), MatchMode.START));
			}
			if(null!= taskVO.getCompanyId() && 0< taskVO.getCompanyId() ) {
				criteria.add(Restrictions.eq("companyId", taskVO.getCompanyId()));
			}
			criteria.addOrder(Order.asc("taskId"));
			list = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}

		return list;
	}

	@Override
	public TaskManagementVO getfindById(TaskManagementVO taskVo) {

		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(TaskManagementVO.class);
			criteria.add(Restrictions.eq("taskId", taskVo.getTaskId()));
			taskVo = (TaskManagementVO) criteria.uniqueResult();

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Edit Task has failed:" + ex.getMessage());
			}
			LOGGER.info("Edit Task has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}

		return taskVo;
	}

	@Override
	public TaskManagementVO update(TaskManagementVO taskVO) {

		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			if (null != taskVO) {

				session.saveOrUpdate(taskVO);
				if (null != taskVO) {
					System.out.println("success");
				}
			}

		} catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Update Task has failed:" + ex.getMessage());
			}
			LOGGER.info("Update Task has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return taskVO;

	}

	@Override
	public Boolean delete(TaskManagementVO taskVo) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			TaskManagementVO vo = (TaskManagementVO) session.get(TaskManagementVO.class, taskVo.getTaskId());
			vo.setisDelete(true);
			if (0 < vo.getTaskId()) {
				session.saveOrUpdate(vo);
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}

		return false;
	}

	@Override
	public TaskTrackingStatusVO saveTracking(TaskTrackingStatusVO vo) {
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			long id = (long) session.save(vo);
			vo.setTaskTrackingId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public List<TaskTrackingStatusVO> retrieveTracking(long taskId) {
		LOGGER.entry();
		List<TaskTrackingStatusVO> taskupdateList = null;
		try {
			Criteria criteria = getSession().createCriteria(TaskTrackingStatusVO.class);
			// criteria.add(Restrictions.eq("entitytype", "Leads"));
			criteria.add(Restrictions.eq("entityid", taskId));
			if (null != criteria.list() && criteria.list().size() > 0) {
				taskupdateList = criteria.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("MobileNo Tracking History failed:" + e.getMessage());
			}
			LOGGER.info("MobileNo Tracking History failed:" + e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return taskupdateList;
	}

	@Override
	public long retriveCount() {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(TaskTrackingStatusVO.class);
		cr.setProjection(Projections.rowCount());
		long activityd = (long) cr.uniqueResult();
		if (0 < activityd) {
			return activityd;
		}
		return 0;
	}

	@Override
	public TaskTrackingStatusVO taskTrackingStatus(long taskId) {
		Session session = sessionFactory.getCurrentSession();
		TaskTrackingStatusVO activityVO = (TaskTrackingStatusVO) getSession().get(TaskTrackingStatusVO.class, taskId);

		if (null != activityVO) {

			return activityVO;
		}

		return null;
	}

	@Override
	public User findOpenTaskForEmployees(List<Long> employeeIds) {
		User employeeWithMinTasks = new User();

		String hql = "SELECT e.id as id, e.name as name, COUNT(t.taskId) AS tasks  FROM login e, task_management t where e.id in (:empIds)  GROUP BY e.id, e.name"
				+ " ORDER BY id ASC";

		Session session = sessionFactory.getCurrentSession();
		try {
			int count = 0;

			SQLQuery query = session.createSQLQuery(hql);
			query.setParameterList("empIds", employeeIds);

			List<Object[]> result = query.list();
			if (null != result) {

				for (Object[] obj : result) {

					if (count == 0) {
						Long employeeId = Long.valueOf(obj[0].toString());
						String employeeName = (String) obj[1].toString();
						Long taskCount = Long.valueOf(obj[2].toString());

						// Create an Employee object with the result

						employeeWithMinTasks.setId(employeeId);
						employeeWithMinTasks.setName(employeeName);

					}
					count++;
				}
				// Use employeeWithMinTasks which contains the employee with the minimum task
				// count
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("  Tracking History failed:" + e.getMessage());
			}
			LOGGER.info("  Tracking History failed:" + e.getMessage());
		}
		return employeeWithMinTasks;
	}

	@Override
	public long saveNewTask(TaskManagementVO taskVO) {
		LOGGER.entry();
		long id = 0;
		Session session = sessionFactory.getCurrentSession();
		try {
			if (null != taskVO) {
				id = (long) session.save(taskVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}
		return id;
	}

	@Override
	public List<TaskManagementVO> findTaskById(TaskManagementVO taskVO) {
		List<TaskManagementVO> list = new ArrayList<TaskManagementVO>();
		LOGGER.entry();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(TaskManagementVO.class);
			// Join the leadsVO association
			criteria.createAlias("leadsVO", "leads");
			criteria.add(Restrictions.eq("isDelete", false));
			if (0 < taskVO.getEntityId()) {
				criteria.add(Restrictions.eq("entityId", taskVO.getEntityId()));
			}
			if(null!= taskVO.getCompanyId()) {
				criteria.add(Restrictions.eq("companyId", taskVO.getCompanyId()));
			}
			if (null != taskVO.getLeadsVO() && 0<  taskVO.getLeadsVO().getLeadsId()) {
				criteria.add(
						Restrictions.eq("leads.leadsId", taskVO.getLeadsVO().getLeadsId()));
			}
			
			list = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage());
			}
			LOGGER.info(e.getMessage());
		} finally {
			LOGGER.exit();
		}

		return list;
	}
	

}
