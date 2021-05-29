package br.com.codificando.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.lang.NonNull;

@Entity 
@Table (name="POST")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Lob
    private byte[] imagem;
    
    private boolean checkImagem;

    @NonNull
    private String data;
    
    @NonNull
    @Lob
    private String texto;
    
    @ManyToOne(cascade=CascadeType.PERSIST)
    private Usuario usuario;
    
    
    @OneToMany(mappedBy = "post")
    private List<Comentario> comentarios;
    
    @OneToMany(mappedBy = "post")
    private List<Curtida> curtidas;
    
    
    public int getQtdCurtidas() {
    	return curtidas.size();
    }
    
	public List<Curtida> getCurtidas() {
		return curtidas;
	}
    
    public void setCurtida(Curtida curtida) {
		this.curtidas.add(curtida);
	}

	public void setCurtidas(List<Curtida> curtidas) {
		this.curtidas = curtidas;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}
    
    public void setComentario(Comentario comentario) {
		this.comentarios.add(comentario);
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Usuario getUsuario() {
        return usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
	
	public boolean isCheckImagem() {
		return checkImagem;
	}

	public void setCheckImagem(boolean checkImagem) {
		this.checkImagem = checkImagem;
	}
	
	@Transient
    public String getImgPost() {
        if (checkImagem == true) return "/getImagem/"+id;
        return null;
    }

	@Override
    public String toString() {
        return "Post [id=" + id + "data=" + data + ", texto=" + texto
                + ", usuarios=" + usuario.getId() +"]";
    }


}