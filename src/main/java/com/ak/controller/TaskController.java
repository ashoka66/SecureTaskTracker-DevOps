package com.ak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ak.service.TaskService;

@Controller
public class TaskController {
	
	
	@Autowired
	private  TaskService taskService;
	
	
	//Loads login page
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	//loads dashboard after successfull login
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		model.addAttribute("tasks",taskService.getAllTasks());
		return "dashboard"; //return dashboard
		
	}
	
	//add a new task
	@PostMapping("/addTask")
	public String addTask(@RequestParam String task) {
		taskService.addTask(task);
		return  "redirect:/dashboard"; //refresh page
	}
	
	//delete taks by id
	@PostMapping("/deleteTask/{id}")
	public String deleteTask(@PathVariable int id) {
		taskService.deleteTask(id);
		return "redirect:/dashboard";
	}
	
	

}
