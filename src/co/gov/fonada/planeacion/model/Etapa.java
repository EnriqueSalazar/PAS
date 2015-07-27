package co.gov.fonada.planeacion.model;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Cache
@Entity
public class Etapa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5521987295840853784L;

	@Id
	private Long id;
	@Index
	private String opcion;
	@Index
	private String modalidad;
	@Index
	private String etapas;
	@Index
	private Integer duracion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		System.out.println(id + " setId");
		
		this.id = id;
	}


	
	
	/**
	 * @return the opcion
	 */
	public String getOpcion() {
		return opcion;
	}

	/**
	 * @param opcion
	 *            the opcion to set
	 */
	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	/**
	 * @return the modalidad
	 */
	public String getModalidad() {
		return modalidad;
	}

	/**
	 * @param modalidad
	 *            the modalidad to set
	 */
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	/**
	 * @return the etapas
	 */
	public String getEtapas() {
		return etapas;
	}

	/**
	 * @param etapas
	 *            the etapas to set
	 */
	public void setEtapas(String etapas) {
		this.etapas = etapas;
	}

	/**
	 * @return the duracion
	 */
	public Integer getDuracion() {
		return duracion;
	}

	/**
	 * @param duracion
	 *            the duracion to set
	 */
	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	
}
