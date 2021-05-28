package br.com.codificando.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="usuario")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"login"}))
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NonNull
	@Size(max=80)
	private String nome;
	
	@Column(nullable = true, length = 64)
    private String foto_perfil;
	
	@Column(nullable = true, length = 64)
    private String foto_capa;
	
	@NonNull
	@Size(max=60)
	private String login;
	
	@NonNull
	private String descricao;
	
	@NonNull
	@Size(max=100)
	private String senha;
	
	@Size(max=10)
	private String dtNascimento;
	
	@NonNull
	private boolean ativo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "usuario")
	private List<Post> posts;
	
	@JsonIgnore
	@OneToMany(mappedBy = "deLogin")
	private List<Mensagem> mensagens;
	
	@JsonIgnore
	@ManyToMany
	private List<Usuario> amigos;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name="usuario_permissao",joinColumns = @JoinColumn
		(name= "usuario_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn
		(name="permissao_id", referencedColumnName = "id"))
	private List<Permissao> permissoes;

	public List<Permissao> getPermissoes() {
		return permissoes;
	}
	
	
	public List<Usuario> getAmigos() {
		return amigos;
	}
	public void setAmigos(List<Usuario> amigos) {
		this.amigos = amigos;
		
	}
	
	public void setAmigo(Usuario amigos) {
		this.amigos.add(amigos);
		
	}
	
	public void setPermissao(Permissao permissao) {
		this.permissoes.add(permissao);
		
	}
	
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	public void setPost(Post post) {
		this.posts.add(post);
		
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public String getDtNascimento() {
		return dtNascimento;
	}
	
	public void setDtNascimento(String dtNascimento) {
		this.dtNascimento = dtNascimento;
	}
	
	public String getFoto_perfil() {
		return foto_perfil;
	}
	
	public void setFoto_perfil(String foto_perfil) {
		this.foto_perfil = foto_perfil;
	}

	public String getFoto_capa() {
		return foto_capa;
	}

	public void setFoto_capa(String foto_capa) {
		this.foto_capa = foto_capa;
	}
	
	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}
	
	public void setMensagen(Mensagem mensagen) {
		this.mensagens.add(mensagen);
		
	}

	@Transient
    public String getFoto() {
        if (foto_perfil == null || id == null) return "/usuario-fotos/default.png";
         
        return "/usuario-fotos/foto/"+ id + "/" + foto_perfil;
    }
	
	@Transient
    public String getCapa() {
        if (foto_capa == null || id == null) return null;
         
        return "/usuario-fotos/capa/"+ id + "/" + foto_capa;
    }

	@Override
	public String toString() {
		return "Usuario [id = " + id + ", nome = " + nome + ", foto_perfil = " + foto_perfil + ", foto_capa = " + foto_capa
				+ ", login = " + login + ", senha = " + senha + ", dtNascimento = " + dtNascimento;
	}
	
}
