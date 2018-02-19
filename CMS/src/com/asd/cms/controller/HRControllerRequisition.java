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

import com.asd.cms.model.TAdminView;
import com.asd.cms.model.TExpense;
import com.asd.cms.model.TRequisition;
import com.asd.cms.model.TUser;
import com.asd.cms.service.HrService;
import com.asd.cms.service.MailService;
import com.asd.cms.service.UserService;
import com.asd.cms.util.Utilities;
import com.google.gson.Gson;

@Controller
@RequestMapping("/main")
public class HRControllerRequisition {

	@Autowired
	private HrService hrService;
	
	@Autowired
	private UserService UserService;
	
	@Autowired
	private MailService mailService;
	
	@RequestMapping(value="/hrReq")
	public ModelAndView HrView() {
		ModelAndView model = new ModelAndView("HrReqPage");
		
		List<TAdminView> adminViewList  = new ArrayList<TAdminView>();
		List<TRequisition> requisitionList = new ArrayList<TRequisition>();
		List<String> empIdList = new ArrayList<String>();
		
		try{
			
			empIdList = hrService.getUniqueEmpIdReq();
			for (String empId : empIdList) {

				TAdminView admin = new TAdminView();
				requisitionList = hrService.getRequisitionList(empId);
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
		
			model.addObject("adminViewList", adminViewList);
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return model;
	}
	@RequestMapping(value="/hrReqDetails/{id}",method = RequestMethod.GET)
	public String getDetailsInfo(@PathVariable("id") String id,Model model) {
		
		List<String> empIdList = hrService.getUniqueEmpId();
		List<TAdminView> adminViewList  = new ArrayList<TAdminView>();
		List<TRequisition> requisitionList = new ArrayList<TRequisition>();
		List<TRequisition> requisitionDetailsList = new ArrayList<TRequisition>();
		
		try {
			for (String empId : empIdList) {

				TAdminView admin = new TAdminView();
				requisitionList = hrService.getRequisitionList(empId);
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
			requisitionDetailsList = hrService.getDetailsInfoReq(Long.parseLong(id));
		
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		TUser user = UserService.getUserByEmployeeID(id);
		String heading = "Details view for "+user.getUserFullName()+"(ID: "+user.getUserLoginId()+")";
		
		model.addAttribute("detailsViewHeading",heading);
		model.addAttribute("adminViewList",adminViewList);
		model.addAttribute("requisitionDetailsList", requisitionDetailsList);
		
		return "HrReqPage";
	}

	@RequestMapping(value="/hrapproveReq", method=RequestMethod.POST)
	private @ResponseBody String approveStatus(@RequestParam("id") String idArray) throws ParseException{
		
		Gson gson = new Gson();
		List<Double> idList = gson.fromJson(idArray, List.class);
		
		int size = idList.size();
		String[] str = new String[size];
		int i = 0;
		for(double id: idList) {

			String empID = Long.toString((long)id);
			TUser user = UserService.getUserByEmployeeID(empID);
			hrService.approveStatusDateReq(empID);

			str[i] = hrService.getEmployee(empID).getUserEmail();
			i++;
			
		}
		
		String[] a = null;
		String subject = "Requisition payment by ";
		String text = "Congratulation! Your Requisition requests are paid successfully.To view your Requisition status  please visit following link http://192.168.1.2:9090/CMS/auth/login";
		
		if(!(idList.isEmpty())) {
			
			TUser hr = UserService.getUserByEmployeeID(Utilities.getCurrentLoginID());
			for(int j = 0; j < size; j++) {
				
				mailService.sendMail(hr.userEmail ,str[j],a,subject+UserService.getUserByEmployeeID(Utilities.getCurrentLoginID()).getUserFullName()+"(ID: "+Utilities.getCurrentLoginID()+")",text);
			}
		}
				
		return gson.toJson("Success");
		
	}

	@RequestMapping(value="/hrRequisitionView",method=RequestMethod.GET)
	private String ConveyanceViewEntry(Model model) {
		
		List<TRequisition> requisitionList = new ArrayList<TRequisition>();
		
		model.addAttribute("requisitionList",requisitionList);
		
		return "HrRequisitionViewPage";
	}
	@RequestMapping(value="/hrRequisitionView",method=RequestMethod.POST)
	private String ConveyanceView(Model model,@RequestParam("reqDateFrom") String reqDateFrom, @RequestParam("reqDateTo") String reqDateTo) {
		
		List<TRequisition> requisitionList = new ArrayList<TRequisition>();
		try{
			
			requisitionList =  hrService.getRequisitionViewDetails(reqDateFrom, reqDateTo);

		}catch(Exception e){
			
			e.printStackTrace();
		}
		model.addAttribute("requisitionList",requisitionList);
		
		return "HrRequisitionViewPage";
	}
}
