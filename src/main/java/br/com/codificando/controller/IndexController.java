package br.com.codificando.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.codificando.model.Report;
import br.com.codificando.model.Usuario;
import br.com.codificando.repository.ReportRepository;
import br.com.codificando.repository.UsuarioRepository;
import br.com.codificando.service.ReportService;
import br.com.codificando.service.UsuarioService;

@Controller
public class IndexController {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ReportRepository reportReposytory;
	
	@Autowired
	 ReportService reportService;
	
	@GetMapping("/")
	public String getIndex(){
		return "index";
	}
	
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
	         reportService.reportar(report, multipartFile);
	     }catch (Exception e) {
	         System.out.println("Erro ao salvar: " + e.getMessage());
	     }
		 return "redirect:/";
	}
	
	@GetMapping("/sobre")
	public String getSobre(){
		return "sobre";
	}	
	
	@GetMapping("/chat")
	public String getChat(Model model){
		Usuario usuario = usuarioService.usuarioLogado();
		model.addAttribute("usuario", usuario);
		return "chat";
	}

}
