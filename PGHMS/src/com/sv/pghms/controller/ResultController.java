package com.sv.pghms.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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
public class ResultController {
	
	@Autowired
	private AdminService adminService;
	String courseNoGlobal,examHeldGlobal,batchNoGlobal;
	String regNoFromPathVariable,courseNoFromPathVariable, examHeldFromPathVariable, batchNoFromPathVariable;
	
	//For showing ShowResultPage.html
	@RequestMapping(value="/showResultWithParam", method=RequestMethod.GET)
	public String ShowResultWithParam(Model model,@RequestParam("courseNo") String courseNo, @RequestParam("examHeld") String examHeld, @RequestParam("batchNo") String batchNo){
		
		TCourseDetails courseFromQuery = new TCourseDetails();
		List<TCourseDetails> courseListFromQuery = new ArrayList<TCourseDetails>();
		
		TResultForm resultForm = new TResultForm();
		List<TResultForm> resultFormList = new ArrayList<TResultForm>();
		
		try{
			courseListFromQuery = adminService.getCourseListFormQuery(courseNo, examHeld,batchNo);
			System.out.println("courseListFromQuery: "+courseListFromQuery);
			resultFormList = adminService.getresultListFromQuery(courseNo, examHeld,batchNo);
			System.out.println("resultFormList: "+resultFormList);
			
		}catch(Exception e){
			e.printStackTrace();
		}

		model.addAttribute("courseFromQuery",courseFromQuery);
		model.addAttribute("courseListFromQuery", courseListFromQuery);
		model.addAttribute("resultForm",resultForm);
		model.addAttribute("resultFormList", resultFormList);
			
		return "ShowResultPage";
	}
	@RequestMapping(value="/showResultWithGlobalParam", method=RequestMethod.GET)
	public String ShowResult(Model model){
		
		TResultForm resultForm = new TResultForm();
		List<TResultForm> resultFormList = new ArrayList<TResultForm>();
		
		try{
			//resultFormList = adminService.getresultFormList();
			resultFormList = adminService.getresultListFromQuery(courseNoGlobal,examHeldGlobal,batchNoGlobal);
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("resultForm",resultForm);
		model.addAttribute("resultFormList", resultFormList);
			
		return "ShowResultPage";
	}
	//For edit 
	@RequestMapping(value="/editSingleResultShowpage/{regNo}/{courseNo}/{examHeld}/{batchNo}")
	public String EditSingleResultShowpage(Model model, @PathVariable("regNo") String regNo, @PathVariable("batchNo") String batchNo,
			@PathVariable("courseNo") String courseNo, @PathVariable("examHeld") String examHeld) { 
		
		TResultForm resultForm = new TResultForm();
		List<TResultForm> resultList = new ArrayList<TResultForm>();
		
		//Path variables declared globally to send "/saveEditedResult" mapped method for deletePreviousRow(...) method.
		regNoFromPathVariable = regNo;
		courseNoFromPathVariable = courseNo;
		examHeldFromPathVariable = examHeld;
		batchNoFromPathVariable = batchNo;
		
		try{
			System.out.println("Before");
			resultList = adminService.getSingleResultForm(regNo, courseNo, examHeld, batchNo);
			Iterator it = resultList.iterator();
			while(it.hasNext()){
				resultForm = (TResultForm) it.next();
			}
			//System.out.println("After: ");
			//System.out.println("Reg. no.: "+resultForm.getRegNo());
		}catch(Exception e){
			
			e.printStackTrace();
		}
		model.addAttribute("resultForm",resultForm);
		
		return "EditResult";
	}  
	//For save after editing 
	@RequestMapping(value="/saveEditedResult", method=RequestMethod.POST)
	public String EditedResEntry(@ModelAttribute("resultForm") TResultForm resultForm, @RequestParam("courseNo") String courseNo, @RequestParam("examHeld") String examHeld, @RequestParam("batchNo") String batchNo, HttpServletRequest req){ //, 
		
		courseNoGlobal = courseNo;
		examHeldGlobal = examHeld;
		batchNoGlobal  = batchNo;
		//batchNoGlobal = req.getParameter("batchNo");
		System.out.println("FromPathVariable: "+regNoFromPathVariable+" "+courseNoFromPathVariable+" "+examHeldFromPathVariable);
		try{
			System.out.println("Deleting...");
			adminService.deletePreviousRow(regNoFromPathVariable,courseNoFromPathVariable, examHeldFromPathVariable);
			System.out.println("Deleted.");
			System.out.println("Inserting...");
			adminService.insertResult(resultForm); //This insert new row. So previous row remains in db. So at first we will delete prev. row. Then save new row.
			System.out.println("Inserted.");
		}catch(Exception e) {
			
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return "redirect:/main/showResultWithGlobalParam";
	}
	//For delete
	/*@RequestMapping(value="/deleteSingleResultShowpage/{id}")
	public ModelAndView deleteSingleResultShowpager(@PathVariable("id") String id) {
		
		ModelAndView model = new ModelAndView("redirect:/main/showResult");
		try{
			adminService.deleteSingleResult(id);
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return model;
	}*/
	//For 'Add More' option, InputForm page show
	/*@RequestMapping(value="/addSingleResult", method=RequestMethod.GET)
	public String SubResEntry(Model model){
		
		TResultForm resultForm = new TResultForm();
		List<TResultForm> resultFormList = new ArrayList<TResultForm>();
		
		try{
			resultFormList = adminService.getresultFormList();
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("resultForm",resultForm);
		model.addAttribute("resultFormList", resultFormList);
			
		return "InputForm";
	}
	//For 'Add More' option, InputForm page save
	@RequestMapping(value="/addSingleResult", method=RequestMethod.POST)
	public String SubResEntry(@ModelAttribute("resultForm") TResultForm resultForm){
		
		try{
			
			adminService.insertResult(resultForm);
			
		}catch(Exception e) {
			
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return "redirect:/main/addSingleResult";
	}
	// For edit of InputForm.html page after 'Add More'  
	@RequestMapping(value="/editSingleResult/{id}")
	public ModelAndView EditUser(@PathVariable("id") String id) {
		
		ModelAndView model = new ModelAndView("InputForm");
		TResultForm resultForm = new TResultForm();
		List<TResultForm> resultFormList = new ArrayList<TResultForm>();
		
		try{
			//resultForm = adminService.getSingleResultForm(id);
			resultFormList = adminService.getresultFormList();
		}catch(Exception e){
			
			e.printStackTrace();
		}
		model.addObject("resultForm",resultForm);
		model.addObject("resultFormList", resultFormList);
		return model;
	}
	// For delete of InputForm.html page after 'Add More'  
	@RequestMapping(value="/deleteSingleResult/{id}")
	public ModelAndView DeleteSingleResult(@PathVariable("id") String id) {
		
		ModelAndView model = new ModelAndView("redirect:/main/addSingleResult");
		try{
			adminService.deleteSingleResult(id);
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return model;
	}
*/
}
