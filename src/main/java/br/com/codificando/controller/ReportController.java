package br.com.codificando.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.codificando.model.Report;
import br.com.codificando.repository.ReportRepository;

@RestController
public class ReportController {
	
	@Autowired
	private ReportRepository reportReposytory;
	
	 
	 @PostMapping("/report")
	 private  void report(@RequestBody Report report) {
	     reportReposytory.save(report);
	  }
	 
	 @GetMapping("/report/list")
	 private  List<Report> allReports() {
	     return reportReposytory.findAll();
	  }
	 
	 @GetMapping("/report/{id}")
	 private Optional<Report> report(@PathVariable long id) {
	     return reportReposytory.findById(id);
	 }
	 
	 @GetMapping("/report/list/{assunto}")
	 private  List<Report> reportAssunto(@PathVariable String assunto) {
	     return reportReposytory.findByAssunto(assunto);
	 }

	 @DeleteMapping("/report/delete/{id}")
	 private  void deleteReport(@PathVariable long id) {
	     reportReposytory.deleteById(id);
	 }
}
