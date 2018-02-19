package com.asd.cms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asd.cms.model.TUser;
import com.asd.cms.service.ReportService;
import com.asd.cms.service.UserService;
import com.asd.cms.service.UserWiseReportService;

@Controller
@RequestMapping("/main")
public class ReportControllerReq {

	@Autowired
	private UserWiseReportService userWiseReportService;
	
	@Autowired
	private UserService userService;
	
	@Resource(name="reportService")
	private ReportService reportService;

	@RequestMapping("/reportReq")
	public String getReport(Model model) {
		
		//ModelAndView model = new ModelAndView("Report.html");
		List<TUser> userList = new ArrayList<>();
		userList = userWiseReportService.getUserList();		
		model.addAttribute("userList", userList);
		
		return "UserWiseReportReq";
	}
	
	@RequestMapping("/userWiseReportReq")
	public void viewReport(HttpServletRequest request, HttpServletResponse response) {
		//System.out.println("Entered into Report Service");
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		String employeeId = request.getParameter("employeeId"); 
		String outputFormat = request.getParameter("outputFormat");
		
		//System.out.println("Date From : " + dateFrom);
		//System.out.println("Date To : " + dateTo);
		//System.out.println("Emp ID : " + employeeId);
		//System.out.println("Output Format: " + outputFormat);
		
		TUser user = userService.getUserByEmployeeID(employeeId);
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("dateFrom", dateFrom);
		param.put("dateTo", dateTo);
		param.put("empID",employeeId);
		param.put("empName", user.getUserFullName().toUpperCase());
		
		String path = request.getSession().getServletContext().getRealPath("/resources/jasper/UserWiseRequisitionReport.jrxml");
		//System.out.println(path);
		reportService.generateReport(path, outputFormat, param, response);
	}
}
