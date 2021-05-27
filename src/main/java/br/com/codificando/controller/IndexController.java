package br.com.codificando.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.codificando.model.Usuario;
import br.com.codificando.repository.UsuarioRepository;
import br.com.codificando.service.UsuarioService;

@Controller
public class IndexController {
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("/")
		public String getIndex(){
			return "index";
	}
	
	@GetMapping("/chat")
	public String getChat(Model model){
		Usuario usuario = usuarioService.usuarioLogado();
		model.addAttribute("usuario", usuario);
		return "chat";
	}

}
