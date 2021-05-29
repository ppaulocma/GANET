package br.com.codificando.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
		          usuario.setFotoPadrao(true);
		          usuario.setCapaPadrao(true);
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
	
	public void editFoto(long id, MultipartFile multipartFile){
		Usuario usuarioLogado  = usuarioLogado();
		try {
			if(multipartFile.getOriginalFilename() != "") {
				System.out.println(multipartFile.getOriginalFilename());
				usuarioLogado.setFoto_perfil(multipartFile.getBytes());
				usuarioLogado.setFotoPadrao(false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		usuarioRepository.save(usuarioLogado);
	}
	
	public void editCapa(long id , MultipartFile multipartFile){
		Usuario usuarioLogado  = usuarioLogado();
		try {
			if(multipartFile.getOriginalFilename() != "") {
				System.out.println(multipartFile.getOriginalFilename());
				usuarioLogado.setFoto_capa(multipartFile.getBytes());
				usuarioLogado.setCapaPadrao(false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		usuarioRepository.save(usuarioLogado);
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
	
	public void getFoto(long id, HttpServletResponse response)throws IOException {
		Usuario usuario = usuarioRepository.findById(id);
		response.setContentType("image/jpeg");
		InputStream is = new ByteArrayInputStream(usuario.getFoto_perfil());
		IOUtils.copy(is, response.getOutputStream());
	}
	
	public void getCapa(long id, HttpServletResponse response)throws IOException {
		Usuario usuario = usuarioRepository.findById(id);
		response.setContentType("image/jpeg");
		InputStream is = new ByteArrayInputStream(usuario.getFoto_capa());
		IOUtils.copy(is, response.getOutputStream());
	}
}
