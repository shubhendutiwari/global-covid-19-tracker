package com.covid19.globalcovid19tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.covid19.globalcovid19tracker.models.LocationStats;
import com.covid19.globalcovid19tracker.service.Covid_19_DataService;

@Controller
public class HomeController {

	@Autowired
	private Covid_19_DataService covid_19_DataService; 
	
	@GetMapping("/")
	public String home(Model model) {

		
		List<LocationStats> allStats = covid_19_DataService.getAllStats();
		int totalCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
		int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPreviousDay()).sum();
		model.addAttribute("totalReportedCases",totalCases );
		model.addAttribute("locationStats",allStats );
		model.addAttribute("totalNewCases",totalNewCases);
		return "home";
	}
}
