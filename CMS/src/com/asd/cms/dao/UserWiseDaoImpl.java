package com.asd.cms.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.asd.cms.model.TUser;

@Repository
public class UserWiseDaoImpl implements UserWiseDao {

	@Autowired
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction tx;
	//@Required
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public List<TUser> getUserList() {
		
		session = sessionFactory.openSession();
		List<TUser> userList = new ArrayList<TUser>();
		try{
			
			tx = session.beginTransaction();
			userList = session.createQuery("from TUser").list();
			tx.commit();	
		}
		catch(Exception e){
			
			e.printStackTrace();
			tx.rollback();
		}
		finally{
			
			session.flush();
			session.clear();
			session.close();
		}
		return userList;
	}
}
