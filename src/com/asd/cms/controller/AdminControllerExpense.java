package com.asd.cms.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asd.cms.model.Mail;
import com.asd.cms.model.TAdminView;
import com.asd.cms.model.TExpense;
import com.asd.cms.model.TUser;
import com.asd.cms.service.AdminService;
import com.asd.cms.service.MailService;
import com.asd.cms.service.UserService;
import com.asd.cms.util.Utilities;
import com.google.gson.Gson;

@Controller
@RequestMapping("/main")
public class AdminControllerExpense {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserService UserService;
	
	@Autowired
	private MailService mailService;
	
	@RequestMapping("/admin")
	public String AdminView(Model model) {
	
		List<String> empIdList = adminService.getUniqueEmpId();
		List<TAdminView> adminViewList  = new ArrayList<TAdminView>();
		List<TExpense> expenseList = new ArrayList<TExpense>();
		List<TExpense> expenseDetailsList = new ArrayList<TExpense>();
		
		for (String empId : empIdList) {
			
			//System.out.println("Employee ID: "+empId);
			
			TAdminView admin = new TAdminView();
			expenseList = adminService.getExpenseList(empId);
			TUser user = UserService.getUserByEmployeeID(empId);
			
			//System.out.println("User ID: "+user.getUserLoginId());
			
			BigDecimal sum = new BigDecimal(0);
			for (TExpense expense : expenseList) {
				
				 sum = sum.add(expense.getExpenseAmount());
				 
				 //admin.setAdminViewDate(expense.getExpenseConveyanceDate());
			}
			admin.setAdminViewAmount(sum);
			admin.setAdminViewId(empId);
			//System.out.println("Employee ID: "+empId);
			admin.setAdminViewName(user.getUserFullName());
			//System.out.println("Employee Name: "+user.getUserFullName());
			adminViewList.add(admin);
			
		}
		model.addAttribute("adminViewList",adminViewList);
		model.addAttribute("expenseDetailsList", expenseDetailsList);
	
		return "AdminDetailsPage"; 
	}
	
	@RequestMapping(value="/details/{id}",method = RequestMethod.GET)
	public String getDetailsInfo(@PathVariable("id") String id,Model model) {
		
		List<String> empIdList = adminService.getUniqueEmpId();
		List<TAdminView> adminViewList  = new ArrayList<TAdminView>();
		List<TExpense> expenseList = new ArrayList<TExpense>();
		List<TExpense> expenseDetailsList = new ArrayList<TExpense>();
		
		try {
			for (String empId : empIdList) {

				TAdminView admin = new TAdminView();
				expenseList = adminService.getExpenseList(empId);
				TUser user = UserService.getUserByEmployeeID(empId.toString());
				BigDecimal sum = new BigDecimal(0);
				for (TExpense expense : expenseList) {
					
					 sum = sum.add(expense.getExpenseAmount());
					 String conveyanceDate = Utilities.convertDateToString(expense.getExpenseConveyanceDate(), "dd-MMM-yyyy");
					 String submitDate = Utilities.convertDateToString(expense.getExpenseSubmitDate(), "dd-MMM-yyyy");
					 
					 //System.out.println("Date after formatting:"+date);
					 admin.setAdminConveyanceViewDate(conveyanceDate);
					 admin.setAdminSubmitViewDate(submitDate);
					 //System.out.println("after 2nd conversion: "+Utilities.convertStringToDate(date,  "dd-MMM-yyyy"));
				}
				admin.setAdminViewAmount(sum);
				admin.setAdminViewId(empId);
				admin.setAdminViewName(user.getUserFullName());
				
				adminViewList.add(admin);
			}
			expenseDetailsList = adminService.getDetailsInfo(Long.parseLong(id));
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		TUser user = UserService.getUserByEmployeeID(id);
		String heading = "Details view for "+user.getUserFullName()+"(ID: "+user.getUserLoginId()+")";
		
		model.addAttribute("detailsViewHeading",heading);
		model.addAttribute("adminViewList",adminViewList);
		model.addAttribute("expenseDetailsList", expenseDetailsList);
		model.addAttribute("userId",id);
		
		return "AdminDetailsPage";
	}

	@RequestMapping(value="/approve", method=RequestMethod.POST)
	private @ResponseBody String approveStatus( Model model,@RequestParam("userId") String userId, @RequestParam("id") String idArray) throws ParseException{  //Model model,@RequestParam("userId") String userId
		
		Gson gson = new Gson();
		List<Double> idList = gson.fromJson(idArray, List.class);
		for (Double id : idList) {
			
			System.out.println("id from idList: "+id);
		}
		List<TExpense> expenseDetailsList = new ArrayList<TExpense>();
		for(double id: idList) {
			
			//System.out.println("id from idList inside Admin controller: "+idList);
			adminService.approveStatusDate((long)id);
			expenseDetailsList = adminService.getDetailsInfo(Long.parseLong(userId));
			//System.out.println("Size of expense list"+expenseDetailsList.size());
		}
		
		TUser user = UserService.getUserByEmployeeID(Utilities.getCurrentLoginID());
		
		List<TUser> hrList = new ArrayList<TUser>();
		hrList = adminService.getHrList();
		//System.out.println("hrList: "+ hrList.size());
		String[] a = null;
		String subject = "Approved Conveyance by ";
		String text1 = "Please pay conveyance approved by ";
		String text2 = ").To pay this request please visit following link http://192.168.1.2:9090/CMS/auth/login";
		if(!(idList.isEmpty())) {
			
			for (TUser hr : hrList) {
				
				//System.out.println("email of admins: "+hr.getUserEmail());
				//mailService.sendMail(user.getUserEmail(), hr.getUserEmail(),a,subject+UserService.getUserByEmployeeID(Utilities.getCurrentLoginID()).getUserFullName()+"(ID: "+Utilities.getCurrentLoginID()+")",text1+UserService.getUserByEmployeeID(Utilities.getCurrentLoginID()).getUserFullName()+"(ID: "+Utilities.getCurrentLoginID()+text2);
				mailService.sendMail(user.getUserEmail(),"atequer.rahman@asdbd.com",a,subject+UserService.getUserByEmployeeID(Utilities.getCurrentLoginID()).getUserFullName()+"(ID: "+Utilities.getCurrentLoginID()+")",text1+UserService.getUserByEmployeeID(Utilities.getCurrentLoginID()).getUserFullName()+"(ID: "+Utilities.getCurrentLoginID()+text2);
			}
			
		}
		List<String> empIdList = adminService.getUniqueEmpId();
		List<TAdminView> adminViewList  = new ArrayList<TAdminView>();
		List<TExpense> expenseList = new ArrayList<TExpense>();
		
		try {
			for (String empId : empIdList) {

				TAdminView admin = new TAdminView();
				expenseList = adminService.getExpenseList(empId);
				//TUser user = UserService.getUserByEmployeeID(empId.toString());
				BigDecimal sum = new BigDecimal(0);
				for (TExpense expense : expenseList) {
					
					 sum = sum.add(expense.getExpenseAmount());
					 String conveyanceDate = Utilities.convertDateToString(expense.getExpenseConveyanceDate(), "dd-MMM-yyyy");
					 String submitDate = Utilities.convertDateToString(expense.getExpenseConveyanceDate(), "dd-MMM-yyyy");
					 //System.out.println("Date after formatting:"+date);
					 admin.setAdminConveyanceViewDate(conveyanceDate);
					 admin.setAdminSubmitViewDate(submitDate);
					 //System.out.println("after 2nd conversion: "+Utilities.convertStringToDate(date,  "dd-MMM-yyyy"));
				}
				admin.setAdminViewAmount(sum);
				admin.setAdminViewId(empId);
				admin.setAdminViewName(user.getUserFullName());
				
				adminViewList.add(admin);
			}
			expenseDetailsList = adminService.getDetailsInfo(Long.parseLong(userId));
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		//TUser user = UserService.getUserByEmployeeID(id);
		String heading = "Details view for "+user.getUserFullName()+"(ID: "+user.getUserLoginId()+")";
		
		model.addAttribute("detailsViewHeading",heading);
		model.addAttribute("adminViewList",adminViewList);
		model.addAttribute("expenseDetailsList", expenseDetailsList);
		//model.addAttribute("userId",userId);
		//System.out.println("Size of expense list"+expenseDetailsList.size());
		return gson.toJson(expenseDetailsList);
		
	}
	
	@RequestMapping(value="/reject", method=RequestMethod.POST)
	private @ResponseBody String rejectStatus(Model model,@RequestParam("id") String idArray,@RequestParam("userId") String userId) throws ParseException{
		
		Gson gson = new Gson();
		List<Double> idList = gson.fromJson(idArray, List.class);
		for (Double id : idList) {
			
			System.out.println("id from idList: "+id);
		}
		
		List<TExpense> expenseDetailsList = new ArrayList<TExpense>();
		for(double id: idList) {
			
			adminService.rejectStatusDate((long) id);
			expenseDetailsList = adminService.getDetailsInfo(Long.parseLong(userId));
			//System.out.println("Size of expense list"+expenseDetailsList.size());
		}
		TUser user = UserService.getUserByEmployeeID(Utilities.getCurrentLoginID());
		
		List<String> empIdList = adminService.getUniqueEmpId();
		List<TAdminView> adminViewList  = new ArrayList<TAdminView>();
		List<TExpense> expenseList = new ArrayList<TExpense>();
		
		try {
			for (String empId : empIdList) {

				TAdminView admin = new TAdminView();
				expenseList = adminService.getExpenseList(empId);
				//TUser user = UserService.getUserByEmployeeID(empId.toString());
				BigDecimal sum = new BigDecimal(0);
				for (TExpense expense : expenseList) {
					
					 sum = sum.add(expense.getExpenseAmount());
					 String conveyanceDate = Utilities.convertDateToString(expense.getExpenseConveyanceDate(), "dd-MMM-yyyy");
					 String submitDate = Utilities.convertDateToString(expense.getExpenseConveyanceDate(), "dd-MMM-yyyy");
					 //System.out.println("Date after formatting:"+date);
					 admin.setAdminConveyanceViewDate(conveyanceDate);
					 admin.setAdminSubmitViewDate(submitDate);					 
					 //System.out.println("after 2nd conversion: "+Utilities.convertStringToDate(date,  "dd-MMM-yyyy"));
				}
				admin.setAdminViewAmount(sum);
				admin.setAdminViewId(empId);
				admin.setAdminViewName(user.getUserFullName());
				
				adminViewList.add(admin);
			}
			expenseDetailsList = adminService.getDetailsInfo(Long.parseLong(userId));
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		//TUser user = UserService.getUserByEmployeeID(id);
		String heading = "Details view for "+user.getUserFullName()+"(ID: "+user.getUserLoginId()+")";
		
		model.addAttribute("detailsViewHeading",heading);
		model.addAttribute("adminViewList",adminViewList);
		model.addAttribute("expenseDetailsList", expenseDetailsList);
		//model.addAttribute("userId",userId);
		//System.out.println("Size of expense list"+expenseDetailsList.size());
		return gson.toJson(expenseDetailsList);
	}
	
	/*private String ConveyanceEntryList() {
		
		List<TExpense> conveyanceViewList = new ArrayList<TExpense>();
		
		return "UsernameInConveyanceViewPage";
	}*/
	@RequestMapping(value="/adminConveyanceView",method=RequestMethod.GET)
	private String ConveyanceViewEntry(Model model) {
		
		List<TExpense> expenseList = new ArrayList<TExpense>();
		
		model.addAttribute("expenseList",expenseList);
		
		return "AdminConveyanceViewPage";
	}
	@RequestMapping(value="/adminConveyanceView",method=RequestMethod.POST)
	private String ConveyanceView(Model model,@RequestParam("expenseDateFrom") String dateFrom, @RequestParam("expenseDateTo") String dateTo) {
		List<TExpense> expenseList = new ArrayList<TExpense>();
		try{
		
			expenseList =  adminService.getConveyanceViewDetails(dateFrom, dateTo);
			
			/*for (TExpense tExpense : expenseList) {
				System.out.println(tExpense.getExpenseStatus());
			}*/
		}catch(Exception e){
			
			e.printStackTrace();
		}
		model.addAttribute("expenseList",expenseList);
		return "AdminConveyanceViewPage";
	}
}