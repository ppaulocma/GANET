package br.com.codificando.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import br.com.codificando.repository.ReportRepository;
import br.com.codificando.service.ReportService;

@Controller
public class AdminController {
	
	class assunto{
		private String assunto;
		public String getAssunto() {
			return assunto;
		}
		public void setAssunto(String assunto) {
			this.assunto = assunto;
		}
		public assunto(String assunto) {
			super();
			this.assunto = assunto;
		}
	}
	
	@Autowired
	ReportRepository reportReposytory;
	
	@Autowired
	ReportService reportService;
	
	
	@GetMapping("/admin")
	public ModelAndView admin(assunto assunto) {
		ModelAndView mv = new ModelAndView("admin");
		mv.addObject("reports", reportService.listarPorAssunto(assunto.getAssunto()));
		return mv;
	}
	
	
	
}

