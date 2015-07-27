package co.gov.fonada.planeacion.model;

import java.io.Serializable;




import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


@Entity
public class Versiones implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5521987295840853799L;

	@Id
	private Long id;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
