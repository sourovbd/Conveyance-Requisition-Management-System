package com.asd.cms.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asd.cms.model.Mail;
import com.asd.cms.model.TAdminView;
import com.asd.cms.model.TExpense;
import com.asd.cms.model.TUser;
import com.asd.cms.service.HrService;
import com.asd.cms.service.MailService;
import com.asd.cms.service.UserService;
import com.asd.cms.util.Utilities;
import com.google.gson.Gson;

@Controller
@RequestMapping("/main")
public class HRControllerExpense {

	@Autowired
	private HrService hrService;
	
	@Autowired
	private UserService UserService;
	
	@Autowired
	private MailService mailService;
	
	@RequestMapping(value="/hr")
	public ModelAndView HrView() {
		ModelAndView model = new ModelAndView("HrPage");
		
		List<TAdminView> adminViewList  = new ArrayList<TAdminView>();
		List<TExpense> expenseList = new ArrayList<TExpense>();
		List<String> empIdList = new ArrayList<String>();
		
		try{
			
			empIdList = hrService.getUniqueEmpId();
			for (String empId : empIdList) {

				TAdminView admin = new TAdminView();
				expenseList = hrService.getExpenseList(empId);
				TUser user = UserService.getUserByEmployeeID(empId.toString());
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
			//model.addAttribute("adminViewList",adminViewList);
			model.addObject("adminViewList", adminViewList);
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return model;
	}
	@RequestMapping(value="/hrDetails/{id}",method = RequestMethod.GET)
	public String getDetailsInfo(@PathVariable("id") String id,Model model) {
		
		List<String> empIdList = hrService.getUniqueEmpId();
		List<TAdminView> adminViewList  = new ArrayList<TAdminView>();
		List<TExpense> expenseList = new ArrayList<TExpense>();
		List<TExpense> expenseDetailsList = new ArrayList<TExpense>();
		
		try {
			for (String empId : empIdList) {

				TAdminView admin = new TAdminView();
				expenseList = hrService.getExpenseList(empId);
				TUser user = UserService.getUserByEmployeeID(empId.toString());
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
			expenseDetailsList = hrService.getDetailsInfo(Long.parseLong(id));
		
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		TUser user = UserService.getUserByEmployeeID(id);
		String heading = "Details view for "+user.getUserFullName()+"(ID: "+user.getUserLoginId()+")";
		
		model.addAttribute("detailsViewHeading",heading);
		model.addAttribute("adminViewList",adminViewList);
		model.addAttribute("expenseDetailsList", expenseDetailsList);
		
		return "HrPage";
	}

	
	
	@RequestMapping(value="/hrapprove", method=RequestMethod.POST)
	private @ResponseBody String approveStatus(@RequestParam("id") String idArray) throws ParseException{
		
		Gson gson = new Gson();
		List<Double> idList = gson.fromJson(idArray, List.class);
		
		int size = idList.size();
		String[] str = new String[size];
		int i = 0;
		for(double id: idList) {
			
			//System.out.println("id for /approve from Admin Controller"+id);
			String empID = Long.toString((long)id);
			TUser user = UserService.getUserByEmployeeID(empID);
			hrService.approveStatusDate(empID);

			str[i] = hrService.getEmployee(empID).getUserEmail();
			i++;
			
		}
		/*for(int j = 0; j < size; j++)
		System.out.println("User mail: "+str[j]);*/
		
		String[] a = null;
		String subject = "Conveyance payment by ";
		String text = "Congratulation! Your conveyance requests are paid successfully.To view your conveyance status  please visit following link http://192.168.1.2:9090/CMS/auth/login";
		
		if(!(idList.isEmpty())) {
			
			TUser hr = UserService.getUserByEmployeeID(Utilities.getCurrentLoginID());
			for(int j = 0; j < size; j++) {
				
				mailService.sendMail(hr.userEmail ,str[j],a,subject+UserService.getUserByEmployeeID(Utilities.getCurrentLoginID()).getUserFullName()+"(ID: "+Utilities.getCurrentLoginID()+")",text);
			}
		}
				
		return gson.toJson("Success");
		
	}
	
	@RequestMapping(value="/userForm",method=RequestMethod.GET)
	public ModelAndView UserForm() {
		
		ModelAndView model = new ModelAndView("UserCreationPage");
		
		TUser user = new TUser();
		List<TUser> userList = new ArrayList<TUser>();
		try {
			
			userList = hrService.viewUser();
		}catch(Exception e){
			
			e.printStackTrace();
		}
		model.addObject("user",user);
		model.addObject("userList", userList);
		
		return model;
	}
	
	@RequestMapping(value="/createUser",method=RequestMethod.POST)
	public ModelAndView CreateUser(@ModelAttribute("user")TUser user) {
		
		ModelAndView model = new ModelAndView("redirect:/main/userForm");
		try{
			
			hrService.SaveOrUpdate(user);
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/editUser/{id}")
	public ModelAndView EditUser(@PathVariable("id") String id) {
		
		ModelAndView model = new ModelAndView("UserCreationPage");
		TUser user = new TUser();
		List<TUser> userList = new ArrayList<TUser>();
		
		try{
			user = hrService.getUser(id);
			userList = hrService.viewUser();
			//System.out.println("update completed.");
		}catch(Exception e){
			
			e.printStackTrace();
		}
		model.addObject("user",user);
		model.addObject("userList", userList);
		return model;
	}
	
	@RequestMapping(value="/deleteUser/{id}")
	public ModelAndView DeleteUser(@PathVariable("id") String id) {
		
		ModelAndView model = new ModelAndView("redirect:/main/userForm");
		try{
			hrService.deleteUser(id);
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/activateUser/{id}")
	public ModelAndView ActivateUser(@PathVariable("id") String id) {
	
		ModelAndView model = new ModelAndView("redirect:/main/userForm");
		try{
			hrService.activateUser(id);
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/deactivateUser/{id}")
	public ModelAndView DeactivateUser(@PathVariable("id") String id) {
	
		ModelAndView model = new ModelAndView("redirect:/main/userForm");
		try{
			hrService.deactivateUser(id);
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return model;
	}
	@RequestMapping(value="/hrConveyanceView",method=RequestMethod.GET)
	private String ConveyanceViewEntry(Model model) {
		
		List<TExpense> expenseList = new ArrayList<TExpense>();
		
		model.addAttribute("expenseList",expenseList);
		
		return "HrConveyanceViewPage";
	}
	@RequestMapping(value="/hrConveyanceView",method=RequestMethod.POST)
	private String ConveyanceView(Model model,@RequestParam("expenseDateFrom") String dateFrom, @RequestParam("expenseDateTo") String dateTo) {
		List<TExpense> expenseList = new ArrayList<TExpense>();
		try{
			
			expenseList =  hrService.getConveyanceViewDetails(dateFrom, dateTo);
			
			/*for (TExpense tExpense : expenseList) {
				System.out.println(tExpense.getExpenseStatus());
			}*/
		}catch(Exception e){
			
			e.printStackTrace();
		}
		model.addAttribute("expenseList",expenseList);
		return "HrConveyanceViewPage";
	}
}
