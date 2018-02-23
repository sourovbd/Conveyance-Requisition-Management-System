package com.asd.cms.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asd.cms.model.TAdminView;
import com.asd.cms.model.TExpense;
import com.asd.cms.model.TRequisition;
import com.asd.cms.model.TUser;
import com.asd.cms.service.AdminService;
import com.asd.cms.service.MailService;
import com.asd.cms.service.UserService;
import com.asd.cms.util.Utilities;
import com.google.gson.Gson;

@Controller
@RequestMapping("/main")
public class AdminControllerRequisition {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserService UserService;
	
	@Autowired
	private MailService mailService;
	
	@RequestMapping("/adminReq")
	public String AdminView(Model model) {
	
		List<String> empIdList = adminService.getUniqueEmpIdReq();
		List<TAdminView> adminViewList  = new ArrayList<TAdminView>();
		List<TRequisition> requisitionList = new ArrayList<TRequisition>();
		List<TRequisition> requisitionDetailsList = new ArrayList<TRequisition>();
		
		for (String empId : empIdList) {

			TAdminView admin = new TAdminView();
			requisitionList = adminService.getRequisitionList(empId);
			TUser user = UserService.getUserByEmployeeID(empId);
			
			BigDecimal sum = new BigDecimal(0);
			for (TRequisition requisition : requisitionList) {
				
				 sum = sum.add(requisition.getReqQuantity());
			}
			admin.setAdminViewQuantity(sum);
			admin.setAdminViewId(empId);
			admin.setAdminViewName(user.getUserFullName());
			adminViewList.add(admin);
			
		}
		model.addAttribute("adminViewList",adminViewList);
		model.addAttribute("requisitionDetailsList", requisitionDetailsList);
	
		return "AdminDetailsPageRequisition"; 
	}
	
	@RequestMapping(value="/detailsReq/{id}",method = RequestMethod.GET)
	public String getDetailsInfo(@PathVariable("id") String id,Model model) {
		
		List<String> empIdList = adminService.getUniqueEmpIdReq();
		List<TAdminView> adminViewList  = new ArrayList<TAdminView>();
		List<TRequisition> requisitionList = new ArrayList<TRequisition>();
		List<TRequisition> requisitionDetailsList = new ArrayList<TRequisition>();
		
		try {
			for (String empId : empIdList) {

				TAdminView admin = new TAdminView();
				requisitionList = adminService.getRequisitionList(empId);
				TUser user = UserService.getUserByEmployeeID(empId.toString());
				BigDecimal sum = new BigDecimal(0);
				for (TRequisition requisition : requisitionList) {
					
					 sum = sum.add(requisition.getReqQuantity());
					 String entryDate = Utilities.convertDateToString(requisition.getReqEntryDate(), "dd-MMM-yyyy");
					 String submitDate = Utilities.convertDateToString(requisition.getReqSubmitDate(), "dd-MMM-yyyy");
					 
					 admin.setAdminRequisitionViewDate(entryDate);
					 admin.setAdminSubmitReqViewDate(submitDate);
					}
				admin.setAdminViewQuantity(sum);
				admin.setAdminViewId(empId);
				admin.setAdminViewName(user.getUserFullName());
				
				adminViewList.add(admin);
			}
			requisitionDetailsList = adminService.getDetailsInfoReq(Long.parseLong(id));
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		TUser user = UserService.getUserByEmployeeID(id);
		String heading = "Details view for "+user.getUserFullName()+"(ID: "+user.getUserLoginId()+")";
		
		model.addAttribute("detailsViewHeading",heading);
		model.addAttribute("adminViewList",adminViewList);
		model.addAttribute("requisitionDetailsList", requisitionDetailsList);
		model.addAttribute("userId",id);
		
		return "AdminDetailsPageRequisition";
	}

	@RequestMapping(value="/approveReq", method=RequestMethod.POST)
	private @ResponseBody String approveStatus( Model model,@RequestParam("userId") String userId, @RequestParam("id") String idArray) throws ParseException{  //Model model,@RequestParam("userId") String userId
		
		Gson gson = new Gson();
		List<Double> idList = gson.fromJson(idArray, List.class);
		List<TRequisition> requisitionDetailsList = new ArrayList<TRequisition>();
		for(double id: idList) {
			
			adminService.approveStatusDateReq((long)id);
			requisitionDetailsList = adminService.getDetailsInfoReq(Long.parseLong(userId));
		}
		
		TUser user = UserService.getUserByEmployeeID(Utilities.getCurrentLoginID());
		
		List<TUser> hrList = new ArrayList<TUser>();
		hrList = adminService.getHrListReq();
		String[] a = null;
		String subject = "Approved Requisition by ";
		String text1 = "Please pay Requisition approved by ";
		String text2 = ").To pay this request please visit following link http://192.168.1.2:9090/CMS/auth/login";
		if(!(idList.isEmpty())) {
			
			for (TUser hr : hrList) {
				
				//System.out.println("email of admins: "+hr.getUserEmail());
				//mailService.sendMail(user.getUserEmail(), hr.getUserEmail(),a,subject+UserService.getUserByEmployeeID(Utilities.getCurrentLoginID()).getUserFullName()+"(ID: "+Utilities.getCurrentLoginID()+")",text1+UserService.getUserByEmployeeID(Utilities.getCurrentLoginID()).getUserFullName()+"(ID: "+Utilities.getCurrentLoginID()+text2);
				mailService.sendMail(user.getUserEmail(),"atequer.rahman@asdbd.com",a,subject+UserService.getUserByEmployeeID(Utilities.getCurrentLoginID()).getUserFullName()+"(ID: "+Utilities.getCurrentLoginID()+")",text1+UserService.getUserByEmployeeID(Utilities.getCurrentLoginID()).getUserFullName()+"(ID: "+Utilities.getCurrentLoginID()+text2);
			}
			
		}
		List<String> empIdList = adminService.getUniqueEmpIdReq();
		List<TAdminView> adminViewList  = new ArrayList<TAdminView>();
		List<TRequisition> requisitionList = new ArrayList<TRequisition>();
		
		try {
			for (String empId : empIdList) {

				TAdminView admin = new TAdminView();
				requisitionList = adminService.getRequisitionList(empId);
				BigDecimal sum = new BigDecimal(0);
				for (TRequisition requisition : requisitionList) {
					
					 sum = sum.add(requisition.getReqQuantity());
					 String entryDate = Utilities.convertDateToString(requisition.getReqEntryDate(), "dd-MMM-yyyy");
					 String submitDate = Utilities.convertDateToString(requisition.getReqSubmitDate(), "dd-MMM-yyyy");
					 
					 admin.setAdminRequisitionViewDate(entryDate);
					 admin.setAdminSubmitReqViewDate(submitDate);
					}
				admin.setAdminViewQuantity(sum);
				admin.setAdminViewId(empId);
				admin.setAdminViewName(user.getUserFullName());
				
				adminViewList.add(admin);
			}
			requisitionDetailsList = adminService.getDetailsInfoReq(Long.parseLong(userId));
		}catch(Exception e) {
			
			e.printStackTrace();
		}

		String heading = "Details view for "+user.getUserFullName()+"(ID: "+user.getUserLoginId()+")";
		
		model.addAttribute("detailsViewHeading",heading);
		model.addAttribute("adminViewList",adminViewList);
		model.addAttribute("requisitionDetailsList", requisitionDetailsList);

		return gson.toJson(requisitionDetailsList);
		
	}
	
	@RequestMapping(value="/rejectReq", method=RequestMethod.POST)
	private @ResponseBody String rejectStatus(Model model,@RequestParam("id") String idArray,@RequestParam("userId") String userId) throws ParseException{
		
		Gson gson = new Gson();
		List<Double> idList = gson.fromJson(idArray, List.class);
		List<TRequisition> requisitionDetailsList = new ArrayList<TRequisition>();
		for(double id: idList) {
			
			adminService.rejectStatusDateReq((long) id);
			requisitionDetailsList = adminService.getDetailsInfoReq(Long.parseLong(userId));
		}
		TUser user = UserService.getUserByEmployeeID(Utilities.getCurrentLoginID());

		List<String> empIdList = adminService.getUniqueEmpIdReq();
		List<TAdminView> adminViewList  = new ArrayList<TAdminView>();
		List<TRequisition> requisitionList = new ArrayList<TRequisition>();
		
		try {
			for (String empId : empIdList) {

				TAdminView admin = new TAdminView();
				requisitionList = adminService.getRequisitionList(empId);
				BigDecimal sum = new BigDecimal(0);
				for (TRequisition requisition : requisitionList) {
					
					 sum = sum.add(requisition.getReqQuantity());
					 String entryDate = Utilities.convertDateToString(requisition.getReqEntryDate(), "dd-MMM-yyyy");
					 String submitDate = Utilities.convertDateToString(requisition.getReqSubmitDate(), "dd-MMM-yyyy");
					 
					 admin.setAdminRequisitionViewDate(entryDate);
					 admin.setAdminSubmitReqViewDate(submitDate);
					}
				admin.setAdminViewQuantity(sum);
				admin.setAdminViewId(empId);
				admin.setAdminViewName(user.getUserFullName());
				
				adminViewList.add(admin);
			}
			requisitionDetailsList = adminService.getDetailsInfoReq(Long.parseLong(userId));
		}catch(Exception e) {
			
			e.printStackTrace();
		}

		String heading = "Details view for "+user.getUserFullName()+"(ID: "+user.getUserLoginId()+")";
		
		model.addAttribute("detailsViewHeading",heading);
		model.addAttribute("adminViewList",adminViewList);
		model.addAttribute("requisitionDetailsList", requisitionDetailsList);

		return gson.toJson(requisitionDetailsList);
	}
	
	@RequestMapping(value="/approvedRequisitionView",method=RequestMethod.GET)
	private String RequisitionViewEntry(Model model) {
		
		List<TRequisition> requisitionList = new ArrayList<TRequisition>();
		
		model.addAttribute("requisitionList",requisitionList);
		
		return "AdminRequisitionViewPage";
	}
	@RequestMapping(value="/approvedRequisitionView",method=RequestMethod.POST)
	private String RequisitionView(Model model,@RequestParam("reqDateFrom") String reqDateFrom, @RequestParam("reqDateTo") String reqDateTo) {
		
		List<TRequisition> requisitionList = new ArrayList<TRequisition>();
		try{
		
			requisitionList =  adminService.getEntryViewDetailsReq(reqDateFrom, reqDateTo);
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		model.addAttribute("requisitionList",requisitionList);
		
		return "AdminRequisitionViewPage";
	}
}
