package com.asd.cms.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.asd.cms.model.TExpense;
import com.asd.cms.model.TRequisition;
import com.asd.cms.model.TUser;
import com.asd.cms.util.Utilities;

 @Repository
 class HrDaoImpl implements HrDao{

	//@Autowired
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
	public List<String> getUniqueEmpId() {
		session = sessionFactory.openSession();
		List<String> empIdList = new ArrayList<String>();
		try{
			
			tx = session.beginTransaction();
			empIdList = session.createQuery(" select distinct expenseEmployeeId from TExpense where expenseStatus="+2).list();
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
		return empIdList;
	}

	@Override
	public List<TExpense> getExpenseList(String empId) {
		
		session = sessionFactory.openSession();
		List<TExpense> expenseList = new ArrayList<TExpense>();
		try{
			
			tx = session.beginTransaction();
			expenseList= session.createQuery("from TExpense where expenseEmployeeId = "+empId+"and expenseStatus="+2).list();
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
		return expenseList;
	}

	@Override
	public boolean approveStatusDate(String id) {
		
		session = sessionFactory.openSession();
		boolean retVal = false;
		try {
			
			tx = session.beginTransaction();
			//System.out.println("id inside approve of adminDaoImpl id from AdminController: "+id);
			//System.out.println("Inside approve of Admin Dao Impl before query.");
			
			session.createQuery("update TExpense set expenseStatus = "+ 3+",expensePaidDate = '"+ Utilities.getCurrentDate() +"' where expenseStatus = "+ 2 +" and expenseEmployeeId = "+id).executeUpdate(); 
			
			retVal = true;	
			tx.commit();
			
		}catch(Exception e){
			
			e.printStackTrace();
			retVal = false;
			tx.rollback();
		}
		finally{
			
			session.clear();
			session.flush();
			//session.close();
		}
		
		return retVal;
	}

	@Override
	public boolean SaveOrUpdate(TUser user) {
		
		session = sessionFactory.openSession(); 
		boolean retVal = false;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(user);
			retVal = true;
			session.getTransaction().commit();
			
		}catch(Exception e) {
			
			e.printStackTrace();
			retVal = false;
			session.getTransaction().rollback();
		}
		finally {
			
			session.flush();
			session.clear();
			session.close();
		}
		return retVal;
	}

	@Override
	public List<TUser> viewUser() {
		
		session = sessionFactory.openSession();
		List<TUser> userList = new ArrayList<TUser>();
		try{
			
			tx = session.beginTransaction();
			userList= session.createQuery("from TUser").list();
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

	@Override
	public TUser getUser(String id) {
		session = sessionFactory.openSession(); 
		TUser user = new TUser();
		try {
			tx = session.beginTransaction();
			user = (TUser) session.get(TUser.class, id);
			tx.commit();
			
		}catch(Exception e) {
			
			e.printStackTrace();
			tx.rollback();
		}
		finally {
			
			session.clear();
			session.flush();
			session.close();
		}
		return user;
	}

	@Override
	public boolean deleteUser(String id) {
		
		session = sessionFactory.openSession(); 
		boolean retVal = false;
		try {
			tx = session.beginTransaction();
			TUser user = (TUser) session.get(TUser.class, id);
			//System.out.println("deleted obj: "+user);
			session.delete(user);
			retVal = true;
			tx.commit();
			
		}catch(Exception e) {
			
			e.printStackTrace();
			retVal = false;
			tx.rollback();
		}
		finally {
			
			session.clear();
			session.flush();
			session.close();
		}
		return retVal;
	}

	@Override
	public boolean activateUser(String id) {
		
		session = sessionFactory.openSession(); 
		boolean retVal = false;
		try {
			tx = session.beginTransaction();
			TUser user = (TUser) session.get(TUser.class, id);
			user.setUserStatus(true);
			session.update(user);
			retVal = true;
			tx.commit();
			
		}catch(Exception e) {
			
			e.printStackTrace();
			retVal = false;
			tx.rollback();
		}
		finally {
			
			session.clear();
			session.flush();
			session.close();
		}
		return retVal;
	}

	@Override
	public boolean deactivateUser(String id) {
		
		session = sessionFactory.openSession(); 
		boolean retVal = false;
		try {
			tx = session.beginTransaction();
			TUser user = (TUser) session.get(TUser.class, id);
			user.setUserStatus(false);
			session.update(user);
			retVal = true;
			tx.commit();
			
		}catch(Exception e) {
			
			e.printStackTrace();
			retVal = false;
			tx.rollback();
		}
		finally {
			
			session.clear();
			session.flush();
			session.close();
		}
		return retVal;
	}

	@Override
	public List<TExpense> getDetailsInfo(long id) {
		session = sessionFactory.openSession();
		List<TExpense> hrViewList = new ArrayList<TExpense>();
		try{
			
			tx = session.beginTransaction();
			hrViewList = session.createQuery("from TExpense where expenseEmployeeId = "+id+" and expenseStatus="+2).list();
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
		return hrViewList;
	}

	@Override
	public TUser getEmployee(String empId) {
		session = sessionFactory.openSession();
		TUser employee = new TUser();
		
		//System.out.println("employee(String) id from HrDaoImpl: "+empId);
		
		try{
			
			tx = session.beginTransaction();
			//employeeID = (TUser) session.createQuery("from TUser where userLoginId = "+empId);
			employee = (TUser) session.get(TUser.class, empId);
			//System.out.println("employeeID: "+employee.getUserLoginId());
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
		return employee;
	}

	@Override
	public TUser getEmployee(long empId) {
		
		session = sessionFactory.openSession();
		TUser employee = new TUser();
		
		//System.out.println("employee(long) id from HrDaoImpl: "+empId);
		
		try{
			
			tx = session.beginTransaction();
			//employeeID = (TUser) session.createQuery("from TUser where userLoginId = "+empId);
			employee = (TUser) session.get(TUser.class, empId);
			//System.out.println("employeeID: "+employee);
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
		return employee;
	}
	
	@Override
	public List<TExpense> getConveyanceViewDetails(String dateFrom,String dateTo) {
		
		session = sessionFactory.openSession(); 
		List<TExpense> expenseList = new ArrayList<TExpense>();
		try {
			
			tx = session.beginTransaction();
			expenseList =  session.createQuery("from TExpense where expensePaidDate between '"+dateFrom+"' and '"+dateTo+"' and expenseStatus = "+3+" order by expensePaidDate").list();
			tx.commit(); 
			
		}catch(Exception e) {
			
			e.printStackTrace();
			tx.rollback();			
		}
		finally {
			
			session.clear();
			session.flush();
			session.close();			
		}
		return expenseList;
	}

	@Override
	public List<String> getUniqueEmpIdReq() {
		
		session = sessionFactory.openSession();
		List<String> empIdList = new ArrayList<String>();
		try{
			
			tx = session.beginTransaction();
			empIdList = session.createQuery(" select distinct reqEmployeeId from TRequisition where reqStatus="+2).list();
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
		return empIdList;
	}

	@Override
	public List<TRequisition> getRequisitionList(String empId) {

		session = sessionFactory.openSession();
		List<TRequisition> requisitionList = new ArrayList<TRequisition>();
		try{
			
			tx = session.beginTransaction();
			requisitionList= session.createQuery("from TRequisition where reqEmployeeId = "+empId+"and reqStatus="+2).list();
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
		return requisitionList;
	}

	@Override
	public List<TRequisition> getDetailsInfoReq(long id) {
		
		session = sessionFactory.openSession();
		List<TRequisition> hrViewList = new ArrayList<TRequisition>();
		try{
			
			tx = session.beginTransaction();
			hrViewList = session.createQuery("from TRequisition where reqEmployeeId = "+id+" and reqStatus="+2).list();
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
		return hrViewList;
	}

	@Override
	public boolean approveStatusDateReq(String id) {
		
		session = sessionFactory.openSession();
		boolean retVal = false;
		try {
			
			tx = session.beginTransaction();
			session.createQuery("update TRequisition set reqStatus = "+ 3+",reqOrderedDate = '"+ Utilities.getCurrentDate() +"' where reqStatus = "+ 2 +" and reqEmployeeId = "+id).executeUpdate(); 
			retVal = true;	
			tx.commit();
			
		}catch(Exception e){
			
			e.printStackTrace();
			retVal = false;
			tx.rollback();
		}
		finally{
			
			session.clear();
			session.flush();
			//session.close();
		}
		
		return retVal;
	}

	@Override
	public List<TRequisition> getRequisitionViewDetails(String dateFrom,
			String dateTo) {

		session = sessionFactory.openSession(); 
		List<TRequisition> requisitionList = new ArrayList<TRequisition>();
		try {
			
			tx = session.beginTransaction();
			requisitionList =  session.createQuery("from TRequisition where reqOrderedDate between '"+dateFrom+"' and '"+dateTo+"' and reqStatus = "+3+" order by reqOrderedDate").list();
			tx.commit(); 
			
		}catch(Exception e) {
			
			e.printStackTrace();
			tx.rollback();			
		}
		finally {
			
			session.clear();
			session.flush();
			session.close();			
		}
		return requisitionList;
	}

}
