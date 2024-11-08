package com.scube.crm.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.scube.crm.vo.HistoryVO;

@Repository
public class ActivityHistoryDAOImpl implements ActivityHistoryDAO {
	
	@Autowired
	private SessionFactory sessionfactory;
	
	protected Session getSession() {
		return sessionfactory.getCurrentSession();
	}

	@Override
	
	public HistoryVO activityLogHistory(HistoryVO historyvo) {
		
		try {
			Session session = getSession();
			long id = (long) session.save(historyvo);
			//session.flush();
			if (0 < id) {
				historyvo.setLogId(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return historyvo;
	}

	
}
