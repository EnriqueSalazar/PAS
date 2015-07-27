package co.gov.fonada.planeacion.model;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.io.Serializable;

@Cache
@Entity
public class Fuente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5521987295840853784L;

	@Id
	private Long id;
	@Index
	private String fuente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		System.out.println(id + " setId");
		this.id = id;
	}


	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}
}
