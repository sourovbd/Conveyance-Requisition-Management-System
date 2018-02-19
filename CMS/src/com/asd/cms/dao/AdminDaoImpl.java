package com.asd.cms.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.asd.cms.model.TExpense;
import com.asd.cms.model.TRequisition;
import com.asd.cms.model.TUser;
import com.asd.cms.util.Utilities;

@Repository
public class AdminDaoImpl implements AdminDao {

	@Autowired
	private SessionFactory sessionFactory;
	public Session session;
	public Transaction tx;
	public String sql = "";
	Query query = null;
	
	@Required
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<String> getUniqueEmpId() {
		
		session = sessionFactory.openSession();
		List<String> empIdList = new ArrayList<String>();
		try{
			
			tx = session.beginTransaction();
			empIdList = session.createQuery("select distinct expenseEmployeeId from TExpense where expenseStatus="+1).list();
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
	
	public List<TExpense> getExpenseList(String empID){
		
		session = sessionFactory.openSession();
		List<TExpense> expenseList = new ArrayList<TExpense>();
		try{
			
			tx = session.beginTransaction();
			expenseList= session.createQuery("from TExpense where expenseEmployeeId = "+empID+"and expenseStatus="+1).list();
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
	public List<TExpense> getDetailsInfo(long id) {
		
		session = sessionFactory.openSession();
		List<TExpense> adminViewList = new ArrayList<TExpense>();
		try{
			
			tx = session.beginTransaction();
			adminViewList = session.createQuery("from TExpense where expenseEmployeeId = "+id+" and expenseStatus="+1).list();
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
		return adminViewList;
	}

	@Override
	public boolean approveStatusDate(Long id) {

		session = sessionFactory.openSession();
		boolean retVal = false;
		try {
			
			tx = session.beginTransaction();
			//System.out.println("id inside approve of adminDaoImpl id from AdminController: "+id);			
			session.createQuery("update TExpense set expenseStatus  = " + 2 + ",expenseApprovedRejectedDate = '"+ Utilities.getCurrentDate() +"' where expenseId = " + id).executeUpdate();				
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
			session.close();
		}
		
		return retVal;
	}

	@Override
	public boolean rejectStatusDate(Long id) {
	
		session = sessionFactory.openSession();
		boolean retVal = false;
		try {
			tx = session.beginTransaction();
			//System.out.println("Inside reject of Admin Dao Impl before query.");
			session.createQuery("update TExpense set expenseStatus  = " + -1 + ",expenseApprovedRejectedDate = '"+ Utilities.getCurrentDate() +"' where expenseId = " + id).executeUpdate();	
			//System.out.println("Inside reject of Admin Dao Impl after query.");
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
			session.close();
		}
		
		return retVal;
	}

	@Override
	public List<TUser> getHrList() {
		session = sessionFactory.openSession();
		List<TUser> hrList = new ArrayList<TUser>();
		try{
			
			tx = session.beginTransaction();
			hrList = session.createQuery("from TUser where userRole = 'HR'").list();
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
		return hrList;
	}

	@Override
	public List<TExpense> getConveyanceViewDetails(String dateFrom,String dateTo) {
		
		session = sessionFactory.openSession(); 
		List<TExpense> expenseList = new ArrayList<TExpense>();
		try {
			
			tx = session.beginTransaction();
			expenseList =  session.createQuery("from TExpense where expenseConveyanceDate between '"+dateFrom+"' and '"+dateTo+"' and expenseStatus in ("+ (-1)+","+2+","+3+") order by expenseConveyanceDate").list();
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
	
	/////////////////////////////////////// For Requisition \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
public List<String> getUniqueEmpIdReq() {
		
		session = sessionFactory.openSession();
		List<String> empIdList = new ArrayList<String>();
		try{
			
			tx = session.beginTransaction();
			empIdList = session.createQuery("select distinct reqEmployeeId from TRequisition where reqStatus="+1).list();
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
	
	public List<TRequisition> getRequisitionList(String empID){
		
		session = sessionFactory.openSession();
		List<TRequisition> requisitionList = new ArrayList<TRequisition>();
		try{
			
			tx = session.beginTransaction();
			requisitionList= session.createQuery("from TRequisition where reqEmployeeId = "+empID+"and reqStatus="+1).list();
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
		List<TRequisition> adminViewList = new ArrayList<TRequisition>();
		try{
			
			tx = session.beginTransaction();
			adminViewList = session.createQuery("from TRequisition where reqEmployeeId = "+id+" and reqStatus="+1).list();
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
		return adminViewList;
	}

	@Override
	public boolean approveStatusDateReq(Long id) {

		session = sessionFactory.openSession();
		boolean retVal = false;
		try {
			
			tx = session.beginTransaction();
			//System.out.println("id inside approve of adminDaoImpl id from AdminController: "+id);			
			session.createQuery("update TRequisition set reqStatus  = " + 2 + ",reqApprovedRejectedDate = '"+ Utilities.getCurrentDate() +"' where reqId = " + id).executeUpdate();				
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
			session.close();
		}
		
		return retVal;
	}

	@Override
	public boolean rejectStatusDateReq(Long id) {
	
		session = sessionFactory.openSession();
		boolean retVal = false;
		try {
			tx = session.beginTransaction();
			//System.out.println("Inside reject of Admin Dao Impl before query.");
			session.createQuery("update TRequisition set reqStatus  = " + -1 + ",reqApprovedRejectedDate = '"+ Utilities.getCurrentDate() +"' where reqId = " + id).executeUpdate();	
			//System.out.println("Inside reject of Admin Dao Impl after query.");
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
			session.close();
		}
		
		return retVal;
	}

	@Override
	public List<TUser> getHrListReq() {
		session = sessionFactory.openSession();
		List<TUser> hrList = new ArrayList<TUser>();
		try{
			
			tx = session.beginTransaction();
			hrList = session.createQuery("from TUser where userRole = 'HR'").list();
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
		return hrList;
	}

	@Override
	public List<TRequisition> getEntryViewDetailsReq(String reqDateFrom,String reqDateTo) {
		
		session = sessionFactory.openSession(); 
		List<TRequisition> requisitionList = new ArrayList<TRequisition>();
		try {
			
			tx = session.beginTransaction();
			requisitionList =  session.createQuery("from TRequisition where reqEntryDate between '"+reqDateFrom+"' and '"+reqDateTo+"' and reqStatus in ("+ (-1)+","+2+","+3+") order by reqEntryDate").list();
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
