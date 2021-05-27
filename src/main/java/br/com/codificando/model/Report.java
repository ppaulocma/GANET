package br.com.codificando.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@NonNull
    private String img;
	

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
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
	
	@Transient
    public String getImgReport() {
        if (img == null || id == null) return null;
        return "/usuario-fotos/report/"+ id + "/" + img;
    }

	@Override
	public String toString() {
		return "Report [id=" + id + ", email=" + email + ", assunto=" + assunto + ", texto=" + texto + "]";
	}
	

}
