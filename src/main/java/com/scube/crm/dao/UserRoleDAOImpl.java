package com.scube.crm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.scube.crm.exception.MySalesException;
import com.scube.crm.exception.MySalesLogger;
import com.scube.crm.vo.User;


@Repository
public class UserRoleDAOImpl implements UserRoleDAO{
	
	private static final MySalesLogger LOGGER = MySalesLogger.getLogger(UserRoleDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	
	@Override
	public User createUserRole(User uservo)throws MySalesException {
		LOGGER.entry();
		try {
		Session session = getSession();
		session.saveOrUpdate(uservo);
		}catch (Exception ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Create UserRoles has failed:" + ex.getMessage());
			}
			LOGGER.info("Create UserRoles has failed:" + ex.getMessage());
		} finally {
			LOGGER.exit();
		}
		return uservo;
	}
	@Override
	public User deleteUserRole(long id,long roleId) {
		/*Session session = getSession();
		session.update(user);*/
		
		int count=0;
		
		User user=new User();
		
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mysaleslive","root","admin");
			Statement stmt=(Statement) con.createStatement();
			String deleteSql="delete from users_roles where id="+id+" and roleId="+roleId+"";
			count=stmt.executeUpdate(deleteSql);
			
			if(0<count) {
				System.out.println("userId and roleId has been deleted successfully");
			}

		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		return user;
	}

	@Override
	public User updateUserRole(User uservo) {
		Session session = getSession();
		session.saveOrUpdate(uservo);
		return uservo;
 }
}