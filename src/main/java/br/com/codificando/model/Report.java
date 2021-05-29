package br.com.codificando.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.springframework.lang.NonNull;

@Entity(name="report")
public class Report {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	private String email;
	
	@NonNull
	private String assunto;
	
	@NonNull
	private String texto;
	
	@NonNull
	private String data;
	
	@Lob
    private byte[] imagem;
	
	private boolean checkImagem;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public boolean isCheckImagem() {
		return checkImagem;
	}

	public void setCheckImagem(boolean checkImagem) {
		this.checkImagem = checkImagem;
	}

	@Transient
    public String getImgReport() {
        if (checkImagem == true) return "/report/imagem/"+id;
        return null;
    }

	@Override
	public String toString() {
		return "Report [id=" + id + ", email=" + email + ", assunto=" + assunto + ", texto=" + texto + "]";
	}
	

}
