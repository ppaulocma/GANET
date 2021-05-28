package br.com.codificando.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import br.com.codificando.UploadFotos;
import br.com.codificando.model.Usuario;
import br.com.codificando.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public Usuario usuarioLogado() {
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		return usuarioRepository.findByLogin(login);
	}
	
	public String salvar(Usuario usuario) throws IOException{
		 try {
			 if(usuario != null) {
		          String senha = usuario.getSenha();
		          usuario.setSenha(new BCryptPasswordEncoder().encode(senha));
		          usuarioRepository.save(usuario);
		          usuario = usuarioRepository.findByLogin(usuario.getLogin());
		          usuarioRepository.insetRole(usuario.getId());
		     }
			 
	      }catch (Exception e) {
	            System.out.println("Erro ao salvar: " + e.getMessage());
	            return "redirect:/login-error";
	      }
	      return "redirect:/login";
	}
	
	public void editFoto(MultipartFile multipartFile) throws IOException {
		try {
	         String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	         Usuario usuario = usuarioLogado();
	         usuario.setFoto_perfil(fileName);
	         if(fileName != null || fileName != "") {
	         String uploadDir = "usuario-fotos/foto/" + usuario.getId();          
	         UploadFotos.saveFile(uploadDir, fileName, multipartFile);
	         }
	 		 usuarioRepository.save(usuario);
	      }catch (Exception e) {
	         System.out.println("Erro ao salvar: " + e.getMessage());
	      }
	}
	
	public void editCapa(MultipartFile multipartFile) throws IOException {
		try {
	         String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	         Usuario usuario = usuarioLogado();
	         usuario.setFoto_capa(fileName);
	         if(fileName != null || fileName != "") {
	         String uploadDir = "usuario-fotos/capa/" +usuario.getId();         
	         UploadFotos.saveFile(uploadDir, fileName, multipartFile);
	         }
	 		 usuarioRepository.save(usuario);
	      } catch (Exception e) {
	         System.out.println("Erro ao salvar: " + e.getMessage());
	      }
	}
	
	public Boolean editSenha(String senhaNova, String senhaAntiga){
		Usuario usuarioLogado  = usuarioLogado();
		String senha = usuarioLogado.getSenha();
	     boolean igual = new BCryptPasswordEncoder().matches(senhaAntiga, senha);
	     if (igual) {
		 	usuarioLogado.setSenha(new BCryptPasswordEncoder().encode(senhaNova));
		 	usuarioRepository.save(usuarioLogado);
			return true;
	     }
	     return false;
	}
	
	public String saveUsuario(Usuario usuario) {
		Usuario usuarioLogado = usuarioLogado();
		usuarioLogado.setDescricao(usuario.getDescricao());
		usuarioLogado.setLogin(usuario.getLogin());
		usuarioLogado.setNome(usuario.getNome());
		usuarioLogado.setDtNascimento(usuario.getDtNascimento());
		usuarioRepository.save(usuarioLogado);
		return "redirect:/perfil/"+usuarioLogado.getLogin();
	}
}
