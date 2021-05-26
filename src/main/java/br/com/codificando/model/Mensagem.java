package br.com.codificando.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity 
@Table (name="MENSAGEM")
public class Mensagem {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
    private String message;

    private String fromLogin;
    
    @Column(name="para")
    private String to;
    
    private String horario;

    private String data;
    
    private Long status;
    
    
    @ManyToOne(cascade=CascadeType.PERSIST)
	private Usuario deLogin;
    
    @ManyToOne(cascade=CascadeType.PERSIST)
	private Usuario paraLogin;
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromLogin() {
        return fromLogin;
    }

    public void setFromLogin(String fromLogin) {
        this.fromLogin = fromLogin;
    }
    

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	

	public Usuario getDeLogin() {
		return deLogin;
	}

	public void setDeLogin(Usuario deLogin) {
		this.deLogin = deLogin;
	}

	public Usuario getParaLogin() {
		return paraLogin;
	}

	public void setParaLogin(Usuario paraLogin) {
		this.paraLogin = paraLogin;
	}

	@Override
	public String toString() {
		return "Mensagem [id=" + id + ", message=" + message + ", fromLogin=" + fromLogin + ", to=" + to + ", horario="
				+ horario + ", data=" + data + ", status=" + status + "]";
	}

	

	

    
}
