package br.com.codificando.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.codificando.model.Mensagem;
import br.com.codificando.model.Usuario;
import br.com.codificando.repository.MensagemRepository;
import br.com.codificando.repository.UsuarioRepository;

@Service
public class ChatService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
    MensagemRepository mensagemRepository;
	
	
	public void setStatus(String para, String de) {
		long lido = 1;
    	List<Mensagem> mensagens = mensagemRepository.getMsgByStatus(para , de , 0);
    	for (int i = 0; i < mensagens.size(); i++) {
    		Mensagem mensagem = mensagens.get(i);
    		mensagem.setStatus(lido);
    		mensagemRepository.save(mensagem);
		}
	}
	
	public Long getStatus(String de, String para) {
		long cont = 0;
    	List<Long>mensagens = mensagemRepository.getStatus(de , para , 0);
        for (int i = 0; mensagens.size() > i ; i++) {
        	cont++;
    	}
        return cont;
	}
	
	public Set<Usuario> getHistorico() {
		String login = usuarioService.usuarioLogado().getLogin();
    	List<Mensagem> mensagens = mensagemRepository.findHistorico(login);
    	Set<Usuario> usuarios = new HashSet<Usuario>();
    	for (int i = 0; mensagens.size() > i; i++) {
			Mensagem mensagem = mensagens.get(i);
			if(mensagem.getFromLogin().equals(login)){
				usuarios.add(usuarioRepository.findByLogin(mensagem.getTo()));
			}else{
				usuarios.add(usuarioRepository.findByLogin(mensagem.getFromLogin()));
			}
		}
    	return usuarios;
	}
	
	public void salvarMensagem(Mensagem mensagem) {
		long status = 0;
    	Usuario de = usuarioService.usuarioLogado();
    	Usuario para = usuarioRepository.findByLogin(mensagem.getTo());
    	mensagem.setDeLogin(de);
    	mensagem.setStatus(status);
    	mensagem.setParaLogin(para);
    	String message = mensagem.getMessage();
    	if(message != "" || message != null) {
    		de.setMensagen(mensagem);
    		mensagemRepository.save(mensagem);
    	}
	}
}
