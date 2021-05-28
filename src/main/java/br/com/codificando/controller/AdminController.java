package br.com.codificando.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.codificando.model.Report;
import br.com.codificando.repository.ReportRepository;
import br.com.codificando.service.ReportService;

@Controller
public class AdminController {
	
	@Autowired
	ReportRepository reportReposytory;
	
	@Autowired
	ReportService reportService;
	
	
	@GetMapping("/admin")
	public ModelAndView admin(Report report) {
		ModelAndView mv = new ModelAndView("admin");
		mv.addObject("reports", reportService.listarPorAssunto(report.getAssunto()));
		return mv;
	}
}

