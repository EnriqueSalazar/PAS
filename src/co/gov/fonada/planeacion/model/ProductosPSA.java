package co.gov.fonada.planeacion.model;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.io.Serializable;
import java.util.Date;

@Cache
@Entity
public class ProductosPSA implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5521987295840853784L;

	@Id
	private Long id;
	@Index
	private Date timeStamp;
	private Integer vigencia;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		System.out.println(id + " setId");
		
		this.id = id;
	}


	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Integer getVigencia() {
		return vigencia;
	}

	public void setVigencia(Integer vigencia) {
		this.vigencia = vigencia;
	}
}
