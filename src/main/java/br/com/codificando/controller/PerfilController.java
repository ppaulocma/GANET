package br.com.codificando.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import br.com.codificando.model.Usuario;
import br.com.codificando.repository.UsuarioRepository;
import br.com.codificando.service.PerfilService;
import br.com.codificando.service.PostService;
import br.com.codificando.service.UsuarioService;

@Controller
public class PerfilController {
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	PostService postService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PerfilService perfilService;
	
	@RequestMapping(value="/perfil/{login}", method=RequestMethod.GET)
    public ModelAndView perfil(@PathVariable("login") String login){
		long id = usuarioRepository.findByLogin(login).getId();
		Usuario usuario = usuarioRepository.findById(id);
		ModelAndView mv = new ModelAndView("perfil");
		mv.addObject("posts", postService.listPostsUsuario(login));
		mv.addObject("usuario_logado", usuarioService.usuarioLogado());
		mv.addObject("usuario", usuario);
		mv.addObject("checkPerfil" , perfilService.checkPerfil(id));
		mv.addObject("checkSeguidor" , perfilService.checkSeguidor(id));
		mv.addObject("seguindoCont", usuario.getAmigos().size());
		mv.addObject("seguidoresCont", usuarioRepository.getSeguidores(id).size());
		mv.addObject("seguidores", usuarioRepository.getSeguidores(id));
		mv.addObject("seguindo", usuario.getAmigos());
		return mv;
    }
	

	@RequestMapping(value = "/perfil/save/{id}", method = RequestMethod.GET)
	public String seguir (@PathVariable("id") long id) {
		return perfilService.seguir(id);
	}
	
	
	@RequestMapping(value = "/perfil/remove/{id}", method = RequestMethod.GET)
	public String removeSeguindoPerfil (@PathVariable("id") long id) {
		perfilService.deixarDeSeguir(id);
		return "redirect:/perfil/"+usuarioRepository.findById(id).getLogin();
	}
	
	@RequestMapping(value = "/seguidores/remove/{id}", method = RequestMethod.GET)
	public String removeSeguidor (@PathVariable("id") long id) {
		perfilService.removeSeguidor(id);
		return "redirect:/perfil/"+usuarioService.usuarioLogado().getLogin();
	}
	
	@RequestMapping(value = "/seguindo/remove/{id}", method = RequestMethod.GET)
	public String removeSeguindo (@PathVariable("id") long id) {
		perfilService.deixarDeSeguir(id);
		return "redirect:/perfil/"+usuarioService.usuarioLogado().getLogin();
	}
	
}
