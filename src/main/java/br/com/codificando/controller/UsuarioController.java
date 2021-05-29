package br.com.codificando.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import br.com.codificando.model.Usuario;
import br.com.codificando.repository.UsuarioRepository;
import br.com.codificando.service.UsuarioService;

@Controller
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("login"); 
		return mv;
	}
	
	@GetMapping("/cadastro")
	public ModelAndView cadastro() {
		ModelAndView mv = new ModelAndView("cadastro"); 
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	
	@GetMapping("/login-error")
		public String getLoginError (Model model) {
		 model.addAttribute("loginError", true);
		return "login-error";
		
	}
	
	
	@PostMapping("/cadastrar")
    public String saveUsuario(Usuario usuario) throws IOException{
       return usuarioService.salvar(usuario);
	}
	
	@GetMapping("/perfil/foto/{id}")
	public void getFoto(@PathVariable long id, HttpServletResponse response) throws IOException {
		usuarioService.getFoto(id, response);
	}
	
	@GetMapping("/perfil/capa/{id}")
	public void getCapa(@PathVariable long id, HttpServletResponse response) throws IOException {
		usuarioService.getCapa(id, response);
	}
	
}
	

