package co.gov.fonada.planeacion.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.*;

import java.io.Serializable;
import java.util.Date;

@Cache
@Entity
public class Seguimiento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5521987295840853784L;

	@Id
	private Long id;
	@Index
	private Date timeStamp;



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
}
