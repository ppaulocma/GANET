package br.com.codificando.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import br.com.codificando.model.Usuario;
import br.com.codificando.repository.UsuarioRepository;
import br.com.codificando.service.UsuarioService;

@Controller
public class EditarController {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("/perfil/editar")
	public ModelAndView editUsuario() {
		ModelAndView mv = new ModelAndView("editar");
		Usuario usuario  = usuarioService.usuarioLogado();
		mv.addObject("usuario", usuario);	
		return mv;
	}
	
	@PostMapping("perfil/editar/foto")
	public String editFoto(@RequestParam("image") MultipartFile multipartFile) throws IOException {
		Usuario usuario  = usuarioService.usuarioLogado();
		usuarioService.editFoto(multipartFile);
		return "redirect:/perfil/"+usuario.getLogin();
	}
	
	@PostMapping("perfil/editar/capa")
	public String editCapa(@RequestParam("image") MultipartFile multipartFile) throws IOException {
		Usuario usuario  = usuarioService.usuarioLogado();
		usuarioService.editCapa(multipartFile);
		return "redirect:/perfil/"+usuario.getLogin();
	}
	
	@ResponseBody
	@PostMapping("/perfil/editar/senha")
	public Boolean editSenha(@RequestParam("senhaNova") String senhaNova, @RequestParam("senhaAntiga") String senhaAntiga){
		return usuarioService.editSenha(senhaNova, senhaAntiga);
	}
	
	@PostMapping("perfil/editar/save")
	public String saveUsuario(Usuario usuario) {
		usuarioService.saveUsuario(usuario);
		return "redirect:/perfil/"+usuario.getLogin();
	}
}