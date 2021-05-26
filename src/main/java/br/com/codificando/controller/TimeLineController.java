package br.com.codificando.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import br.com.codificando.model.Comentario;
import br.com.codificando.model.Post;
import br.com.codificando.repository.UsuarioRepository;
import br.com.codificando.service.ComentarioService;
import br.com.codificando.service.CurtidasService;
import br.com.codificando.service.PostService;
import br.com.codificando.service.UsuarioService;
import java.io.IOException;
import java.util.List;

@Controller
public class TimeLineController {

	@Autowired
	PostService postService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	CurtidasService curtidasService;
	
	@Autowired
	ComentarioService comentarioService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	
	@RequestMapping(value = "/time-line", method = RequestMethod.GET)
	public ModelAndView listPosts() {
		ModelAndView mv = new ModelAndView("time-line");
		List<Post> posts = postService.list();
		mv.addObject("usuario", usuarioService.usuarioLogado());
		mv.addObject("posts", posts);
		return mv;
	}
	
	@PostMapping("/comentar/{id}")
	public String comentar(Comentario comentario, @PathVariable long id) {
		comentarioService.comentar(id, comentario);
		return "redirect:/time-line";
	}
	
	@PostMapping("/curtir/{id}")
	public String curtir(@PathVariable long id) {
		curtidasService.curtir(id);
		return "redirect:/time-line";
		
	}
	
	@GetMapping("/checkCurtida/{id}")
	public boolean checkCurtida(@PathVariable long id) {
		return curtidasService.checkCurtida(id);
	}

	@RequestMapping(value = "/postar", method = RequestMethod.POST)
	public String savePost(Post post, @RequestParam("image") MultipartFile multipartFile) throws IOException{
		postService.save(post, multipartFile);
		return "redirect:/time-line";
	}
	
}