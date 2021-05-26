package br.com.codificando.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import br.com.codificando.UploadFotos;
import br.com.codificando.model.Report;
import br.com.codificando.repository.ReportRepository;

@Controller
public class ReportController {
	
	@Autowired
	private ReportRepository reportReposytory;
	
	 @GetMapping("/suporte")
	 private ModelAndView suporte() {
		ModelAndView mv = new ModelAndView("suporte");
		Report report = new Report();
		mv.addObject(report);
	    return mv;
	 }
	 
	 @PostMapping("/report")
	 private String report(Report report, @RequestParam("image") MultipartFile multipartFile) {
		 try {
	         String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	         report.setImg(fileName);
	         reportReposytory.save(report);
	         if(fileName != null || fileName != "") {
	        	 String uploadDir = "usuario-fotos/report/" + report.getId();          
	        	 UploadFotos.saveFile(uploadDir, fileName, multipartFile);
	         }
	         reportReposytory.save(report);
	      } catch (Exception e) {
	         System.out.println("Erro ao salvar: " + e.getMessage());
	      }
		 return "redirect:/";
	  }
	 
	 @GetMapping("/report/list")
	 private List<Report> allReports() {
	     return reportReposytory.findAll();
	  }
	 
	 @GetMapping("/report/{id}")
	 private Optional<Report> report(@PathVariable long id) {
	     return reportReposytory.findById(id);
	 }
	 
	 @GetMapping("/report/list/{assunto}")
	 private List<Report> reportAssunto(@PathVariable String assunto) {
	     return reportReposytory.findByAssunto(assunto);
	 }

	 @DeleteMapping("/report/delete/{id}")
	 private void deleteReport(@PathVariable long id) {
	     reportReposytory.deleteById(id);
	 }
}
