package br.com.codificando;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import br.com.codificando.repository.PermissaoRepository;

@SpringBootApplication
public class Application implements ApplicationRunner{
	@Autowired
	PermissaoRepository permissaoRepository;
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}



	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (permissaoRepository.findAll().isEmpty()) {
			permissaoRepository.insetPermissao1();
			permissaoRepository.insetPermissao2();
		}
	}
}
