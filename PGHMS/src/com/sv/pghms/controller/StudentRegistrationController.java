package com.sv.pghms.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sv.pghms.model.TCourseDetails;
import com.sv.pghms.model.TResultForm;
import com.sv.pghms.service.AdminService;

@Controller
@RequestMapping("/main")
public class StudentRegistrationController {
	
	@Autowired
	private AdminService adminService;
		
	String editRegNoGlobal, courseNoGlobal, examHeldGlobal, batchNoGlobal;
	
	@RequestMapping(value="/studentEntry", method=RequestMethod.GET)
	public String getBatchDetails(Model model){
		
		TResultForm resultForm = new TResultForm();
		List<TResultForm> resultFormList = new ArrayList<TResultForm>();
		
		String courseDetails = new String();
		List<String> courseDetailsList_courseNo = new ArrayList<String>();
		List<String> courseDetailsList_examHeld = new ArrayList<String>();
		List<String> courseDetailsList_batchNo = new ArrayList<String>();
				
		try{
			
			courseDetailsList_courseNo = adminService.getcourseDetailsList_courseNo();
			courseDetailsList_examHeld = adminService.getcourseDetailsList_examHeld();
			courseDetailsList_batchNo = adminService.getcourseDetailsList_batchNo();
			
			resultFormList = adminService.getresultListFromQuery(courseNoGlobal,examHeldGlobal,batchNoGlobal);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		model.addAttribute("resultForm",resultForm);
		model.addAttribute("resultFormList", resultFormList);
		
		model.addAttribute("courseDetails",courseDetails);
		model.addAttribute("courseDetailsList_courseNo", courseDetailsList_courseNo);
		model.addAttribute("courseDetailsList_examHeld", courseDetailsList_examHeld);
		model.addAttribute("courseDetailsList_batchNo", courseDetailsList_batchNo);
		
		return "StudentEntry";
	}
	//For 'Add More' option, InputForm page save
	@RequestMapping(value="/studentEntry", method=RequestMethod.POST)
	public String saveBatchDetails(@ModelAttribute("resultForm") TResultForm resultForm, @RequestParam("courseNo") String courseNo, @RequestParam("examHeld") String examHeld, @RequestParam("batchNo") String batchNo, HttpServletRequest req){
		
		courseNoGlobal = courseNo;
		examHeldGlobal = examHeld;
		batchNoGlobal = req.getParameter("batchNo");//batchNo;
		
		try{
			adminService.deletePreviousROW(editRegNoGlobal, courseNo, batchNo);
			adminService.insertBatch(resultForm);
			
		}catch(Exception e) {
			
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return "redirect:/main/studentEntry";
	}
	//For course search page
	@RequestMapping(value="/studentViewPage", method=RequestMethod.GET)
	public String CourseViewPage(Model model){
				
		TResultForm resultForm = new TResultForm();
		TCourseDetails courseDetails = new TCourseDetails();
		List<TCourseDetails> courseDetailsList = new ArrayList<TCourseDetails>();
				
		try{
			courseDetailsList = adminService.getCourseDetailsList();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		model.addAttribute("resultForm",resultForm);
		model.addAttribute("courseDetails",courseDetails);
		model.addAttribute("courseDetailsList", courseDetailsList);
				
		return "SearchStudent";
	}
	//For course view page
	@RequestMapping(value="/viewStudents", method=RequestMethod.GET)
	public String ViewCourses(Model model,@RequestParam("courseNo") String courseNo, @RequestParam("batchNo") String batchNo){
		
		TResultForm resultForm = new TResultForm();
		List<TResultForm> resultFormList = new ArrayList<TResultForm>();
		
		System.out.println("courseNo: "+courseNo);
		System.out.println("batchNo: "+batchNo);
		try{
			resultFormList = adminService.getresultListQuery(courseNo, batchNo);
			System.out.println("resultFormList: "+resultFormList);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		model.addAttribute("resultForm",resultForm);
		model.addAttribute("resultFormList", resultFormList);
			
		return "StudentView";
	}
	// For edit   
	@RequestMapping(value="/editSingleStudent/{regNo}/{courseNo}/{batchNo}")
	public ModelAndView EditUser(@PathVariable("regNo") String regNo, @PathVariable("courseNo") String courseNo, @PathVariable("batchNo")  String batchNo) {
		
		ModelAndView model = new ModelAndView("StudentEntry");
		TResultForm resultForm = new TResultForm();
		List<TResultForm> resultFormList = new ArrayList<TResultForm>();
		
		TCourseDetails courseDetails = new TCourseDetails();
		List<TCourseDetails> courseDetailsList = new ArrayList<TCourseDetails>();
		
		editRegNoGlobal = regNo;
		System.out.println("editRegNoGlobal: "+editRegNoGlobal);
		
		try{
			courseDetailsList = adminService.getCourseDetailsList();
			resultFormList = adminService.getresultListFor3Query(regNo, courseNo, batchNo);
			System.out.println("resultFormList: "+resultFormList);
		}catch(Exception e){
			
			e.printStackTrace();
		}
		Iterator it = resultFormList.iterator();
		while(it.hasNext()){
			resultForm = (TResultForm) it.next();
		}
		
		model.addObject("resultForm",resultForm);
		model.addObject("resultFormList", resultFormList);
		model.addObject("courseDetails",courseDetails);
		model.addObject("courseDetailsList", courseDetailsList);
		
		return model;
	}
	// For delete 
	@RequestMapping(value="/deleteSingleStudent/{regNo}/{courseNo}/{batchNo}")
	public ModelAndView DeleteSingleCourse(@PathVariable("regNo") String regNo, @PathVariable("courseNo") String courseNo, @PathVariable("batchNo") String batchNo) {
		
		ModelAndView model = new ModelAndView("redirect:/main/studentEntry");
		try{
			adminService.deleteSingleStudent(regNo, courseNo, batchNo);
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return model;
	}
}
