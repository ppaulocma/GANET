package br.com.codificando.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import br.com.codificando.UploadFotos;
import br.com.codificando.model.Usuario;
import br.com.codificando.repository.UsuarioRepository;

@Controller
public class EditarController {
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@GetMapping("/perfil/editar")
	public ModelAndView editUsuario() {
		ModelAndView mv = new ModelAndView("editar");
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario  = usuarioRepository.findByLogin(login);
		mv.addObject("usuario", usuario);	
		return mv;
	}
	
	@PostMapping("perfil/editar/foto")
	public String editFoto(@RequestParam("image") MultipartFile multipartFile) throws IOException {
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		try {
	         String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	         Usuario savedUser = usuarioRepository.findByLogin(login);
	         savedUser.setFoto_perfil(fileName);
	         String uploadDir = "usuario-fotos/foto/" + savedUser.getId();          
	         UploadFotos.saveFile(uploadDir, fileName, multipartFile);
	 		usuarioRepository.save(savedUser);
	      } catch (Exception e) {
	         System.out.println("Erro ao salvar: " + e.getMessage());
	      }
		return "redirect:/perfil/"+login;
	}
	
	@PostMapping("perfil/editar/capa")
	public String editCapa(@RequestParam("image") MultipartFile multipartFile) throws IOException {
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		
		try {
	         String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	         Usuario savedUser = usuarioRepository.findByLogin(login);
	         savedUser.setFoto_capa(fileName);
	         String uploadDir = "usuario-fotos/capa/" + savedUser.getId();         
	         UploadFotos.saveFile(uploadDir, fileName, multipartFile);
	 		usuarioRepository.save(savedUser);
	      } catch (Exception e) {
	         System.out.println("Erro ao salvar: " + e.getMessage());
	      }
		return "redirect:/perfil/"+login;
	}
	
	@ResponseBody
	@PostMapping("/perfil/editar/senha")
	public Boolean editSenha(@RequestParam("senhaNova") String senhaNova, @RequestParam("senhaAntiga") String senhaAntiga){
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuarioLogado  = usuarioRepository.findByLogin(login);
		String senha = usuarioLogado.getSenha();
	     boolean igual = new BCryptPasswordEncoder().matches(senhaAntiga, senha);
	     if (igual) {
		 	usuarioLogado.setSenha(new BCryptPasswordEncoder().encode(senhaNova));
		 	usuarioRepository.save(usuarioLogado);
				return true;
	     }
	     return false;
	}
	
	@PostMapping("perfil/editar/save")
	public String saveUsuario(Usuario usuario) {
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuarioLogado = usuarioRepository.findByLogin(login);
		usuarioLogado.setLogin(usuario.getLogin());
		usuarioLogado.setNome(usuario.getNome());
		usuarioLogado.setDtNascimento(usuario.getDtNascimento());
		usuarioRepository.save(usuarioLogado);
		return "redirect:/perfil/"+login;
	}
}