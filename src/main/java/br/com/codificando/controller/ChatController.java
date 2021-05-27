package br.com.codificando.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.com.codificando.model.Mensagem;
import br.com.codificando.model.Usuario;
import br.com.codificando.repository.MensagemRepository;
import br.com.codificando.repository.UsuarioRepository;
import br.com.codificando.service.ChatService;
import br.com.codificando.service.UsuarioService;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
public class ChatController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ChatService chatService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
    MensagemRepository mensagemRepository;
	
	@Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, Mensagem message) {
        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
    }
    
    @RequestMapping(value = "/mensagem", method = RequestMethod.POST)
    public void saveMensagem(@RequestBody Mensagem mensagem){
    	chatService.salvarMensagem(mensagem);
    }

    @GetMapping("/registration")
    public String getLogin() {
        return usuarioService.usuarioLogado().getLogin();
    }
    
    @GetMapping("/todos")
    public List<Usuario> todos() {
    	return usuarioRepository.findAll();
    }
    
    @GetMapping("/getMensagens/{de}/{para}")
    public List <Mensagem> getMensagens(@PathVariable("de") String de, @PathVariable("para") String para ){
    	return mensagemRepository.findMensagens(de,para);
    }
    
    @RequestMapping(value = "/setStatus/{de}/{para}", method = RequestMethod.GET)
    public void setStatus(@PathVariable("de") String de, @PathVariable("para") String para){
    	chatService.setStatus(para, de);
    }

    @GetMapping("/getStatus/{de}/{para}")
    public Long getStatus(@PathVariable("de") String de, @PathVariable("para") String para )throws IOException{
    	return chatService.getStatus(de, para);
    }
    
    @GetMapping("/findHistorico")
    public Set<Usuario> findHistorico(){
    	return chatService.getHistorico();
    }
    
}
