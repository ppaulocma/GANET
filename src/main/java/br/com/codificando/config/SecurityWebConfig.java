package br.com.codificando.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.codificando.security.CodificandoDetailsService;

@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CodificandoDetailsService codificandoDetailsService;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable()
		
		//Hablitar ou desabilitar paginas
		.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/cadastro").permitAll()
		.antMatchers("/cadastrar").permitAll()
		.antMatchers("/login-error").permitAll()
		.antMatchers("/suporte").permitAll()
		//Habilitar statics
		.antMatchers("/bootstrap-4.5.2/**").permitAll()
		.antMatchers("/css/**").permitAll()
		.antMatchers("/fontawesome-5.14.0/**").permitAll()
		.antMatchers("/js/**").permitAll()
		.antMatchers("/img/**").permitAll()
		.antMatchers("/usuario-fotos/**").permitAll()
		.antMatchers("/usuario-fotos/**/**").permitAll()
		
		//Outras autenticacpes
		.antMatchers(HttpMethod.POST, "/report").permitAll()
		.antMatchers("/admin").hasAnyRole("ADMIN")
		.antMatchers("/admin/**").hasAnyRole("ADMIN")
		.anyRequest().hasAnyRole("USER")
		.and()
		//Definir página de login
		.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/time-line", true)
			.permitAll()
		.and()
		.logout()
        .logoutSuccessUrl("/");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder builder)throws Exception{
		builder
			.userDetailsService(codificandoDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	public static void main(String[]args) {
		System.out.println(new BCryptPasswordEncoder().encode("123456"));
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
